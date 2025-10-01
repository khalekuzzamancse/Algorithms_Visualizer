import SwiftUI

public struct ImageView<S: Shape>: View {
    private let image: ImageResource
    private let width: CGFloat
    private let height: CGFloat
    private let shape: S
  

    // Default shape is Rectangle
    public init(
        image: ImageResource,
        width: CGFloat,
        height: CGFloat,
        shape: S = Rectangle()
    ) {
        self.image = image
        self.width = width
        self.height = height
        self.shape = shape
        
    }
    // Default shape is Rectangle
    public init(
        image: ImageResource,
        size: CGFloat,
        shape: S = Rectangle()
    ) {
        self.image = image
        self.width = size
        self.height = size
        self.shape = shape
        
    }

    public var body: some View {
        Image(image)
            .resizable()
            .scaledToFill()
            .frame(width: width, height: height)
            .clipShape(shape)
            .clipped()
    }
}

public struct IconView<S: Shape>: View {
    private let icon: String
    private let width: CGFloat
    private let height: CGFloat
    private let shape: S
    private var tint: Color? = nil
  

    // Default shape is Rectangle
    public init(
        icon: String,
        width: CGFloat,
        height: CGFloat,
        shape: S = Rectangle(),
        tint:Color? = nil
    ) {
        self.icon = icon
        self.width = width
        self.height = height
        self.shape = shape
        self.tint=tint
        
    }
    // Default shape is Rectangle
    public init(
        icon: String,
        size: CGFloat = 25,
        shape: S = Rectangle(),
        tint:Color? = nil
    ) {
        self.icon = icon
        self.width = size
        self.height = size
        self.shape = shape
        self.tint=tint
    }

    public var body: some View {
        let view:some View=Image(systemName:icon)
            .frame(width: width, height: height)
            .clipShape(shape)
            .clipped()
        
        if(tint != nil){
            
                view.foregroundColor(tint!)
        }
        else { view}
         
    }
}

