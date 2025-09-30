
import SwiftUI
import CoreUI
public struct Register:View{
    @State private var username = ""
    @State private var showSheet=false
    public init(){}
    public var body: some View{
        
        Column{
            TextView(
                text:"This name will identify you to nearby devices. Choose a unique name. If the among group multiple device has the same may cause data loss and unwanted behavior",
                fontSize : 16,
                
            )
            SpacerVertical(height: 32)
            _Image().onTapGesture {
                    showSheet=true
                }
            SpacerVertical(height: 16)
            
            TextFieldView(
                value: $username,
                hints: "Username",
                leadingIcon:{
                    Image(systemName: "person.fill")
                        .font(.system(size: 20))
                        .foregroundColor(.blue)
                }
                
            )
            
            SpacerVertical(height: 64)
            ButtonViewSolid(
                label:"Register",
                shape:AnyView(RoundedRectangle(cornerRadius:10)),
                paddingHorizontal: 8,
                paddingVertical: 8,
                fontSize:25,
                minWidth:250,
                onClick: {}
            )
            
            
        }
        BottomSheet(show: $showSheet,onDismiss: {
            showSheet=false
        })
      
     
        
        
    }
    
}

struct _Image: View {
    private let imageSize:CGFloat=100
    private let iconSize:CGFloat=30


    
    var body: some View {
        ZStack{
            ImageView(
                image: .profile,
                size: imageSize,
                shape: Circle()
            )
            
            ZStack{
                IconView(
                    icon: "camera",
                    size:iconSize
                ).foregroundColor(Color.white)
            }
            .background(
                RoundedRectShape(radius: iconSize/2,color:Color.yellow))
            
            .position(x: imageSize, y: imageSize/2)
        }.size(value: 100)
           
        
        
        
        
    }
}

 struct BottomSheet: View {
    var show: Binding<Bool>
    var onDismiss:  () ->Void
    

     var body: some View {
         
         ZStack{}
        .sheet(isPresented: show,onDismiss: onDismiss) {
            VStack{
                TextView(
                    text:"Upload Profile Photo",
                    fontSize: 20
                )
                    .fillMaxWidth(alignment: .topLeading)
                    .padding()
                
                _Actions()
                
            }
            
            .presentationDetents([.height(150)])
            .presentationDragIndicator(.visible)
        
           
        }
        
    }
}
struct _Actions: View {
    var body: some View {
        HStack{
            
            _BottomSheetItem(
                icon:"camera",
                label: "Upload From Camera",
                onClick: {}
            )
            _BottomSheetItem(
                icon:"folder",
                label: "Upload From Device",
                
                onClick: {}
            )
        }
     
      
        
    }
}
struct _BottomSheetItem :View {
     var icon:String
     var label:String
     var onClick: ()->Void
    
    var body: some View {
        Column{
            IconView(
                icon: icon,
                size: 48,
                shape: Circle(),
                tint: Color.white
            )
            .background(
                CircleShape(color:Color.blue)
            )
                .onTapGesture {
                onClick()
            }
            SpacerVertical(4)
            TextView(text:label)
            
        }
    }
}



