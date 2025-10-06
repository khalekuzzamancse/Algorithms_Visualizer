// MARK: - Simulation State
public enum BubbleSortEvent {
    case start(String)
    case pointers(i: Int, j: Int?, code: String)
    case swapped(i: Int, j: Int, array: [Int], code: String)
    case finished(String)
}

// MARK: - Iterator
class BubbleSortIterator: BubbleSortIteratorStates {
    private var state: IState? = nil

    override func next() -> BubbleSortEvent {
//        if(step == .finished){
//            return _Finished(self).handle()
//        }
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

    
    nonisolated(unsafe) static var subLabel = "1.0"
   
    override func handle() -> BubbleSortEvent {
        let lastIndex = __.array.count-1
        let label=__.label
        print("array:\(__.array), i:\(__.i),j:\(__.j)")
//        if(__.i>=lastIndex){
//            __.step = .finished
//        }
        switch(label){
            
        case "0": //while(i<lastIndex)
            if(__.i < lastIndex){
                __.label="1"
                let i = __.i
                __.i+=1
                __.j=0
                return .pointers(i:i, j:0, code: Code.generate(__.model))
            }
           
            
        case "1": return innerLoop()
    
            
          default: return .finished("")
            
        }
    
        

        return .pointers(i: __.i, j:nil, code: Code.generate(__.model))
    }
    
    // for i: 0 to n-2
    func innerLoop()->BubbleSortEvent{
        let lastIndex = __.array.count-1
        let j=__.j
        let i=__.i
    
        let label=__.label
        //while ( j<lastIndex )
      //  print("innetLoopCalled")
        switch(_NextStep.subLabel){
        case "1.0":
            if(j < lastIndex-1){
                __.label="1"
                _NextStep.subLabel="1.1"
              //  print("innerLoop:j=\(j),lastIndex=\(lastIndex)")
                return .pointers(i:i, j:j, code: Code.generate(__.model))
                
            }
            else{
                __.label="0"
            }
        case "1.1":
            print("innerLoop:swap(\(j),\(j+1))")

            if j >= 0 && j + 1 < __.array.count {
                let shouldSwap = __.array[j] > __.array[j + 1]
                
                if shouldSwap {
                    print("innerLoop:swap(\(j),\(j+1))")
                    __.label = "1"
                    _NextStep.subLabel = "1.0"
                    __.array.swapAt(j, j + 1)
                    print("array:\(__.array)")
                    return .swapped(i: j, j: j + 1, array: __.array, code: "")
                }
            }
           

            // increment j even if no swap or indices invalid
            __.j += 1

//            print("innerLoop:swap(\(j),\(j+1) )")
//            let swap=(__.array[j]>__.array[j+1])
//            if(swap){
//              print("innerLoop:swap(\(j),\(j+1) )")
//              
//                __.label="1"
//                _NextStep.subLabel="1.0"
//                __.array.swapAt(j,j+1)
//                print("array:\(__.array)")
//                return .swapped(i:j, j:j+1, array:__.array,code: "")
//                
//            }
//            __.j+=1 //swap or not increamnet j
        
        default: let x=0
            
            
        }

        
       return .pointers(i:__.i, j:nil, code: Code.generate(__.model))
        
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

