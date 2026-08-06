//
//  SelectionSortRoute.swift
//  AlgorithmsVisualizer
//
//  Created by Md Khalekuzzaman on 10/9/25.
//


import SwiftUI
import CoreUI

public struct SelectionSortRoute: View {
    private let tag = "SelectionSortRoute"


       @State private var code: String? = nil

       @StateObject private var controller: ArrayControllerImpl
       private let iterator: SelectionSortIterator

       public init(array: [Int]) {
           _controller = StateObject(
               wrappedValue: ArrayControllerImpl(
                   itemLabels: array.map(\.description),
                   pointerLabels: ["i", "min"]
               )
           )

           iterator = SelectionSortIterator(array: array)
       }
    
    public var body: some View {
        SimulationSlot(
            onNextRequest: {
                let ctrl = controller
                Task {
                    let st = iterator.next()
                    
                    switch st {
                    case .start(let code):
                        self.code = code
                        
                    case .pointers(let i, let minIndex, let code):
                        ctrl.movePointer(label: "i", index: i)
                        
                        if(minIndex != nil){
                          
                            ctrl.movePointer(label: "min", index: minIndex!)
                        }
                        
                        self.code = code
                        
                    case .swapped(let i, let j, let array, let code):
                        
                        await ctrl.swap(i: i, j: j, delay: 100)
        
                        self.code = code
                        ctrl.hidePointer(label: "min")
                        
                    case .none(let code):
                        ctrl.hidePointer(label: "min")
                        self.code = code
                        
                    case .finished(let code):
                        self.code = code
                        ctrl.hidePointer(label: "i")
                        ctrl.hidePointer(label: "min")
                    }
                }
            },
            
            onResetRequst: {
                controller.reset()
                controller.movePointer(label: "i", index: 0)
                controller.movePointer(label: "min", index: 0)
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
