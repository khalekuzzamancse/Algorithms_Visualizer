

import SwiftUI
import CoreUI
import UIKit

#Preview {
    HomeScreen(){_ in }
}
public enum Routes: Codable, Hashable {
 case LinearSearch
 case BinarySearch
 case BubbleSort
 case SelectionSort
 case InsertionSort
 case QuickSort

}
struct HomeScreen:View {
    let onSelect: (Routes) -> Void
    private let features=[
        "Visualie algorithms step-by-step",
        "Custom input for different data scenarious",
        "Adjustable visualization speed"
        
    ]
    init(onSelect: @escaping (Routes) -> Void) {
        self.onSelect = onSelect
    }
    var body: some View {
        
        VStack(
        
        ){
            TextH1(text: "Welcome to Algorithms Visualizer")
            SpacerVertical(32)
            TextBody1(text: "Explore and understand the common algorithms taught in Data Structures and Algorithms courses throgh interactive visualizations.")
            SpacerVertical(32)
            TextH2(text: "Current Features")
                .fillMaxWidth(alignment: .leading)

            
            HStack{
                SpacerHorizontal(8)
                VStack{
                    ForEach(features,id:\.self){feature in
                        TextPoint(text:feature).fillMaxWidth(alignment: .leading)
                    }
                    
                }
                
            }
           
            SpacerVertical(32)
            Divider()
            TextH2(text: "Visualize")
                .fillMaxWidth(alignment: .leading)
            _NavigationItems(onSelect:onSelect)
            
        }
        .padding()
        .fillMaxHeight(alignment:.top)


        
    }
}


struct _NavigationItems: View {
    let onSelect: (Routes) -> Void
    let items = [
        _GridItemData("photo.badge.magnifyingglass","Linear Search",.LinearSearch),
        _GridItemData("photo.badge.magnifyingglass","Binary Search",.BinarySearch ),
        _GridItemData("arrow.up.arrow.down.square","Bubble Sort",.BubbleSort),
        _GridItemData("rectangle.and.hand.point.up.left","Selection Sort",.SelectionSort),
        _GridItemData("arrow.down.backward.toptrailing.rectangle","Insertion Sort",.InsertionSort),
        _GridItemData("arrow.left.and.right.square","Quick Sort",.QuickSort ),
    ]
    
    var body: some View {
      
        EqualWidthRowLayout{
            ForEach(items) { item in
                    _GridItem(item: item) {route in
                        
                        onSelect(route)
                    }
    
                  
            }
            
        }
     }
       
        
    }






struct _GridItemData:Identifiable{
    var id: String { name }
    let icon:String
    let name:String
    let route:Routes
    init(_ icon: String, _ name: String, _ route:Routes) {
        self.icon = icon
        self.name = name
        self.route = route
        
    }
    
}
struct _GridItem: View {
    let item: _GridItemData
    let onClick: (Routes)->Void
    var body: some View {
        HStack{
            IconView(icon: item.icon, size: 30,tint: .white)
            SpacerHorizontal(4)
            TextView(text: item.name,color: .white)
        }
        .padding(8)
        .fillMaxWidth()
        .background(
            RoundedRectShape(radius:12, color:.blue)
        ).onTapGesture {
            onClick(item.route)
        }
       
    }
}




struct CustomLayout:Layout{
    func sizeThatFits(proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) -> CGSize {
        
        let maxWidthAvailable = proposal.width ?? .infinity
               
               // Step 1: Find the maximum width among ALL children
               let maxChildWidth = subviews.map { subview in
                   subview.sizeThatFits(.unspecified).width
               }.max() ?? 0
        
        
        return CGSize.zero
    
    }
    
    func placeSubviews(in bounds: CGRect, proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) {
        
        
    }
    
    
}
struct EqualWidthRowLayout: Layout {
    var spacing: CGFloat = 12

