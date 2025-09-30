import SwiftUI


public struct ArrayView:View {
    @StateObject var controller = ArrayControllerImpl(
        itemLabels: ["10", "20", "30", "40","50","60","70","80"],
        pointerLabels: ["i"]
    )
    @State var cnt=0
    public init(){
        
  
    }
   public var body: some View {
       VStack{
            HStack{
                Button("Swap 0 and 1") {
                    let ctrl = controller  // capture safely
                    Task {
                        await ctrl.swap(i: 0, j: 1, delay: 0)
                      
                    }
                }.padding()
                           .background(Color.blue)
                           .foregroundColor(.white)
                           .cornerRadius(8)
                
                Button("Move") {
                    let ctrl = controller  // capture safely
                    let cellLen=controller.cells.capacity
                    Task {
                       
                        ctrl.movePointer(label: "i", index: cnt%cellLen)
                        cnt+=1
                    }
                }.padding()
                           .background(Color.blue)
                           .foregroundColor(.white)
                           .cornerRadius(8)

            }
          
           _ArrayView(controller: controller)
        }
       
    
       
            
    }
}

struct CellPosKey: PreferenceKey {
    nonisolated(unsafe) static var defaultValue: [Int: Anchor<CGPoint>] = [:]

    static func reduce(value: inout [Int: Anchor<CGPoint>],
                       nextValue: () -> [Int: Anchor<CGPoint>]) {
        value.merge(nextValue(), uniquingKeysWith: { $1 })
    }
}


struct _ArrayView: View {
    @ObservedObject var controller: ArrayControllerImpl
    let columns = [
        GridItem(.adaptive(minimum: 64), spacing:0)
    ]

    
    var body: some View {
        ZStack {
            LazyVGrid(columns: columns, spacing: 0) {
                ForEach(controller.cells.indices, id: \.self) { index in
                    CellView(size: 64, color: .blue, borderColor: .gray)
                        .anchorPreference(key: CellPosKey.self, value: .center) {
                            [index: $0]
                        }
                }
            }
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
            .fill(Color.clear) // no solid fill
            .overlay(
                Rectangle()
                    .stroke(color, lineWidth: 1) // use `color` as border
            )
            .frame(width: size, height: size)
    }
}


struct CellPointer: View {
    let cellSize: CGFloat
    let label: String
    let color: Color
    let position: CGPoint

    @State private var animatedPosition: CGPoint = .zero

    var body: some View {
    
        TextView(text:label,color: .white,fontSize: 20)
            .size(value: cellSize)
            .background(Color.yellow.opacity(0.9))
            .cornerRadius(8)
            .position(animatedPosition)
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
