import Foundation

// MARK: - QuickSortState
enum QuickSortState {
    case finish
    case state(low: Int, high: Int, pivot: Int, swapped: (Int, Int)?, i: Int?, j: Int?)
}

// MARK: - QuickSortSimulatorImpl
final class QuickSortSimulatorImpl {
    private var states: [QuickSortState] = []
    private var count: Int = 0

    init(list: [Int]) {
        var mutableList = list       // Make a mutable copy first
        quickSort(&mutableList)
    }

    func next() -> QuickSortState {
        if count < states.count {
            let state = states[count]
            count += 1
            return state
        } else {
            return .finish
        }
    }

    // MARK: - Private Sorting Logic

    private func quickSort(_ arr: inout [Int], low: Int = 0, high: Int? = nil) {
        let high = high ?? (arr.count - 1)

        // Safety check for valid bounds
        guard low <= high, low >= 0, high < arr.count else { return }

        if low < high {
            let pivotIndex = partition(&arr, low: low, high: high)
            quickSort(&arr, low: low, high: pivotIndex - 1)
            quickSort(&arr, low: pivotIndex + 1, high: high)
        } else {
            states.append(
                .state(low: low, high: high, pivot: high, swapped: nil, i: nil, j: nil)
            )
        }
    }

    private func partition(_ arr: inout [Int], low: Int, high: Int) -> Int {
        guard low >= 0, high < arr.count else { return low }

        let pivot = arr[high]
        var i = low - 1

        states.append(.state(low: low, high: high, pivot: high, swapped: nil, i: i, j: nil))

        for j in low..<high {
            states.append(.state(low: low, high: high, pivot: high, swapped: nil, i: i, j: j))
            
            if arr[j] <= pivot {
                i += 1
                // Safety swap
                if i < arr.count, j < arr.count {
                    arr.swapAt(i, j)
                    states.append(.state(low: low, high: high, pivot: high, swapped: (i, j), i: i, j: j))
                }
            }
        }

        // Final pivot placement swap
        if (i + 1) < arr.count, high < arr.count {
            arr.swapAt(i + 1, high)
            states.append(.state(low: low, high: high, pivot: high, swapped: (i + 1, high), i: i, j: nil))
        }

        return i + 1
    }
}

