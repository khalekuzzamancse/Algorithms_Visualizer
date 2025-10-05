//// MARK: - Simulation State
public enum BinarySearchST {
    case start(String)
    case pointers(low: Int?, high: Int?, mid: Int?, code: String)
    case foundAt(index: Int, code: String)
    case finished(String)
}


 class BinarySearchIterator: SearchIteratorBase {
    
   private var label="1"

    
    override func next() -> BinarySearchST {
        if isFinished { return onFinished() }

        switch step {
        case .start:
            return onStart()
        case .search:
            return searchNext()
        case .found:
            step = .finished
            return .foundAt(index: mid!, code: Code.generate(model))
        case .finished:
            return onFinished()
        }
    }

  
    private func searchNext() -> BinarySearchST {
    
        guard low <= high else {
            step = .finished
            model = model.copy(returnIndex: -1)
            return .finished(Code.generate(model))
        }
    
        switch(label){
            
        case "1":  mid = low + (high - low) / 2
                   label="2"
            model = model.copy( mid: mid, current: array[mid!])
            return .pointers(low: low, high: high, mid: mid, code: Code.generate(model))
            
        case "2":   if( array[mid!] == target) {
                step = .found
                 model = model.copy(low: low, high: high, mid: mid, current: array[mid!], isFound: true, returnIndex: mid)
            label="xx"
            return  .foundAt(index:mid!, code:Code.generate(model))
                     }
            else {
                label="3"
                return .start(Code.generate(model))
                
            }
        case "3": if(low<array[mid!]){
            low=mid!+1
            mid=nil
            label="1"
            model = model.copy( low: low,mid:nil,current: nil)
            return .pointers(low: low, high: high, mid: nil, code: Code.generate(model))
        }
            else {
                label="4"
                return .start(Code.generate(model))
            }
            
        case "4" : high=mid!-1
                   mid=nil
                label="1"
            model = model.copy( high: high,mid:nil,current: nil)
            return .pointers(low: low, high: high, mid: nil, code: Code.generate(model))
            
        default: return .finished("")
            
        
            
        }
    

        return .start(Code.generate(model))
    }
    
  
    
    
    
    
    private func onStart() -> BinarySearchST {
        step = .search
        return .start(Code.generate(model))
    }

    private func onFinished() -> BinarySearchST {
        isFinished = true
        return .finished(Code.generate(model))
    }
}



class SearchIteratorBase {
    var isFinished = false
    var step: Step = .start
    
    enum Step {
        case start
        case search
        case found
        case finished
    }
    
    func hasNext() -> Bool { !isFinished }
    
    func next() -> BinarySearchST {
        fatalError("Subclasses must override next()")
    }
    
    let array: [Int]
    let target: Int
    
    var low = 0
    var high: Int
    var mid: Int? = nil
    
    var emitLow = false
    var emitMid = false
    var emitHigh = false
    
    var model: CodeStateModel
    
    init(array: [Int], target: Int) {
        self.array = array
        self.target = target
        self.high = array.count - 1
        self.model = CodeStateModel(
            target: target,
            low: nil,
            high: nil,
            mid: nil,
            current: nil,
            isFound: nil,
            currentLessThanTarget: nil,
            currentGreaterThanTarget: nil,
            returnIndex: nil
        )
       
    }
}



// MARK: - Code State Model
public struct CodeStateModel {
    let target: Int
    let low: Int?
    let high: Int?
    let mid: Int?
    let current: Int?
    let isFound: Bool?
    let currentLessThanTarget: Bool?
    let currentGreaterThanTarget: Bool?
    let returnIndex: Int?

    func copy(
        low: Int?? = nil,
        high: Int?? = nil,
        mid: Int?? = nil,
        current: Int?? = nil,
        isFound: Bool?? = nil,
        currentLessThanTarget: Bool?? = nil,
        currentGreaterThanTarget: Bool?? = nil,
        returnIndex: Int?? = nil
    ) -> CodeStateModel {
        return CodeStateModel(
            target: self.target,
            low: low ?? self.low,
            high: high ?? self.high,
            mid: mid ?? self.mid,
            current: current ?? self.current,
            isFound: isFound ?? self.isFound,
            currentLessThanTarget: currentLessThanTarget ?? self.currentLessThanTarget,
            currentGreaterThanTarget: currentGreaterThanTarget ?? self.currentGreaterThanTarget,
            returnIndex: returnIndex ?? self.returnIndex
        )
    }
}

