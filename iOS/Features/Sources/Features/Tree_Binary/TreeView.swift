import SwiftUI
import CoreUI

// MARK: - TreeView
struct TreeView: View {
    var size: CGFloat = 50
    @ObservedObject var controller = TreeViewControllerImpl<Int>(
        delayOnInsertionComplete: 500,processingTime: 500)

    var body: some View {
            VStack{
                IconView(icon: SFIcons.next)
                    .onTapGesture(perform: {
                        controller.onCanvasSizeChanged(300, 300)
                        
                    })
                IconView(icon: SFIcons.reload)
                    .onTapGesture(perform: {
                        controller.insert(
                            value:Int.random(in: 0...100),
                            onRunning: {},
                            onFinish: {}
                        )
                        
                    })
                
                ZStack {
                    // Draw lines
                    Canvas { context, _ in
                        for line in controller.lines {
                            var path = Path()
                            path.move(to: line.start)
                            path.addLine(to: line.end)
                            context.stroke(path, with: .color(.black), lineWidth: 2)
                        }
                    }

                    // Draw nodes
                    ForEach(controller.nodes) { node in
                
                        VisualNode(
                            label: node.label,
                            size: size,
                            offset: node.center,
                            color: .blue
                        )
                    }
                }
           
                .background(.red)
            

            
        }
    }
}

// MARK: - VisualNode
struct VisualNode: View {
    var label: String
    var size: CGFloat = 50
    var offset: CGPoint = .zero
    var color: Color = .blue

    @State private var animatedOffset: CGPoint = .zero

    var body: some View {
        let x = offset.x.isNaN ? 0 : offset.x
        let y = offset.y.isNaN ? 0 : offset.y

        Circle()
            .fill(color)
            .frame(width: size, height: size)
            .overlay(
                Text(label)
                  //  .foregroundColor(color.luminance < 0.6 ? .white : .black)
                    .multilineTextAlignment(.center)
            )
            .position(animatedOffset)
            .onChange(of: offset) { newValue in
                withAnimation(.easeInOut) {
                    animatedOffset = CGPoint(x: x, y: y)
                }
            }
            .onAppear {
                animatedOffset = CGPoint(x: x, y: y)
            }
    }
}

