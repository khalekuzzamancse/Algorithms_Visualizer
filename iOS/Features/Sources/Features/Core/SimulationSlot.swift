
import SwiftUI
import CoreUI

public struct SimulationSlot<Content: View>: View {
    private let onNextRequest: () -> Void
    private let onResetRequst: () -> Void
    private let hasNext: () -> Bool
    private let peudocode: String?
    let visualization: () -> Content

    @State private var showAutoPlayDialog = false
    @State private var autoPlayDelayInput = ""
    @State private var autoPlayTask: Task<Void, Never>? = nil

    public init(
        onNextRequest: @escaping () -> Void,
        onResetRequst: @escaping () -> Void,
        onAutoPlayRequest: @escaping () -> Bool,
        peudocode: String? = nil,
        @ViewBuilder visualization: @escaping () -> Content
    ) {
        self.onNextRequest = onNextRequest
        self.onResetRequst = onResetRequst
        self.hasNext = onAutoPlayRequest
        self.visualization = visualization
        self.peudocode = peudocode
    }

    public var body: some View {
        NavigationStack {
            ZStack {
                VStack {
                    visualization()
                      
                      //  .background(Color.green)
                    CodeViewer(code: randomString(n: 900))
                }
                .toolbar {
                    ToolbarItemGroup(placement: .navigationBarTrailing) {
                        IconView(icon: SFIcons.next)
                            .onTapGesture(perform: onNextRequest)
                        Spacer().frame(width: 8)
                        IconView(icon: SFIcons.reload)
                            .onTapGesture(perform: onResetRequst)
                        Spacer().frame(width: 8)
                        IconView(icon: SFIcons.timer)
                            .onTapGesture {
                                showAutoPlayDialog = true
                            }
                        Spacer().frame(width: 8)
                        IconView(icon: SFIcons.code)
                    }
                }
                
                if showAutoPlayDialog {
                    AutoPlayBlockingDialog(
                        delayInput: $autoPlayDelayInput,
                        onCancel: { showAutoPlayDialog = false },
                        onConfirm: { delayMs in
                            showAutoPlayDialog = false
                            startAutoPlay(withDelay: delayMs)
                        }
                    )
                    .transition(.opacity)
                    .zIndex(1)
                }
            }
            .onDisappear {
                stopAutoPlay()
            }
        }
    }

    private func startAutoPlay(withDelay millis: Int) {
        stopAutoPlay()
        autoPlayTask = Task {
            while !Task.isCancelled {
                if(hasNext()==false){
                    break
                }
                try? await Task.sleep(nanoseconds: UInt64(millis) * 1_000_000)
                if Task.isCancelled { break }
                onNextRequest()
            }
        }
    }

    private func stopAutoPlay() {
        autoPlayTask?.cancel()
        autoPlayTask = nil
    }
}

struct AutoPlayBlockingDialog: View {
    @Binding var delayInput: String
    var onCancel: () -> Void
    var onConfirm: (Int) -> Void
    @State private var showInvalidInputAlert = false

    var body: some View {
        Color.black.opacity(0.4)
            .edgesIgnoringSafeArea(.all)
            .overlay(
                VStack(spacing: 20) {
                    Text("Set Auto Play Time")
                        .font(.headline)

                    TextField("Delay in ms", text: $delayInput)
                        .keyboardType(.numberPad)
                        .padding()
                        .background(Color(UIColor.systemGray6))
                        .cornerRadius(8)
                        .frame(width: 250)

                    HStack {
                        Button("Cancel", action: onCancel)
                            .frame(minWidth: 100)
                            .padding()
                            .background(Color.gray.opacity(0.3))
                            .cornerRadius(8)

                        Button("Confirm") {
                            if let delay = Int(delayInput), delay > 0 {
                                onConfirm(delay)
                            } else {
                                showInvalidInputAlert = true
                            }
                        }
                        .frame(minWidth: 100)
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                    }
                }
                .padding()
                .background(Color(UIColor.systemBackground))
                .cornerRadius(12)
                .shadow(radius: 10)
                .alert("Invalid input", isPresented: $showInvalidInputAlert) {
                    Button("OK", role: .cancel) {}
                } message: {
                    Text("Please enter a valid positive integer for the delay.")
                }
            )
    }
}

func randomString(n: Int) -> String {
    let letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 "
    return String((0..<n).map { _ in letters.randomElement()! })
}



//import SwiftUI
//import CoreUI
//
//public struct SimulationSlot<Content: View>: View {
//    
//    private let onNextRequest:   ()->Void
//    private let onResetRequst:   ()->Void
//    private let onAutoPlayRequest:  (Int)->Void
//    private let peudocode:String?
//    let visualization: () -> Content
//    
//   public init(
//        onNextRequest: @escaping () -> Void,
//        onResetRequst: @escaping () -> Void,
//        onAutoPlayRequest: @escaping (Int) -> Void,
//        peudocode:String? = nil,
//        @ViewBuilder visualization: @escaping () -> Content
//        
//   ) {
//        self.onNextRequest = onNextRequest
//        self.onResetRequst = onResetRequst
//        self.onAutoPlayRequest = onAutoPlayRequest
//        self.visualization = visualization
//        self.peudocode = peudocode
//    }
//    
//
//
//   public var body: some View {
//       NavigationStack{
//           VStack {
//               
//               visualization()
//                   .height(200)
//                   .background(Color.green)
//               CodeViewer(code:randomString(n:900))
//            
//              
//               
//           }
//           
//           .toolbar {
//           
//               ToolbarItemGroup(placement: .navigationBarTrailing) {
//                       IconView(icon: SFIcons.next)
//                       .onTapGesture(perform: onNextRequest)
//                       SpacerHorizontal(8)
//                       IconView(icon: SFIcons.reload)
//                       .onTapGesture(perform: onResetRequst)
//                       SpacerHorizontal(8)
//                       IconView(icon: SFIcons.timer)
//                       .onTapGesture(perform: {
//                           onAutoPlayRequest(1000)
//                       })
//                       SpacerHorizontal(8)
//                       IconView(icon: SFIcons.code)
//                      
//                   }
//               
//           }
//            
//        }
//       
//    }
//}
//
//
//func randomString(n: Int) -> String {
//    let letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 "
//    return String((0..<n).map { _ in letters.randomElement()! })
//}
//
