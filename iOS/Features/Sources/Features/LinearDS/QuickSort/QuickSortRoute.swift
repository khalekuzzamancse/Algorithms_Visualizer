//
//  QuickSortRoute.swift
//  AlgorithmsVisualizer
//
//  Created by Md Khalekuzzaman on 10/10/25.
//


import SwiftUI
import CoreUI

public struct QuickSortRoute: View {
    private let tag = "QuickSortRoute"
    
    @State private var code: String? = nil

    @StateObject private var controller: ArrayControllerImpl
    private let simulator: QuickSortSimulatorImpl

    public init(array: [Int]) {
        _controller = StateObject(
            wrappedValue: ArrayControllerImpl(
                itemLabels: array.map(\.description),
                pointerLabels: ["low", "high", "i", "j", "pivot"]
            )
        )

        simulator = QuickSortSimulatorImpl(list: array)
    }
    
    public var body: some View {
        SimulationSlot(
            onNextRequest: {
                let ctrl = controller
                Task {
                    let state = simulator.next()
                    
                    switch state {
                    case .finish:
                        ctrl.hidePointer(label: "low")
                        ctrl.hidePointer(label: "high")
                        ctrl.hidePointer(label: "i")
                        ctrl.hidePointer(label: "j")
                        ctrl.hidePointer(label: "pivot")
                       
                        
                    case .state(let low, let high, let pivot, let swapped, let i, let j):
                        // Move pointers if available
                        ctrl.movePointer(label: "low", index: low)
                        ctrl.movePointer(label: "high", index: high)
                        
                        if let i = i, i >= 0 {
                            ctrl.movePointer(label: "i", index: i)
                        }
                        if let j = j, j >= 0 {
                            ctrl.movePointer(label: "j", index: j)
                        }
                      
                      
                        
                        // Perform swap if needed
                        if let swap = swapped {
                            await ctrl.swap(i: swap.0, j: swap.1, delay: 200)
                        }
                        
                    
                    }
                }
            },
            
            onResetRequst: {
                controller.reset()
                controller.movePointer(label: "low", index: 0)
                controller.movePointer(label: "high", index: 4)
            },
            
            onAutoPlayRequest: {
               // simulator.next()
              return  false
            },
            
            pseudocode: $code,
            
            visualization: {
                ArrayView(controller: controller)
            }
        )
    }
}
