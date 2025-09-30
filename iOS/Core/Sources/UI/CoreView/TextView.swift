import SwiftUI

public struct TextView: View {
    let text: String
    var color: Color = .primary
    var fontSize: CGFloat = 15
    var fontWeight: Font.Weight = .regular
    var maxLines: Int? = nil
    var overflow: Text.TruncationMode = .tail
    var justifyContent: Bool = false   // 🔹 new flag
    public init(
           text: String,
           color: Color = .primary,
           fontSize: CGFloat = 15,
           fontWeight: Font.Weight = .regular,
           maxLines: Int? = nil,
           overflow: Text.TruncationMode = .tail,
           justifyContent: Bool = false
       ) {
           self.text = text
           self.color = color
           self.fontSize = fontSize
           self.fontWeight = fontWeight
           self.maxLines = maxLines
           self.overflow = overflow
           self.justifyContent = justifyContent
       }
   public var body: some View {
                  if justifyContent {
                      TextUIView(text: text)
                          
                  } else {
                      Text(text)
                          .font(.system(size: fontSize, weight: fontWeight))
                          .foregroundColor(color)
                          .lineLimit(maxLines)
                          .truncationMode(overflow)
                          .multilineTextAlignment(.leading)
                  }
    }
}

public struct TextUIView: UIViewRepresentable {
    var text: String
    var fontSize: CGFloat = 15
    var fontWeight: UIFont.Weight = .regular
    var color: UIColor = .label
    
    public init(text: String) {
        self.text = text
      
    }
    
   public func makeUIView(context: Context) -> UITextView {
        let textView = UITextView()
        textView.isEditable = false
        textView.isScrollEnabled = false
        textView.isSelectable = false
        textView.backgroundColor = .clear
        textView.textContainerInset = .zero
        textView.textContainer.lineFragmentPadding = 0
        textView.textAlignment = .justified
        
        // 🔹 Let Auto Layout size the view
        textView.setContentCompressionResistancePriority(.required, for: .vertical)
        textView.setContentHuggingPriority(.required, for: .vertical)
        textView.setContentHuggingPriority(.required, for: .horizontal)
        
        return textView
    }
    
    public func updateUIView(_ uiView: UITextView, context: Context) {
        uiView.text = text
        uiView.textColor = color
        uiView.font = UIFont.systemFont(ofSize: fontSize, weight: fontWeight)
    }
}

