//
//  InsertionSortRoute.swift
//  AlgorithmsVisualizer
//
//  Created by Md Khalekuzzaman on 10/10/25.
//


import SwiftUI
import CoreUI

public struct InsertionSortRoute: View {
    private let tag = "InsertionSortRoute"
    
    @State private var code: String? = nil

    @StateObject private var controller: ArrayControllerImpl
    private let iterator: InsertionSortIterator

    public init(array: [Int]) {
        _controller = StateObject(
            wrappedValue: ArrayControllerImpl(
                itemLabels: array.map(\.description),
                pointerLabels: ["i", "j", "j+1"]
            )
        )

        iterator = InsertionSortIterator(array: array)
    }
    
    public var body: some View {
        SimulationSlot(
            onNextRequest: {
                let ctrl = controller
                Task {
                    let st = self.iterator.next()
                    
                    switch st {
                    case .start(let code):
                        self.code = code
                        ctrl.movePointer(label: "i", index: 1)
                        ctrl.movePointer(label: "j", index: 1)
                        
                    case .pointers(let i, let j, let code):
                        ctrl.movePointer(label: "i", index: i)
                        if let j = j {
                            ctrl.movePointer(label: "j", index: j)
                            if j > 0 {
                                ctrl.movePointer(label: "j-1", index: j - 1)
                            }
                        }
                        self.code = code
                        
                    case .swapped(let i, let j, let array, let code):
                        await ctrl.swap(i: i, j: j, delay: 100)
                        self.code = code
                        
                    case .none(let code):
                        self.code = code
                        
                    case .finished(let code):
                        self.code = code
                        ctrl.hidePointer(label: "i")
                        ctrl.hidePointer(label: "j")
                        ctrl.hidePointer(label: "j-1")
                    }
                }
            },
            
            onResetRequst: {
                controller.reset()
                controller.movePointer(label: "i", index: 1)
                controller.movePointer(label: "j", index: 1)
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