// MARK: - Pseudocode Generator
class Code {
    static func generate(_ model: CodeStateModel) -> String {
        let lowComment = model.low.map { "//low: \($0)" } ?? ""
        let highComment = model.high.map { "//high: \($0)" } ?? ""
        let midComment = model.mid.map { "//mid: \($0)" } ?? ""
        let currentComment = model.current.map { "//current: \($0)" } ?? ""
        let foundComment = model.isFound.map { "//isFound: \($0)" } ?? ""
        let lessComment = model.currentLessThanTarget.map { "//current<target: \($0)" } ?? ""
        let greaterComment = model.currentGreaterThanTarget.map { "//current>target: \($0)" } ?? ""
        let returnComment = model.returnIndex.map { "//returned: \($0)" } ?? ""

        return """
BinarySearch(list, target) { //target: \(model.target)
    low = 0 \(lowComment)
    high = list.count - 1 \(highComment)
    while (low <= high) {
        mid = (low + high)/2 \(midComment)
        current = list[mid] \(currentComment)
        isFound = (current == target) \(foundComment)
        if (isFound)
            return mid
        else if (current < target) \(lessComment)
            low = mid + 1
        else \(greaterComment)
            high = mid - 1
    }
    return -1 \(returnComment)
}
"""
    }
}
//
//// MARK: - Binary Search Iterator
//public class BinarySearchIterator {
//    let array: [Int]
//    let target: Int
//    var low = 0
//    var high: Int
//    var mid: Int? = nil
//    var step: Step = .start
//    var isFinished = false
//    private var model: CodeStateModel
//
//    enum Step {
//        case start
//        case search
//        case found
//        case finished
//    }
//
//    init(array: [Int], target: Int) {
//        self.array = array
//        self.target = target
//        self.high = array.count - 1
//        self.model = CodeStateModel(target: target, low: nil, high: nil, mid: nil, current: nil, isFound: nil, currentLessThanTarget: nil, currentGreaterThanTarget: nil, returnIndex: nil)
//    }
//
//    func hasNext() -> Bool {
//        return !isFinished
//    }
//
//    func next() -> BinarySearchST {
//        if isFinished { return onFinished() }
//
//        switch step {
//        case .start:
//            return onStart()
//        case .search:
//            return searchNext()
//        case .found:
//            step = .finished
//            return .foundAt(index: mid!, code: Code.generate(model))
//        case .finished:
//            return onFinished()
//        }
//    }
//
//    private func searchNext() -> BinarySearchST {
//        guard low <= high else {
//            step = .finished
//            model = model.copy(returnIndex: -1)
//            return .finished(Code.generate(model))
//        }
//
//        mid = (low + high) / 2
//        let currentValue = array[mid!]
//
//        if currentValue == target {
//            step = .found
//            model = model.copy(low: low, high: high, mid: mid, current: currentValue, isFound: true, returnIndex: mid)
//            return .pointerMid(index: mid!, code: Code.generate(model))
//        } else if currentValue < target {
//            let oldMid = mid!
//            low = mid! + 1
//            model = model.copy(low: low, high: high, mid: oldMid, current: currentValue, isFound: false, currentLessThanTarget: true, currentGreaterThanTarget: false)
//            return .pointerLow(index: low, code: Code.generate(model))
//        } else {
//            let oldMid = mid!
//            high = mid! - 1
//            model = model.copy(low: low, high: high, mid: oldMid, current: currentValue, isFound: false, currentLessThanTarget: false, currentGreaterThanTarget: true)
//            return .pointerHigh(index: high, code: Code.generate(model))
//        }
//    }
//
//    private func onStart() -> BinarySearchST {
//        step = .search
//        return .start(Code.generate(model))
//    }
//
//    private func onFinished() -> BinarySearchST {
//        isFinished = true
//        return .finished(Code.generate(model))
//    }
//}
