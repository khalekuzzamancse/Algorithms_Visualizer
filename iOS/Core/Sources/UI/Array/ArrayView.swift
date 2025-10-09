import SwiftUI


struct CellPosKey: PreferenceKey {
    nonisolated(unsafe) static var defaultValue: [Int: Anchor<CGPoint>] = [:]

    static func reduce(value: inout [Int: Anchor<CGPoint>],
                       nextValue: () -> [Int: Anchor<CGPoint>]) {
        value.merge(nextValue(), uniquingKeysWith: { $1 })
    }
}


public struct ArrayView: View {

    
    @ObservedObject private var controller: ArrayControllerImpl
    public init(controller: ArrayControllerImpl){
        self.controller = controller
  
    }
    
   
   public var body: some View {
        ZStack {
            FlowLayout() {
                ForEach(controller.cells.indices, id: \.self) { index in
                    CellView(size: 64, color: controller.cells[index].color, borderColor: .gray)
                        .anchorPreference(key: CellPosKey.self, value: .center) {
                            [index: $0]
                        }
                        
                }
            }
            .coordinateSpace(name: "ArrayViewSpace") // Add this
            .frame(maxWidth: .infinity, maxHeight: .infinity) // Ensure full frame
            ForEach(controller.elements.indices, id: \.self) { index in
                let element = controller.elements[index]
                let x=element.position.x
                let y=element.position.y
                ElementView(
                    label: element.label,
                    color: element.color,
                    size: 64,
                    position: CGPoint(x: x, y: y)
                )
                  
            }
         
            
            ForEach(controller.pointers,id: \.self){poiner in
                if(poiner.position != nil){
                    let x=poiner.position!.x
                    let y=poiner.position!.y
                    CellPointer(
                        cellSize: 64,
                        label: poiner.label,
                        color: Color.black,
                        position:CGPoint(x: x, y: y)
                    )
                }
             
                
            }
            
            
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity) // Ensure full frame
        .overlayPreferenceValue(CellPosKey.self) { prefs in
            GeometryReader { proxy in
                Color.clear.onAppear {
                    for (index, anchor) in prefs {
                        let center = proxy[anchor]
                        controller.onCellPositionChanged(
                            index: index, position: center)
                    }
                }
                .onChange(of: prefs) { newPrefs in
                    for (index, anchor) in newPrefs {
                        let center = proxy[anchor]
                        controller.onCellPositionChanged(index: index, position: center)
                    }
                }
            }
        }
        .coordinateSpace(name: "OverlaySpace") // Add this
    }
    
}



struct ElementView: View {
    let label: String
    let color: Color
    let size: CGFloat
    let position:CGPoint
    
    @State private var animatedPosition: CGPoint = .zero
    
    var body: some View {
        Circle()
            .fill(color == .clear ? .teal : color)
            .frame(width: size, height: size)
            .overlay(Text(label).foregroundColor(.white))
            .position(position)
            .onChange(of: position) { newValue in
                withAnimation(.easeInOut(duration: 0.3)) {
                    animatedPosition = newValue
                }
            }
            .onAppear {
                animatedPosition = position
            }
    }
}




struct CellView: View {
    let size: CGFloat
    let color: Color
    let borderColor: Color?
    
    var body: some View {
        Rectangle()
                  .fill(color)
                  .frame(width: size, height: size)
                  .border(borderColor ?? .gray, width: 1)
           
    }
}


struct CellPointer: View {
    let cellSize: CGFloat
    let label: String
    let color: Color
    let position: CGPoint



    var body: some View {
        ZStack(alignment: .bottom) {
            TextView(
                text:label,
                color: .white,
                fontSize: 22
            )
           .background(Color.yellow.opacity(0.5))
            Rectangle().stroke(Color.clear, lineWidth: 1)
        }
        .size(value: cellSize)
       
        .cornerRadius(8)
        .position(position)

       
    
        
    }
}

struct FlowLayout: Layout {
    func sizeThatFits(proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) -> CGSize {
        var width: CGFloat = 0
        var height: CGFloat = 0
        var rowHeight: CGFloat = 0
        let maxWidth = proposal.width ?? .infinity

        for subview in subviews {
            let size = subview.sizeThatFits(.unspecified)
            if width + size.width > maxWidth {
                width = 0
                height += rowHeight
                rowHeight = 0
            }
            rowHeight = max(rowHeight, size.height)
            width += size.width
        }
        height += rowHeight
        return CGSize(width: maxWidth, height: height)
    }

    func placeSubviews(in bounds: CGRect, proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) {
        var x: CGFloat = bounds.minX
        var y: CGFloat = bounds.minY
        var rowHeight: CGFloat = 0

        for subview in subviews {
            let size = subview.sizeThatFits(.unspecified)
            if x + size.width > bounds.maxX {
                x = bounds.minX
                y += rowHeight
                rowHeight = 0
            }
            subview.place(at: CGPoint(x: x, y: y), proposal: ProposedViewSize(size))
            x += size.width
            rowHeight = max(rowHeight, size.height)
        }
    }
}
