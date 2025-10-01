
import SwiftUI
import CoreUI

public struct LinearSearchRoute:View {
    private let tag="LinearSearchRoute"
    @State var cnt=0
    @State var next=0
    @StateObject var controller = ArrayControllerImpl(
        itemLabels: ["10", "20", "30", "40","50","60","70","80"],
        pointerLabels: ["i"]
    )

    public init(){}
   public var body: some View {
    
       
       SimulationSlot(
           onNextRequest:{
               next+=1
               let ctrl = controller  // capture safely
               let cellLen=controller.cells.capacity
               Task {
                   ctrl.movePointer(label: "i", index: cnt%cellLen)
                   cnt+=1
                
                 
               }
             
           },
           onResetRequst:{
               print("\(tag):onResetClick")
               controller.hidePointer(label: "i")
           },
           onAutoPlayRequest:{
               
//               let ctrl = controller  // capture safely
//               Task {
//                   await ctrl.swap(i: 0, j: 1, delay: 0)
//                 
//               }
               next<10
               
               
           }
           , visualization:{
               ArrayView(controller: controller)
               
           }
       )
      
    }
}
