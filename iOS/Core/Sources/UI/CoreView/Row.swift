import SwiftUI

public struct Row<Content: View>: View {
    var alignment: VerticalAlignment = .center
    var spacing: CGFloat? = nil
    let content: () -> Content
    
   public init(
        alignment: VerticalAlignment = .center,
        spacing: CGFloat? = nil,
        @ViewBuilder content: @escaping () -> Content
    ) {
        self.alignment = alignment
        self.spacing = spacing
        self.content = content
    }
    
   public var body: some View {
        HStack(
            alignment: alignment,
            spacing: spacing,
            content: content
        )
    }
}
