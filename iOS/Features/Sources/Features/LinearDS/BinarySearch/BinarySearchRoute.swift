

import SwiftUI
import CoreUI

public struct BinarySearchRoute: View {
    private let tag = "BinarySearchRoute"
    
    @State private var code: String? = nil
    
    @StateObject private var controller: ArrayControllerImpl
    private let iterator: BinarySearchIterator
    
    public init(array: [Int], target: Int) {
        _controller = StateObject(
            wrappedValue: ArrayControllerImpl(
                itemLabels: array.map(\.description),
                pointerLabels: ["low", "high", "mid"]
            )
        )
        
        iterator = BinarySearchIterator(
            array: array,
            target: target
        )
    }
    

    public var body: some View {
        SimulationSlot(
            onNextRequest: {
                let ctrl = controller // capture safely
                Task {
                    let st = self.iterator.next()

                    switch st {
                    case .start(let code): print("")
                        self.code = code

                    case .pointers(let low, let high, let mid, let code):
                        ctrl.movePointer(label: "low", index: low!)
                        ctrl.movePointer(label: "high", index: high!)
                        if let midIndex = mid {
                            ctrl.movePointer(label: "mid", index: midIndex)
                        } else {
                            ctrl.hidePointer(label: "mid")
                        }
                        self.code = code

                    case .foundAt(let index, let code):
                        self.code = code
                        ctrl.changeCellColor(index: index, color: Color.green)
                        ctrl.hidePointer(label: "low")
                        ctrl.hidePointer(label: "high")
                        ctrl.hidePointer(label: "mid")

                    case .finished(let code):
                        self.code = code
                        ctrl.hidePointer(label: "low")
                        ctrl.hidePointer(label: "high")
                        ctrl.hidePointer(label: "mid")
                    }
                }
            },

            onResetRequst: {
                //controller.reset()
                controller.movePointer(label: "low", index: 0)
                controller.movePointer(label: "mid", index: 0)
                controller.movePointer(label: "high", index: 0)
                
                
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
