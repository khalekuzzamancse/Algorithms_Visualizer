// MARK: - Simulation State
public enum BubbleSortEvent {
    case start(String)
    case none(String)
    case pointers(i: Int, j: Int?, code: String)
    case swapped(i: Int, j: Int, array: [Int], code: String)
    case finished(String)
}

// MARK: - Iterator
class BubbleSortIterator: BubbleSortIteratorStates {
    private var state: IState? = nil

    override func next() -> BubbleSortEvent {
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
    let __: BubbleSortIteratorStates
    public init(_ state: BubbleSortIteratorStates) { self.__ = state }
    func handle() -> BubbleSortEvent { fatalError("Not implemented") }
}

private class _Start: IState {
    override func handle() -> BubbleSortEvent {
        __.i = 0
        __.j = 0
        __.step = .next
        return .start(Code.generate(__.model))
    }
}

private class _NextStep: IState {

    nonisolated(unsafe) static var label = "1"
 
   
    override func handle() -> BubbleSortEvent {
        let lastIndex = __.array.count-1
        if (__.i >= lastIndex) {
            
            return .finished(Code.generate(__.model))
        }
        
        let L=_NextStep.label

        if(L=="1"){
            __.j=0
            nextL("2")
            return .pointers(i: __.i, j: nil, code: "")
        }
        else if(L=="2" || L=="3" || L=="4"){
            if(__.j>=lastIndex){
                nextL("5")
                __.model=__.model.copy(i:__.i)
                let code=__.model.code()
               
                return .pointers(i: __.i, j:nil, code:code)
            }
            
            if(L=="2"){
                nextL("3")
                __.model=__.model.copy(j: __.j)
                let code=__.model.code()
                return .pointers(i: __.i, j:__.j, code: code)
            }
            else if(L=="3"){
                let shouldSwap = __.array[__.j] > __.array[__.j + 1]
                
                if (shouldSwap) {
                    __.array.swapAt(__.j, __.j + 1)
                    nextL("4")
                    __.model=__.model.copy(swapped: true)
                    let code=__.model.code()
                    return .swapped(i: __.j, j:__.j + 1, array: __.array, code: code)
                }
                nextL("4")
                
            }
            else if(L=="4"){
                __.j+=1
                nextL("2")
                let code=__.model.code()
                return .none(code)
            }
            
            
        }
        else if(L=="5")  {
            __.i+=1
            __.j=0
        
            nextL("1")
    
            let code=__.model.code()
            return .none(code)
        }
        __.model=__.model.copy(swapped: true)
        let code=__.model.code()
        return .finished(code)
    
    }
    
    func nextL(_ label:String){
        _NextStep.label = label
    }
    

}
/**
 Bubble sort
 while(i <lastIndex){            →L1
          j=0                  →L1
         pause: emit(i), L=2
        while(j<lastIndex){         →L2
              pause: emit(i), L=3
              swap=a[j]>a[j+1]           →L3
  if(swap){               →L3
   swap()                →L3
   pause: emit(swap), L=4
    j++                → L4
    L=2
 }
    
 }
 i++                    →L5
 L=1                    →L5
 }

 */

private extension _BubbleSortCodeStateModel {
    func code() -> String {
        return Code.generate(self)
    }
}

private class _Swap: IState {
    override func handle() -> BubbleSortEvent {
        __.array.swapAt(__.j, __.j + 1)
        __.model = __.model.copy(
            i: __.i,
            j: __.j,
            swapped: true,
            arrayState: __.array
        )
        __.step = .next
        __.j += 1
        return .swapped(i: __.i, j: __.j - 1, array: __.array, code: Code.generate(__.model))
    }
}

private class _Finished: IState {
    override func handle() -> BubbleSortEvent {
        __.step = .finished
        print("Array:\(__.array)")
        return .finished(Code.generate(__.model))
    }
}

// MARK: - Iterator State Base
class BubbleSortIteratorStates {
    enum Step {
        case start, next, finished
    }

    var step: Step = .start
    var array: [Int]
    var i = 0
    var j = 0
    var label="0"
   
    
    var model: _BubbleSortCodeStateModel

    func hasNext() -> Bool { step != .finished }
    func next() -> BubbleSortEvent { fatalError("Subclasses must override next()") }

    init(array: [Int]) {
        self.array = array
        self.model = _BubbleSortCodeStateModel(
            i: nil, j: nil,
            current: nil, next: nil,
            swapped: nil, arrayState: array
        )
    }
}

// MARK: - Code Model
struct _BubbleSortCodeStateModel {
    let i: Int?
    let j: Int?
    let current: Int?
    let next: Int?
    let swapped: Bool?
    let arrayState: [Int]

    func copy(
        i: Int?? = nil,
        j: Int?? = nil,
        current: Int?? = nil,
        next: Int?? = nil,
        swapped: Bool?? = nil,
        arrayState: [Int]? = nil
    ) -> _BubbleSortCodeStateModel {
        _BubbleSortCodeStateModel(
            i: i ?? self.i,
            j: j ?? self.j,
            current: current ?? self.current,
            next: next ?? self.next,
            swapped: swapped ?? self.swapped,
            arrayState: arrayState ?? self.arrayState
        )
    }
}

// MARK: - Code Generator
private class Code {
    static func generate(_ model: _BubbleSortCodeStateModel) -> String {
        let iComment = model.i.map { "// i: \($0)" } ?? ""
        let jComment = model.j.map { "// j: \($0)" } ?? ""
        let currentComment = model.current.map { "// current: \($0)" } ?? ""
        let nextComment = model.next.map { "// next: \($0)" } ?? ""
        let swappedComment = model.swapped.map { "// swapped: \($0)" } ?? ""
        let arrayComment = "// array: \(model.arrayState)"

        return """
BubbleSort(array) {
    for i in 0..<array.count-1 \(iComment) {
        for j in 0..<array.count - i - 1 \(jComment) {
            current = array[j] \(currentComment)
            next = array[j+1] \(nextComment)
            if current > next \(swappedComment) {
                swap(array[j], array[j+1])
            }
        }
    }
    \(arrayComment)
}
"""
    }
}

