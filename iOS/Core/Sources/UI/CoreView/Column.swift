import SwiftUI

public struct Column<Content: View>: View {
    var alignment: HorizontalAlignment = .center
    var spacing: CGFloat? = nil
    let content: () -> Content
    
   public init(
        alignment: HorizontalAlignment = .center,
        spacing: CGFloat? = nil,
        @ViewBuilder content: @escaping () -> Content
    ) {
        self.alignment = alignment
        self.spacing = spacing
        self.content = content
    }
    
    public var body: some View {
        VStack(
            alignment: alignment,
            spacing: spacing,
            content: content
        )
    }
}
