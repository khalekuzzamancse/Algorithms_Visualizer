//
//  CodeViewerTheme.swift
//  AlgorithmsVisualizer
//
//  Created by Md Khalekuzzaman on 10/1/25.
//


import SwiftUI
import Combine

// Define code theme colors
struct CodeViewerTheme {
    var name: String
    var backgroundColor: Color
    var borderColor: Color
    var toolbarBackgroundColor: Color
    var textColor: Color
    var highlightColor: Color
}

// Example themes
extension CodeViewerTheme {
    static var defaultTheme: CodeViewerTheme {
        CodeViewerTheme(
            name: "Default",
            backgroundColor: Color(.systemBackground),
            borderColor: Color.gray,
            toolbarBackgroundColor: Color(.secondarySystemBackground),
            textColor: Color.primary,
            highlightColor: Color.green
        )
    }
    
    static var availableThemes: [CodeViewerTheme] {
        [defaultTheme] // Add more themes here
    }
}

struct Token {
    let keywords: Set<String>
    let operators: Set<String>
    let literals: Set<String>
    let functions: Set<String>
}

// Example token set
let codeTokens = Token(
    keywords: ["for", "if", "return", "while", "break"],
    operators: ["=", "<", "++", "==", "[", "]"],
    literals: ["true", "false", "nil"],
    functions: ["print", "println"]
)

struct CodeViewer: View {
    @State private var fontSize: CGFloat = 15
    @State private var theme: CodeViewerTheme = .defaultTheme
    @State private var showThemePicker = false
    let code: String
    
    var body: some View {
        VStack(spacing: 0) {
            ToolbarView(
                onIncrease: { fontSize += 2 },
                onDecrease: { if fontSize > 8 { fontSize -= 2 } },
                onCopy: { UIPasteboard.general.string = code },
                onThemeChange: { showThemePicker.toggle() },
                theme: theme
            )
            ScrollView {
                CodeTextView(code: code, fontSize: fontSize, theme: theme, tokens: codeTokens)
                    .padding(8)
                    .background(theme.backgroundColor)
                    .cornerRadius(6)
                    .overlay(RoundedRectangle(cornerRadius: 6).stroke(theme.borderColor, lineWidth: 2))
            }
        }
        .sheet(isPresented: $showThemePicker) {
            ThemePickerView(selectedTheme: $theme)
        }
        .padding()
        .background(theme.backgroundColor.edgesIgnoringSafeArea(.all))
    }
}

struct ToolbarView: View {
    var onIncrease: () -> Void
    var onDecrease: () -> Void
    var onCopy: () -> Void
    var onThemeChange: () -> Void
    var theme: CodeViewerTheme
    
    var body: some View {
        HStack {
            Spacer()
            Button(action: onIncrease) {
                Image(systemName: "plus")
            }
            Button(action: onDecrease) {
                Image(systemName: "minus")
            }
            Button(action: onCopy) {
                Image(systemName: "doc.on.doc")
            }
            Button(action: onThemeChange) {
                Image(systemName: "paintpalette")
            }
        }
        .padding()
        .background(theme.toolbarBackgroundColor)
        .foregroundColor(theme.textColor)
    }
}

struct ThemePickerView: View {
    @Binding var selectedTheme: CodeViewerTheme
    @Environment(\.presentationMode) var presentationMode
    let themes = CodeViewerTheme.availableThemes
    
    var body: some View {
        NavigationView {
            List(themes, id: \.name) { theme in
                HStack {
                    Text(theme.name)
                    Spacer()
                    if theme.name == selectedTheme.name {
                        Image(systemName: "checkmark")
                    }
                }
                .contentShape(Rectangle())
                .onTapGesture {
                    selectedTheme = theme
                    presentationMode.wrappedValue.dismiss()
                }
                .listRowBackground(theme.backgroundColor)
                .foregroundColor(theme.textColor)
            }
            .navigationBarTitle("Select Theme", displayMode: .inline)
            .navigationBarItems(trailing: Button("Close") {
                presentationMode.wrappedValue.dismiss()
            })
        }
    }
}

// Highlight code text with tokens
struct CodeTextView: View {
    let code: String
    let fontSize: CGFloat
    let theme: CodeViewerTheme
    let tokens: Token
    
    var body: some View {
        // Use AttributedString to apply different styles to tokens
        Text(highlightedCode)
            .font(.system(size: fontSize, design: .monospaced))
            .lineSpacing(fontSize * 0.5)
            .frame(maxWidth: .infinity, alignment: .leading)
            .fixedSize(horizontal: false, vertical: true)
    }
    
    // Construct an AttributedString for the code highlighting keywords, literals, operators, etc.
    var highlightedCode: AttributedString {
        var attributed = AttributedString(code)
        
        // Reset style to default
        attributed.foregroundColor = theme.textColor
        
        let keywordStyle = AttributeContainer()
            .foregroundColor(theme.highlightColor)
            .font(.system(.body, design: .default).weight(.bold))
        let operatorStyle = AttributeContainer.foregroundColor(.blue)
        let literalStyle = AttributeContainer.foregroundColor(.green)
        let functionStyle = AttributeContainer.foregroundColor(.purple)
        
        // Tokenize by words, then apply styles based on token sets
        let words = code.split(whereSeparator: { !$0.isLetter && !$0.isNumber && $0 != "_" })
        
        for word in words {
            let str = String(word)
            guard let range = attributed.range(of: str) else { continue }
            
            if tokens.keywords.contains(str) {
                attributed[range].setAttributes(keywordStyle)
            } else if tokens.operators.contains(str) {
                attributed[range].setAttributes(operatorStyle)
            } else if tokens.literals.contains(str) {
                attributed[range].setAttributes(literalStyle)
            } else if tokens.functions.contains(str) {
                attributed[range].setAttributes(functionStyle)
            } else {
                // Keep default
            }
        }
        
        return attributed
    }
}

// Preview
struct CodeViewer_Previews: PreviewProvider {
    static var sampleCode = """
    for i in 0..<10 {
        if i % 2 == 0 {
            print("even number:", i)
        } else {
            print("odd number:", i)
        }
    }
    """
    
    static var previews: some View {
        CodeViewer(code: sampleCode)
    }
}
