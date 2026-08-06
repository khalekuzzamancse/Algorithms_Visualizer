
import SwiftUI
import CoreUI

public struct LinearSearchRoute:View {
    private let array: [Int]
       private let target: Int
       private let tag = "LinearSearchRoute"

       @State private var code: String? = nil
       @State private var next = 0

       @StateObject private var controller: ArrayControllerImpl
       private let iterator: LinearSearchIterator

       public init(array: [Int], target: Int) {
           self.array = array
           self.target = target

           _controller = StateObject(
               wrappedValue: ArrayControllerImpl(
                   itemLabels: array.map(\.description),
                   pointerLabels: ["i"]
               )
           )

           iterator = LinearSearchIterator(
               array: array,
               target: target
           )
       }
     public var body: some View {
    
       
       SimulationSlot(
           onNextRequest:{
               let ctrl = controller  // capture safely
               Task {
                    let state = self.iterator.next()
                   
                       switch state {
                       case .start(let code):
                           self.code=code
                       case .pointerI(let index, let code):
                           ctrl.movePointer(label: "i", index:index)
                           self.code=code
                       case .foundAt(let index, let code):
                           self.code=code
                           ctrl.changeCellColor(index: index, color: Color.green)
                               ctrl.hidePointer(label: "i")
                           
                       case .finished(let code):
                           self.code=code
                           ctrl.hidePointer(label: "i")
                       }
                   
                
                 
               }
             
           },
           onResetRequst:{
               controller.reset()
              
           },
           onAutoPlayRequest:{
               iterator.hasNext()
           },
           pseudocode: $code,
           visualization:{
               ArrayView(controller: controller)
        
               
           }
       )
      
    }
}
