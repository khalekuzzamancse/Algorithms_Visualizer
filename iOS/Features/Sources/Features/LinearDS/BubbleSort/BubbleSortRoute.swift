
import SwiftUI
import CoreUI

public struct BubbleSortRoute: View {
    private let tag = "BubbleSortRoute"
    
    @State private var code: String? = nil

    @StateObject private var controller: ArrayControllerImpl
    private let iterator: BubbleSortIterator

    public init(array: [Int]) {
        _controller = StateObject(
            wrappedValue: ArrayControllerImpl(
                itemLabels: array.map(\.description),
                pointerLabels: ["i", "j"]
            )
        )

        iterator = BubbleSortIterator(array: array)
    }
    
    public var body: some View {
        SimulationSlot(
            onNextRequest: {
            
                let ctrl = controller // capture safely
                Task {
                    let st = self.iterator.next()
                  
                    
                    switch st {
                    case .start(let code):
                        self.code = code
                        
                    case .pointers(let i, let j, let code):
                        ctrl.movePointer(label: "i", index: i)
                        if(j != nil ){
                            ctrl.movePointer(label: "j", index: j!)
                            ctrl.movePointer(label: "j+1", index: j!+1)
                        }
                    
                   
                        self.code = code
                        
            
                    case .swapped(let i, let j, let array, let code):
                    
                        await ctrl.swap(i: i, j: j,delay: 100)
                        self.code = code
                    case .none:
                        let x="do nothing"
                        
                    case .finished(let code):
                        self.code = code
                        ctrl.hidePointer(label: "i")
                        ctrl.hidePointer(label: "j")
                        ctrl.hidePointer(label: "j+1")
                        
                    }
                }
            },
            
            onResetRequst: {
                controller.reset()
                controller.movePointer(label: "i", index: 0)
                controller.movePointer(label: "j", index: 0)
            },
            
            onAutoPlayRequest: {
                iterator.hasNext()
            },
            
            pseudocode: $code,
            
            visualization: {
                ArrayView(controller: controller)
            }
        )
    }
}
