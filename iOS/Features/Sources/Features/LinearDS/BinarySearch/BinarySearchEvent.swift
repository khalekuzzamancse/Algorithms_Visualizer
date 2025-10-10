
//// MARK: - Simulation State
public enum BinarySearchEvent {
    case start(String)
    case pointers(low: Int?, high: Int?, mid: Int?, code: String)
    case foundAt(index: Int, code: String)
    case finished(String)
}


 class BinarySearchIterator: BinarySearchIteratorStates {
     private var state:IState? = nil
    
    override func next() -> BinarySearchEvent {
        switch (step){
        case .start:
            state = _Start(self)
        case .search:
            state = _Search(self)
        case .finished:
            state = _Finished(self)
        }
        
        return state?.handle() ?? _Start(self).handle()
    }



}


private class IState{
    let __: BinarySearchIteratorStates
    public init(_ state: BinarySearchIteratorStates) {
        self.__ = state
    }
    func handle()->BinarySearchEvent{
        fatalError("Not implemented")
    }
}

/// while(low <= high) {
///     mid = low + (high - low) / 2
///     if (mid == target) return
///     else if (array[mid] < target)
///         low = mid + 1
///     else
///         high = mid - 1
/// }

private class _Search: IState {

    override func handle() -> BinarySearchEvent {
      
        guard __.low <= __.high else {
            __.step = .finished
            __.model = __.model.copy(returnIndex: -1)
            return .finished(Code.generate(__.model))
        }

        switch(__.label) {

        case "1":
            __.mid = __.low + (__.high - __.low) / 2
            __.label = "2"
            __.model = __.model.copy(mid: __.mid, current: __.array[__.mid!])
            return .pointers(low: __.low, high: __.high, mid: __.mid, code: Code.generate(__.model))

        case "2":
            print("case 2")
            if __.array[__.mid!] == __.target {
                __.model = __.model.copy(low: __.low, high: __.high, mid: __.mid, current: __.array[__.mid!], isFound: true, returnIndex: __.mid)
                __.label = "xx"
                __.step = .finished
                return .foundAt(index: __.mid!, code: Code.generate(__.model))
            } else {
                __.label = "3"
                return .start(Code.generate(__.model))
            }

        case "3":
            if __.array[__.mid!] < __.target {
                __.low = __.mid! + 1
                __.mid = nil
                __.label = "1"
                __.model = __.model.copy(low: __.low, mid: nil, current: nil)
                return .pointers(low: __.low, high: __.high, mid: nil, code: Code.generate(__.model))
            } else {
                __.label = "4"
                return .start(Code.generate(__.model))
            }

        case "4":
            __.high = __.mid! - 1
            __.mid = nil
            __.label = "1"
            __.model = __.model.copy(high: __.high, mid: nil, current: nil)
            return .pointers(low: __.low, high: __.high, mid: nil, code: Code.generate(__.model))

        default:
            return .finished("")
        }

        return .start(Code.generate(__.model))
    }
}


private class _Start:IState{
    
   override func handle()->BinarySearchEvent{
        __.step = .search
        return .start(Code.generate(__.model))
    }
    
}


private class _Finished:IState{
   override func handle()->BinarySearchEvent{
        __.step = .finished
        return .finished(Code.generate(__.model))
    }
    
}



 class BinarySearchIteratorStates {

    var step: Step = .start
    var label="1"

    enum Step {
        case start
        case search
        case finished
    }
    
     func hasNext() -> Bool { step != .finished }
    
    func next() -> BinarySearchEvent {
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

private class Code {
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
