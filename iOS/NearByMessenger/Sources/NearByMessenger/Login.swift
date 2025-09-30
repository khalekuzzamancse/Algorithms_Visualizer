
import SwiftUI
import CoreUI
public struct LoginScreen:View {
    @State private var username = ""
    @State private var password = ""
   @State private var password_ = ""
  
    public init(){}
    public var body: some View {
        
        Column{
            userNameField
            SpacerVertical(32)
            passwordField
           
        }.padding(10)
      
    }
    
    var userNameField: some View{
        TextFieldView(
            value: $username,
            hints: "Phone number",
            cornerRadius: 10,
            leadingIcon:{
                Image(systemName: "person.fill")
                    .font(.system(size: 20))
                    .foregroundColor(.blue)
            },
            onValueChange:{value in

                username=value.filter{char in
                   char=="-"||char.isNumber || char=="+"||char.isWhitespace
                    
                }
                username=if(value=="+880"){
                    "\(username)-"
                }
                else {username}
            },
            
            
        )
        
        
    }
    var passwordField: some View{
        TextFieldView(
            value: $password,
            hints: "Password",
            cornerRadius: 10,
            leadingIcon:{
                Image(systemName: "lock.fill")
                    .font(.system(size: 20))
                    .foregroundColor(.blue)
            },
            onValueChange:{value in
                password_=value.filter{char in
                    char.isWhitespace
                }
                
                 password = String(value.map { _ in "*" })
                
             
             
            },
            
            
        )
        
        
    }
}
