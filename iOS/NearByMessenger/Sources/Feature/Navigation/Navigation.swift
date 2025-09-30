
import SwiftUI
import CoreUI

public struct HomeScreen:View{
    
    public init(){
        
    }
   public var body: some View{
        Column{
            _Title()
            _Introduction()
            _FeatureSection(
                title : "Current Features",
                symbol: "✔",
                items:[
                    "Chat using Bluetooth",
                    "Update and Edit Profile that will read by Peer",
                    "Image share",
                    "Light theme support only",
                ]
            )
            _FeatureSection(
                title : "Up Coming Features",
                symbol:"⏳",
                items:[
                    "Chat and File Share Using Wifi-Direct",
                    "Chat and File Share Using WiFi-Hotspot",
                    "Dark theme support",
                ]
            )
            
        }.padding(4)
        
    }
}

struct _Title:View {
    var body: some View {
        TextView(
            text:"NearBy Messenger",
            fontSize: 28,
            fontWeight: .bold,
            
            
        )
        
    }
}

struct _Introduction:View {
    var body: some View {
        TextView(
            text:"A local Chat app that works without internet connection",
            fontSize: 16,
            justifyContent: true
            
        )
        .fillMaxWidth()
       
    }
}

struct _FeatureSection:View {
    let title:String
    let symbol:String
    let items: Array<String>
    var body: some View {
        
        Column{
            TextView(
                text:title,
                fontSize: 22,
                
            )
            SpacerVertical(height: 8)

            ForEach(items.indices, id: \.self) { index in
                Group {
                    _FeatureItem(
                        symbol: symbol,
                        text: items[index]
                    )
                    if (index != items.endIndex ){
                        SpacerVertical(height: 8)
                    }
                }
            }
        }
        
    }
}

struct _FeatureItem:View {
    let symbol:String
    let text:String
    var body: some View {
        Row{
            TextView(
                text:symbol,
                fontSize: 16,
            )
            TextView(
                text:text,
                fontSize: 16,
            )
         
        }.fillMaxWidth()
    }
}


#Preview {
    HomeScreen()
}

