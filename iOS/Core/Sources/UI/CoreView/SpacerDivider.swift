import SwiftUI

public struct SpacerVertical: View {
    public let height: CGFloat
    
    public init(height: CGFloat) {
        self.height = height
    }
    public init(_ height: CGFloat) {
        self.height = height
    }
    
    public var body: some View {
        Spacer()
            .frame(height: height)
    }
}

public struct SpacerHorizontal: View {
    public let width: CGFloat
    

    public init(width: CGFloat) {
        self.width = width
    }
    public init(_ width: CGFloat) {
        self.width = width
    }
    
    public var body: some View {
        Spacer()
            .frame(width: width)
    }
}
