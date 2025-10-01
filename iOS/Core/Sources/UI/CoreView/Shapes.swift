
import SwiftUI

public struct RoundedRectShape: View {
    
    public var radius: CGFloat
    public var color: Color
    
    public init(radius: CGFloat, color: Color) {
        self.radius = radius
        self.color = color
    }
    
    public var body: some View {
        RoundedRectangle(cornerRadius: radius).fill(color)
    }
}


public struct CircleShape: View {
    public var color: Color

    public init(color: Color) {
        self.color = color
    }

    public var body: some View {
        Circle()
            .fill(color)
    }
}


