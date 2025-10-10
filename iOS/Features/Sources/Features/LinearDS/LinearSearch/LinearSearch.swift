
import SwiftUI
import CoreUI

public struct LinearSearchRoute:View {
    private let tag="LinearSearchRoute"
    @State var code:String? = nil
    @State var next=0
    @StateObject var controller = ArrayControllerImpl(
        itemLabels: ["10", "20", "30", "40","50","60","70","80"],
        pointerLabels: ["i"]
    )

    let iterator = LinearSearchIterator(array: [10, 20, 30,40,50,60,70,80], target: 90)


    public init(){}
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
