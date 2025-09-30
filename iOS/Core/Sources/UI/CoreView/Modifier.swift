import SwiftUI


public extension View {
    
    /// Fills the maximum width available with optional alignment
    func fillMaxWidth(alignment: Alignment = .leading) -> some View {
        self.frame(maxWidth: .infinity, alignment: alignment)
    }
    
    /// Fills the maximum height available with optional alignment
    func fillMaxHeight(alignment: Alignment = .center) -> some View {
        self.frame(maxHeight: .infinity, alignment: alignment)
    }
    
    /// Fills both width and height available with optional alignment
    func fillMaxSize(alignment: Alignment = .center) -> some View {
        self.frame(maxWidth: .infinity, maxHeight: .infinity, alignment: alignment)
    }
    
    /// Sets a fixed width
    func width(_ width: CGFloat, alignment: Alignment = .center) -> some View {
        self.frame(width: width, alignment: alignment)
    }
    
    /// Sets a fixed height
    func height(_ height: CGFloat, alignment: Alignment = .center) -> some View {
        self.frame(height: height, alignment: alignment)
    }
    
    /// Sets both fixed width and height
    func size(width: CGFloat, height: CGFloat, alignment: Alignment = .center) -> some View {
        self.frame(width: width, height: height, alignment: alignment)
    }
    /// Sets both fixed width and height
    func size(value: CGFloat, alignment: Alignment = .center) -> some View {
        self.frame(width: value, height: value, alignment: alignment)
    }
    /// Sets min and max width
       func widthIn(min: CGFloat? = nil, max: CGFloat? = nil, alignment: Alignment = .center) -> some View {
           self.frame(minWidth: min, maxWidth: max, alignment: alignment)
       }
       
       /// Sets min and max height
       func heightIn(min: CGFloat? = nil, max: CGFloat? = nil, alignment: Alignment = .center) -> some View {
           self.frame(minHeight: min, maxHeight: max, alignment: alignment)
       }
    
}


public extension View {
    
    /// Applies a rounded rectangle background with specified color and corner radius
    func backgroundRoundRect(color: Color, radius: CGFloat) -> some View {
        self.background(
            RoundedRectangle(cornerRadius: radius)
                .fill(color)
        )
    }
}
