//
//  SelectionSortEvent.swift
//  AlgorithmsVisualizer
//
//  Created by Md Khalekuzzaman on 10/9/25.
//


// MARK: - Simulation State
public enum SelectionSortEvent {
    case start(String)
    case none(String)
    case pointers(i: Int, minIndex: Int?, code: String)
    case swapped(i: Int, j: Int, array: [Int], code: String)
    case finished(String)
}

// MARK: - Iterator
class SelectionSortIterator: SelectionSortIteratorStates {
    private var state: IState? = nil

    override func next() -> SelectionSortEvent {
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

// MARK: - Base State
private class IState {
    let __: SelectionSortIteratorStates
    public init(_ state: SelectionSortIteratorStates) { self.__ = state }
    func handle() -> SelectionSortEvent { fatalError("Not implemented") }
}

// MARK: - Start State
private class _Start: IState {
    override func handle() -> SelectionSortEvent {
        __.i = 0
        __.j = 0
        __.step = .next
        return .start(__.model.code())
    }
}

// MARK: - Next Step State
private class _NextStep: IState {

    nonisolated(unsafe) static var label = "1"

    override func handle() -> SelectionSortEvent {
        let lastIndex = __.array.count - 1
        if __.i >= lastIndex {
            return .finished(__.model.code())
        }

        let L = _NextStep.label
        print("L->\(L) : i=\(__.i)")

        if (L == "1") {
            nextL("2")
            return .pointers(i: __.i, minIndex: nil, code: "")
        } else if( L == "2" || L == "3") {
            if __.j >= __.array.count {
                nextL("5")
                __.model = __.model.copy(i: __.i)
                return .pointers(i: __.i, minIndex:nil, code: __.model.code())
            }

            if (L == "2") {
                let minIndex=findMinimumIndex(__.i)
                let isFound = (minIndex != nil)
                if(isFound){
                    nextL("3")
                    __.minIndex=minIndex!
                    print("minIndex=\(__.minIndex)")
                    return .pointers(i: __.i, minIndex: __.minIndex, code: __.model.code())
                    
                }
                else {
                 
                    nextL("4") // min index not found jump to L4
                }
                
                
        
            }
            else if( L == "3") {
                __.array.swapAt(__.i, __.minIndex)
                nextL("4")
                return .swapped(i: __.i, j: __.minIndex, array: __.array, code: "")
                
            }
        }
        else if (L == "4" ){
            __.i += 1
            nextL("1")
            return .pointers(i: __.i, minIndex: nil, code:__.model.code())
        
        }


        return .finished(__.model.code())
    }

    func nextL(_ label: String) {
        _NextStep.label = label
    }
    private func findMinimumIndex(_ i: Int) -> Int? {
        var minIndex = i
        var j = i + 1
        let sortedList = __.array
        let lastIndex = sortedList.count - 1
        
        while j <= lastIndex {
            if sortedList[j] < sortedList[minIndex] {
                minIndex = j
            }
            j += 1
        }
        
        // Return nil if no new minimum found
        return minIndex == i ? nil : minIndex
    }


}

/**
 Selection Sort:
 while( i < lastIndex){               →L1
 pause:emit(i), L=2
 minIndex=findMinIndex(array,i,len)        →L2
 isMinIndexFound=(minIndex!=null)        →L2
 if(isMinFound){                →L2
 pause:emit(minIndex), L=3
 swap(i,minIndex)                →L3
           pause: emit(swap), L=4
 L4// swap or not jump to L4
 }

 i++                        →L4
 }

 */
// MARK: - Finished State
private class _Finished: IState {
    override func handle() -> SelectionSortEvent {
        __.step = .finished
        return .finished(__.model.code())
    }
}

// MARK: - Iterator State Base
class SelectionSortIteratorStates {
    enum Step {
        case start, next, finished
    }

    var step: Step = .start
    var array: [Int]
    var i = 0
    var j = 0
    var minIndex = 0

    var model: _SelectionSortCodeStateModel

    func hasNext() -> Bool { step != .finished }
    func next() -> SelectionSortEvent { fatalError("Subclasses must override next()") }

    init(array: [Int]) {
        self.array = array
        self.minIndex = 0
        self.model = _SelectionSortCodeStateModel(i: nil, j: nil, minIndex: nil, swapped: nil, arrayState: array)
    }
}

// MARK: - Code Model
struct _SelectionSortCodeStateModel {
    let i: Int?
    let j: Int?
    let minIndex: Int?
    let swapped: Bool?
    let arrayState: [Int]

    func copy(
        i: Int?? = nil,
        j: Int?? = nil,
        minIndex: Int?? = nil,
        swapped: Bool?? = nil,
        arrayState: [Int]? = nil
    ) -> _SelectionSortCodeStateModel {
        _SelectionSortCodeStateModel(
            i: i ?? self.i,
            j: j ?? self.j,
            minIndex: minIndex ?? self.minIndex,
            swapped: swapped ?? self.swapped,
            arrayState: arrayState ?? self.arrayState
        )
    }

    func code() -> String {
        let iComment = i.map { "// i: \($0)" } ?? ""
        let jComment = j.map { "// j: \($0)" } ?? ""
        let minIndexComment = minIndex.map { "// minIndex: \($0)" } ?? ""
        let swappedComment = swapped.map { "// swapped: \($0)" } ?? ""
        let arrayComment = "// array: \(arrayState)"

        return """
SelectionSort(array) {
    for i in 0..<array.count-1 \(iComment) {
        minIndex = findMinIndex(array, i, array.count) \(minIndexComment)
        for j in i+1..<array.count \(jComment) {
            if array[j] < array[minIndex] {
                minIndex = j
            }
        }
        if minIndex != i \(swappedComment) {
            swap(array[i], array[minIndex])
        }
    }
    \(arrayComment)
}
"""
    }
}
