
import SwiftUI
import SwiftUI
import CoreUI
import NearByMessenger


@main
struct SwiftUIConceptApp: App {
    var body: some Scene {
        WindowGroup {
           // ContentView()
            NearByMessengerNavHost()
        }
    }
}


struct ContentView: View {
    var body: some View {
        NearByMessengerNavHost()
    }
}

#Preview {
   NearByMessengerNavHost()
   //ProfileNavHost()
   // Demo()
  
  
}

struct Demo: View {
    
    var body: some View {
        
        VStack{
            VStack{
                Text("Upload Profile Photo")
                
               // Spacer()
                
                SubView()
                
            }
            .frame(height: 300)
            .background(Color.green.opacity(0.5))
        }
        .fillMaxHeight()
        .background(Color.blue.opacity(0.3))
    
     
        
    }
}


struct SubView: View {
    var body: some View {
        ZStack{
            Color.clear
        }
        .frame(height: 70)
        .background(Color.red)
    }
}
