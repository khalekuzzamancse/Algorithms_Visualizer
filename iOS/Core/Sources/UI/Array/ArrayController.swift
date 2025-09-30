import SwiftUI

protocol VisualArrayController: ObservableObject {
    var pointerPosition: CGPoint? { get set }
    var allCellPlaced: Bool { get set }
    var cells: [Cell] { get set }
    var elements: [Element] { get set }
    var pointers: [Pointer] { get set }
    
    var numberOfElements: Int { get }
    
    func onCellPositionChanged(index: Int, position: CGPoint)
    func changeElementPosition(index: Int, position: CGPoint)
    func swap(i: Int, j: Int, delay: TimeInterval) async
    func movePointer(label: String, index: Int)
    func hidePointer(label: String)
    func changeElementColor(index: Int, color: Color)
    func removePointers(labels: [String])
    func changeCellColorUpTo(index: Int, color: Color)
    func changeCellColor(index: Int, color: Color)
}



@Observable
class ArrayControllerImpl : VisualArrayController{
    
   
    var pointerPosition: CGPoint? = nil
    
    var allCellPlaced: Bool = false
    
    var cells: [Cell] = []
    
    var elements: [Element]
    
    var pointers: [Pointer]
    
    //
    
    var numberOfElements: Int
    
    public init(
        itemLabels:[String],
        pointerLabels:[String]
        
    ){
        cells = itemLabels.enumerated().map { index, _ in
            Cell(index: index, color: .blue)
        }
        elements=itemLabels.enumerated().map{index,label in
            Element(
                position:.zero,
                color:Color.red,
                label:label,

            )
            
        }
        self.pointers = pointerLabels.map { label in
            Pointer(label: label, position: nil)
               }
               
        self.numberOfElements = itemLabels.count

        
    }
    
    func onCellPositionChanged(index: Int, position: CGPoint) {
        
        
        print("onCellPositionChanged,:\(index),\(position)")
        let oldCell = cells[index]
           let newCell = oldCell.copy(position: position)
           cells[index] = newCell
           
           if index < elements.count {
               let oldElement = elements[index]
               let newElement = oldElement.copy(position: position)
               elements[index] = newElement
           }
        
    }
    func changeElementPosition(index: Int, position: CGPoint) {
        guard index < elements.count else { return }
        let oldElement = elements[index]
        let newElement = oldElement.copy(position: position)
        elements[index] = newElement
    }

    func swap(i: Int, j: Int, delay: TimeInterval) async {
        guard i < elements.count, j < elements.count else { return }

        // Swap positions
        let posI = elements[i].position
        let posJ = elements[j].position

        // Optional: animate delay
        try? await Task.sleep(nanoseconds: UInt64(delay * 1_000_000_000))

        elements[i] = elements[i].copy(position: posJ)
        elements[j] = elements[j].copy(position: posI)
    }

    func movePointer(label: String, index: Int) {
        guard index < cells.count else { return }
        let targetCell = cells[index]
        
        if let pointerIndex = pointers.firstIndex(where: { $0.label == label }) {
            let oldPointer = pointers[pointerIndex]
            // Animate movement by just updating the position
            let newPointer = oldPointer.copy(position: targetCell.position)
            pointers[pointerIndex] = newPointer
        }
    }

    func hidePointer(label: String) { }
    func changeElementColor(index: Int, color: Color) { }
    func removePointers(labels: [String]) { }
    func changeCellColorUpTo(index: Int, color: Color) { }
    func changeCellColor(index: Int, color: Color) { }
    
    
}





struct Cell: Equatable {
    let index: Int
    let position: CGPoint
    let elementId: Int?
    let color: Color
    
    init(index: Int, position: CGPoint = .zero, elementId: Int? = nil, color: Color) {
        self.index = index
        self.position = position
        self.elementId = elementId
        self.color = color
    }
    
    func copy(
        index: Int? = nil,
        position: CGPoint? = nil,
        elementId: Int? = nil,
        color: Color? = nil
    ) -> Cell {
        Cell(
            index: index ?? self.index,
            position: position ?? self.position,
            elementId: elementId ?? self.elementId,
            color: color ?? self.color
        )
    }
}

struct Element: Equatable, CustomStringConvertible {
    let position: CGPoint
    let color: Color
    let label: String
    
    var description: String {
        "(\(label): \(position.x), \(position.y))"
    }
    
    func copy(
        label: String? = nil,
        position: CGPoint? = nil,
        color: Color? = nil
    ) -> Element {
        Element(
            position: position ?? self.position,
            color: color ?? self.color,
            label: label ?? self.label
        )
    }
}

struct Pointer: Equatable,Hashable {
    let label: String
    let position: CGPoint?
    
    func copy(label: String? = nil, position: CGPoint? = nil) -> Pointer {
        Pointer(label: label ?? self.label, position: position ?? self.position)
    }
}


