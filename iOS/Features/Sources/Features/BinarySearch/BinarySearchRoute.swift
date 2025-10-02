

import SwiftUI
import CoreUI

public struct BinarySearchRoute: View {
    private let tag = "BinarySearchRoute"
    @State var code: String? = nil
    @StateObject var controller = ArrayControllerImpl(
        itemLabels: ["10", "20", "30", "40", "50", "60", "70", "80"],
        pointerLabels: ["low", "high", "mid"]
    )

    let iterator = BinarySearchIterator(array: [10, 20, 30, 40, 50, 60, 70, 80], target: 90)

    public init() {}

    public var body: some View {
        SimulationSlot(
            onNextRequest: {
                let ctrl = controller // capture safely
                Task {
                    let state = self.iterator.next()

                    switch state {
                    case .start(let code):
                        self.code = code

                    case .pointers(let low, let high, let mid, let code):
                        ctrl.movePointer(label: "low", index: low)
                        ctrl.movePointer(label: "high", index: high)
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