    func sizeThatFits(proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) -> CGSize {
        let maxWidthAvailable = proposal.width ?? .infinity
        
        // Step 1: Find the maximum width among ALL children
        let maxChildWidth = subviews.map { subview in
            subview.sizeThatFits(.unspecified).width
        }.max() ?? 0
        
        // Step 2: Calculate how many children can fit in a row
        let itemsPerRow = calculateItemsPerRow(
            maxWidthAvailable: maxWidthAvailable,
            itemWidth: maxChildWidth,
            spacing: spacing
        )
        
        // Step 3: Calculate total height based on equal-width layout
        let totalRows = ceil(CGFloat(subviews.count) / CGFloat(itemsPerRow))
        let rowHeight = calculateRowHeight(subviews: subviews, itemWidth: maxChildWidth)
        let totalHeight = totalRows * rowHeight + (totalRows - 1) * spacing
        
        return CGSize(width: maxWidthAvailable, height: totalHeight)
    }
    func placeSubviews(in bounds: CGRect, proposal: ProposedViewSize, subviews: Subviews, cache: inout ()) {
        let maxWidthAvailable = bounds.width
        
        let maxChildWidth = subviews.map { $0.sizeThatFits(.unspecified).width }.max() ?? 0
        let itemsPerRow = calculateItemsPerRow(maxWidthAvailable: maxWidthAvailable, itemWidth: maxChildWidth, spacing: spacing)
        let totalSpacing = CGFloat(itemsPerRow - 1) * spacing
        let availableForItems = maxWidthAvailable - totalSpacing
        let itemWidth = min(maxChildWidth, availableForItems / CGFloat(itemsPerRow))
        let rowHeight = calculateRowHeight(subviews: subviews, itemWidth: itemWidth)
        
        var y = bounds.minY
        var currentIndex = 0
        var lastShift: CGFloat = 0 // Track the shift from the last fully-filled row
        
        while currentIndex < subviews.count {
            let rowEndIndex = min(currentIndex + itemsPerRow, subviews.count)
            let rowSubviews = Array(subviews[currentIndex..<rowEndIndex])
            
            let totalRowWidth = CGFloat(rowSubviews.count) * itemWidth + CGFloat(rowSubviews.count - 1) * spacing
            
            // Compute shift only if row is fully filled, otherwise reuse lastShift
            let shift: CGFloat
            if rowSubviews.count == itemsPerRow {
                shift = (maxWidthAvailable - totalRowWidth) / 2
                lastShift = shift
            } else {
                shift = lastShift
            }
            
            var x = bounds.minX + shift
            
            for subview in rowSubviews {
                let childSize = subview.sizeThatFits(ProposedViewSize(width: itemWidth, height: nil))
                
                subview.place(
                    at: CGPoint(x: x, y: y),
                    proposal: ProposedViewSize(width: itemWidth, height: childSize.height)
                )
                x += itemWidth + spacing
            }
            
            y += rowHeight + spacing
            currentIndex += itemsPerRow
        }
    }




    
    private func calculateItemsPerRow(maxWidthAvailable: CGFloat, itemWidth: CGFloat, spacing: CGFloat) -> Int {
        guard itemWidth > 0 else { return 1 }
        
        // Calculate how many items can fit considering spacing
        let itemsWithoutSpacing = Int(floor(maxWidthAvailable / itemWidth))
        let itemsWithSpacing = Int(floor((maxWidthAvailable + spacing) / (itemWidth + spacing)))
        
        // Use the more conservative estimate that accounts for spacing
        return max(1, itemsWithSpacing)
    }
    
    private func calculateRowHeight(subviews: Subviews, itemWidth: CGFloat) -> CGFloat {
        return subviews.map { subview in
            subview.sizeThatFits(ProposedViewSize(width: itemWidth, height: nil)).height
        }.max() ?? 0
    }
}

struct TextH1 : View{
    let text:String
    
    var body: some View{
        
        TextView(
            text:text,
            color:Color.blue,
            fontSize: 22,
            
        )
        
        
    }
    
    
}
struct TextH2 : View{
    let text:String
    
    var body: some View{
        
        TextView(
            text:text,
            color:Color.blue,
            fontSize: 20,
            
        )
        
        
    }
    
    
}

struct TextBody1 : View{
    let text:String
    
    var body: some View{
  
        TextView(
            text:text,
            fontSize: 15,
        
            
        )
        
    }
    
    
}



struct TextPoint: View {
    var text: String
    var bulletSize: CGFloat = 8
    
    
    var body: some View {
        let fontSize:CGFloat = 16
        HStack(alignment: .top, spacing: 4) {
            let font = UIFont.systemFont(ofSize: fontSize)
            let lineHeight = font.lineHeight
            let shiftDown = (lineHeight - bulletSize) / 2
            
            Circle()
                .fill(Color.accentColor)
                .size(value:bulletSize)
                .offset(y: shiftDown)
            
                .size(value:bulletSize)
            
            Text(text)
                .font(.system(size: fontSize))
                .foregroundColor(Color.primary)
                .lineSpacing(4) // roughly matches 20.sp line height
                .multilineTextAlignment(.leading)
        }
    }
}


