import SwiftUI

public struct ButtonViewSolid: View {
    
    public var label: String
    public var paddingHorizontal: CGFloat = 8
    public var paddingVertical: CGFloat = 4
    public var backgroundColor: Color = .blue
    public var fontSize: CGFloat = 18
    public var shape: AnyView
    public var minWidth: CGFloat? = nil
    public var maxWidth: CGFloat? = nil
    public var onClick: () -> Void
    public var isEnabled: Bool = true
    
    public init(
        label: String,
        shape: AnyView,
        paddingHorizontal: CGFloat = 8,
        paddingVertical: CGFloat = 4,
        backgroundColor: Color = .blue,
        fontSize: CGFloat = 18,
        minWidth: CGFloat? = nil,
        maxWidth: CGFloat? = nil,
        isEnabled: Bool = true,
        onClick: @escaping () -> Void = {}
    ) {
        self.label = label
        self.shape = shape
        self.paddingHorizontal = paddingHorizontal
        self.paddingVertical = paddingVertical
        self.backgroundColor = backgroundColor
        self.fontSize = fontSize
        self.minWidth = minWidth
        self.maxWidth = maxWidth
        self.isEnabled = isEnabled
        self.onClick = onClick
    }
    
    public var body: some View {
        Button(action: {
            if isEnabled { onClick() }
        }) {
            TextView(
                text: label,
                color: backgroundColor.isDarkColor ? .white : .black,
                fontSize: fontSize
            )
            .padding(.horizontal, paddingHorizontal)
            .padding(.vertical, paddingVertical)
            .frame(minWidth: minWidth, maxWidth: maxWidth)
            .background(
                shape
                    .foregroundColor(backgroundColor)
                    .opacity(isEnabled ? 1.0 : 0.5)
            )
        }
        .buttonStyle(PlainButtonStyle())
        .disabled(!isEnabled)
    }
}
