import SwiftUI


struct ArrayView:View {
    @StateObject var controller = ArrayControllerImpl(
        itemLabels: ["A", "B", "C", "D"],
        pointerLabels: ["i"]
    )
    @State var cnt=0
    var body: some View {
        
        VStack{
            HStack{
                Button("Swap 0 and 1") {
                    let ctrl = controller  // capture safely
                    Task {
                        await ctrl.swap(i: 0, j: 1, delay: 0.3)
                      
                    }
                }.padding()
                           .background(Color.blue)
                           .foregroundColor(.white)
                           .cornerRadius(8)
                
                Button("Move") {
                    let ctrl = controller  // capture safely
                    Task {
                       
                        ctrl.movePointer(label: "i", index: cnt%4)
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
    
    var body: some View {
        ZStack {
            HStack {
                ForEach(controller.cells.indices, id: \.self) { index in
                    CellView(size: 64, color: .blue, borderColor: .gray)
                        .anchorPreference(key: CellPosKey.self, value: .center) {
                            [index: $0]   // publish each cell's center anchor
                        }
                }
            }
            .background(Color.yellow)
            
            // Place elements using stored positions in controller
            ForEach(controller.elements.indices, id: \.self) { index in
                let element = controller.elements[index]
                ElementView(label: element.label, color: element.color, size: 64)
                    .position(x:element.position.x,y:element.position.y) // 👈 position comes from controller
            }
            let cell=controller.cells[0]
            
            ForEach(controller.pointers,id: \.self){poiner in
                if(poiner.position != nil){
                    CellPointer(
                        cellSize: 64,
                        label: poiner.label,
                        color: Color.black,
                        position:poiner.position!
                    )
                }
             
                
            }
            
           
            
        }
        .frame(height: 64)
        .background(Color.gray)
        .overlayPreferenceValue(CellPosKey.self) { prefs in
            GeometryReader { proxy in
                Color.clear.onAppear {
                    for (index, anchor) in prefs {
                        let center = proxy[anchor]
                        controller.onCellPositionChanged(index: index, position: center)
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
    
    var body: some View {
        Circle()
            .fill(color == .clear ? .teal : color)
            .frame(width: size, height: size)
            .overlay(Text(label).foregroundColor(.white))
    }
}




struct CellView: View {
    let size: CGFloat
    let color: Color
    let borderColor: Color?
    
    var body: some View {
        Rectangle()
            .fill(color)
            .overlay(
                Rectangle()
                    .stroke(borderColor ?? .clear,
                            lineWidth: borderColor == nil ? 0 : 1)
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
        
    
        Text(label)

            .foregroundColor(color)
            .frame(width: cellSize, height: cellSize)
            .background(Color.yellow.opacity(0.4)) // Material-like
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
