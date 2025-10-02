import Foundation

// SimulationState equivalents
public enum LinearSearchST {
    case start(String)
    case pointerI(index: Int, code: String)
    case foundAt(index: Int, code: String)
    case finished(String)
}

class LinearSearchIterator {
     let array: [Int]
     let target: Int
     var currentIndex = 0
     var step: Step = .start
     var isFinished = false
     private var model: LinearSearchCSM
   


     enum Step {
        case start
        case hasSearch
        case found
        case finished
    }

    init(array: [Int], target: Int) {
        self.array = array
        self.target = target
        self.model =
        LinearSearchCSM(len: array.count, target: target, i: nil, current: nil, isFound: nil, returnIndex: nil)
    }

    func hasNext() -> Bool {
        return !isFinished
    }

    func next() -> LinearSearchST {
      
        if isFinished { return onFinished() }

        switch step {
        case .start: return onStart()
            
        case .hasSearch:
           return searchNext()
        
        case .found:
            let foundIndex = currentIndex
            step = .finished
            return .foundAt(index: foundIndex, code: "Target \(target) found at index \(foundIndex)")

        case .finished: return onFinished()
            
        }
        
    }
    
    private func searchNext()->LinearSearchST{
        if currentIndex >= array.count {
            return onArrayBoundExit()
        } else {
            //Need the current copy, because will be modified it
            let currentTemp = currentIndex
            let currentValue = array[currentTemp]

            if currentValue == target {
                return onFound(currentTemp)
            } else {
               return onNotFoundAtCurrentIndex(currentTemp)
            }
           
        }
        
    }
    
    private func onFound(_ current:Int)->LinearSearchST{
        let current=array[current]
        step = .found
        self.model = model.copy(i: current, current:current, isFound:true)
        return .pointerI(index: current, code:CodeLS.generate(model))
       
    }
    private func onNotFoundAtCurrentIndex(_ idx:Int)->LinearSearchST{
        currentIndex += 1
        let current=array[idx]
        self.model = model.copy(i: idx, current:current, isFound:false)
        return .pointerI(index: idx, code:CodeLS.generate(model))
    }
    
    private func onArrayBoundExit()->LinearSearchST{
        step = .finished
        model = model.copy(
            i: nil, current: nil, isFound: nil, returnIndex: -1)
        return .finished(CodeLS.generate(model))
        
    }
    
    private func onFinished()->LinearSearchST{
        isFinished = true
        self.model=LinearSearchCSM(len: array.count, target: target, i: nil, current: nil, isFound: nil, returnIndex: nil)
        print("onFinished")
        return .finished(CodeLS.generate(model))
        
    }
    private func onStart()->LinearSearchST{
        step = .hasSearch
        return .start(CodeLS.generate(model))
    }
}

// MARK: - Code State Model

private struct LinearSearchCSM {
    let len: Int
    let target: Int
    let i: Int?
    let current: Int?
    let isFound: Bool?
    let returnIndex: Int?

    func copy(
        len: Int? = nil,
        target: Int? = nil,
        i: Int?? = nil,
        current: Int?? = nil,
        isFound: Bool?? = nil,
        returnIndex: Int?? = nil
    ) -> LinearSearchCSM {
        return LinearSearchCSM(
            len: len ?? self.len,
            target: target ?? self.target,
            i: i ?? self.i,
            current: current ?? self.current,
            isFound: isFound ?? self.isFound,
            returnIndex: returnIndex ?? self.returnIndex
        )
    }
}

// MARK: - Pseudocode Generator

private class CodeLS {
    static func generate(_ model: LinearSearchCSM) -> String {
        let idxComment = model.i.map { "// i: \($0)" } ?? ""
        let valueComment = model.current.map { "// current: \($0)" } ?? ""
        let foundComment = model.isFound.map { "//\($0)" } ?? ""
        let returnComment = model.returnIndex.map { "// returned: \($0)" } ?? ""

        return """
LinearSearch(list, target) { // target: \(model.target)
    len = list.size // len: \(model.len)
    for (i = 0; i < len; i++) { \(idxComment)
        current = list[i] \(valueComment)
        isFound = (current == target) 
        if (isFound)\(foundComment)
            return index
    }
    return -1 \(returnComment)
}
"""
    }
}
