import SwiftUI

public struct TextFieldView: View {
    
    // Bound value
    @Binding public var value: String
    
    // Customizable properties
    public var hints: String
    public var contentPaddingH: CGFloat
    public var contentPaddingV: CGFloat
    public var leadingIcon: AnyView?
    public var onValueChange: (String) -> Void
    public var onSubmit: ()->Void
    private let cornerRadius:CGFloat

    
    public init(
        value: Binding<String>,
        hints: String = "",
        paddingHorizontal: CGFloat = 8,
        paddingVertical: CGFloat = 4,
        cornerRadius:CGFloat=10,
        @ViewBuilder leadingIcon: () -> some View = { EmptyView() },
        onValueChange:@escaping (String)->Void = {_ in },
        onSubmit:@escaping ()->Void = {}
    ) {
        self._value = value
        self.hints = hints
        self.contentPaddingH = paddingHorizontal
        self.contentPaddingV = paddingVertical
        self.leadingIcon = AnyView(leadingIcon())
        self.onValueChange = onValueChange
        self.onSubmit=onSubmit
        self.cornerRadius=cornerRadius
    }
    
    public var body: some View {
        let shape = RoundedRectangle(cornerRadius: cornerRadius)
        
        HStack {
            if !(leadingIcon is EmptyView) {
                leadingIcon
            }
            
            TextField(hints, text: $value)
                .textFieldStyle(.plain)
                .onChange(of:value){(value) in
                    onValueChange(value)
                   // self.value=value.filter { $0.isNumber }
                    
                }
                .onSubmit {
                   // hidekeyboard()
                }
        }
        .padding(.horizontal, contentPaddingH)
        .padding(.vertical, contentPaddingV)
        .background(shape.stroke(Color.black,lineWidth: 1))
        .clipShape(shape)
    }
}
