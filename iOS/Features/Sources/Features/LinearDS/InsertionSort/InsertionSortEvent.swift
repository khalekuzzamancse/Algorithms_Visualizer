// MARK: - Simulation State
public enum InsertionSortEvent {
    case start(String)
    case none(String)
    case pointers(i: Int, j: Int?, code: String)
    case swapped(i: Int, j: Int, array: [Int], code: String)
    case finished(String)
}

// MARK: - Iterator
class InsertionSortIterator: InsertionSortIteratorStates {
    private var state: IState? = nil

    override func next() -> InsertionSortEvent {
        switch step {
        case .start:
            state = _Start(self)
        case .next:
            state = _NextStep(self)
        case .finished:
            state = _Finished(self)
        }
        return state?.handle() ?? _Start(self).handle()
    }
}

private class IState {
    let __: InsertionSortIteratorStates
    public init(_ state: InsertionSortIteratorStates) { self.__ = state }
    func handle() -> InsertionSortEvent { fatalError("Not implemented") }
}

private class _Start: IState {
    override func handle() -> InsertionSortEvent {
        __.i = 1
        __.step = .next
        return .start(Code.generate(__.model))
    }
}

private class _Finished: IState {
    override func handle() -> InsertionSortEvent {
        __.step = .finished
        print("Array: \(__.array)")
        return .finished(Code.generate(__.model))
    }
}

private class _NextStep: IState {
    nonisolated(unsafe) static var label = "L1"
    
    override func handle() -> InsertionSortEvent {
        let lastIndex = __.array.count
        let L = _NextStep.label
        
        if L == "L1" {
            if __.i >= lastIndex {
                return .finished(Code.generate(__.model))
            }
            __.j = __.i
            _NextStep.label = "L2"
            return .pointers(i: __.i, j: nil, code: "")
        }
        else if L == "L2" {
            if __.j <= 0 {
                __.i += 1
                _NextStep.label = "L1"
                return .none(Code.generate(__.model))
            }
            let shouldSwap = __.array[__.j] < __.array[__.j - 1]
            if shouldSwap {
                _NextStep.label = "L3"
                return .pointers(i: __.i, j: __.j, code: Code.generate(__.model))
            }
            else {
                __.i += 1
                _NextStep.label = "L1"
                return .none(Code.generate(__.model))
            }
        }
        else if L == "L3" {
            __.array.swapAt(__.j, __.j - 1)
            _NextStep.label = "L4"
            return .swapped(i: __.j, j: __.j - 1, array: __.array, code: Code.generate(__.model))
        }
        else if L == "L4" {
            __.j -= 1
            _NextStep.label = "L2"
            return .none(Code.generate(__.model))
        }
        
        return .finished(Code.generate(__.model))
    }
}

// MARK: - Iterator State Base
class InsertionSortIteratorStates {
    enum Step {
        case start, next, finished
    }
    
    var step: Step = .start
    var array: [Int]
    var i = 1
    var j = 0
    
    var model: _InsertionSortCodeStateModel
    
    func hasNext() -> Bool { step != .finished }
    func next() -> InsertionSortEvent { fatalError("Subclasses must override next()") }
    
    init(array: [Int]) {
        self.array = array
        self.model = _InsertionSortCodeStateModel(
            i: nil, j: nil,
            swapped: nil, arrayState: array
        )
    }
}

// MARK: - Code Model
struct _InsertionSortCodeStateModel {
    let i: Int?
    let j: Int?
    let swapped: Bool?
    let arrayState: [Int]
    
    func copy(
        i: Int?? = nil,
        j: Int?? = nil,
        swapped: Bool?? = nil,
        arrayState: [Int]? = nil
    ) -> _InsertionSortCodeStateModel {
        _InsertionSortCodeStateModel(
            i: i ?? self.i,
            j: j ?? self.j,
            swapped: swapped ?? self.swapped,
            arrayState: arrayState ?? self.arrayState
        )
    }
}

// MARK: - Code Generator
private class Code {
    static func generate(_ model: _InsertionSortCodeStateModel) -> String {
        let iComment = model.i.map { "// i: \($0)" } ?? ""
        let jComment = model.j.map { "// j: \($0)" } ?? ""
        let swappedComment = model.swapped.map { "// swapped: \($0)" } ?? ""
        let arrayComment = "// array: \(model.arrayState)"
        
        return """
        insertionSort(list) {
            i = \(model.i ?? 0) \(iComment)
            j = \(model.j ?? 0) \(jComment)
            if j > 0 \(swappedComment) {
                if list[j] < list[j-1] {
                    swap(list[j], list[j-1])
                }
            }
            \(arrayComment)
        }
        """
    }
}

