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
    let _: BinarySearchIteratorStates
    public init(_ state: BinarySearchIteratorStates) {
        self._ = state
    }
    func handle()->BinarySearchEvent{
        fatalError("Not implemented")
    }
}

/// while(low <= high) {
///     mid = low + (high - low) / 2
///     if (mid == target) return
///     else if (array[mid] < target)
///         low = mid + 1
///     else
///         high = mid - 1
/// }

private class _Search: IState {

    override func handle() -> BinarySearchEvent {
      
        guard _.low <= _.high else {
            _.step = .finished
            _.model = _.model.copy(returnIndex: -1)
            return .finished(Code.generate(_.model))
        }

        switch(_.label) {

        case "1":
            _.mid = _.low + (_.high - _.low) / 2
            _.label = "2"
            _.model = _.model.copy(mid: _.mid, current: _.array[_.mid!])
            return .pointers(low: _.low, high: _.high, mid: _.mid, code: Code.generate(_.model))

        case "2":
            print("case 2")
            if _.array[_.mid!] == _.target {
                _.model = _.model.copy(low: _.low, high: _.high, mid: _.mid, current: _.array[_.mid!], isFound: true, returnIndex: _.mid)
                _.label = "xx"
                _.step = .finished
                return .foundAt(index: _.mid!, code: Code.generate(_.model))
            } else {
                _.label = "3"
                return .start(Code.generate(_.model))
            }

        case "3":
            if _.array[_.mid!] < _.target {
                _.low = _.mid! + 1
                _.mid = nil
                _.label = "1"
                _.model = _.model.copy(low: _.low, mid: nil, current: nil)
                return .pointers(low: _.low, high: _.high, mid: nil, code: Code.generate(_.model))
            } else {
                _.label = "4"
                return .start(Code.generate(_.model))
            }

        case "4":
            _.high = _.mid! - 1
            _.mid = nil
            _.label = "1"
            _.model = _.model.copy(high: _.high, mid: nil, current: nil)
            return .pointers(low: _.low, high: _.high, mid: nil, code: Code.generate(_.model))

        default:
            return .finished("")
        }

        return .start(Code.generate(_.model))
    }
}


private class _Start:IState{
    
   override func handle()->BinarySearchEvent{
        _.step = .search
        return .start(Code.generate(_.model))
    }
    
}


private class _Finished:IState{
   override func handle()->BinarySearchEvent{
        _.step = .finished
        return .finished(Code.generate(_.model))
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
