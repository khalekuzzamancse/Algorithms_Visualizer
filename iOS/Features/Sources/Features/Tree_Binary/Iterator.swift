import Foundation
import CoreGraphics



// MARK: - BstIteratorImpl

 class BstIteratorImpl<T: Comparable> {
     let root: Node<T>?
    private var states: [Node<T>] = []
    private var current: Node<T>?
    private var cnt = 0
    private var preparedStates: [IteratorState<T>] = []
    private var stateIndex = 0

    init(_ root: Node<T>?) {
        self.root = root
    }

    // MARK: - Prepare Insert States (like Kotlin sequence builder)
    func prepareInsert(value: T) {
        states.removeAll()
        preparedStates.removeAll()
        __insert(root, value)
        for node in states {
            preparedStates.append(.processingNode(node.id))
        }
        if let last = states.last {
            preparedStates.append(.newTree(BstIteratorImpl(last)))
        }
        stateIndex = 0
    }

    // MARK: - Manual iterator step
    func nextInsertStep() -> IteratorState<T>? {
        guard stateIndex < preparedStates.count else { return nil }
        let state = preparedStates[stateIndex]
        stateIndex += 1
        return state
    }

    // MARK: - Direct insert (single-step style)
    func insertStep(value: T) -> IteratorState<T> {
        if root == nil {
            return .newTree(BstIteratorImpl(Node(value)))
        }
        __insert(root, value)
        if cnt <= states.indices.last ?? -1 {
            current = states[cnt]
            cnt += 1
            if let current = current {
                return .processingNode(current.id)
            }
        }
        return .newTree(BstIteratorImpl(current))
    }

    // MARK: - Core Insert
    private func __insert(_ root: Node<T>?, _ value: T) {
        guard let root = root else {
            states.append(Node(value))
            return
        }

        var stack: [Node<T>] = []
        var path: [Node<T>] = []
        var current: Node<T>? = root

        while let node = current {
            states.append(node)
            stack.insert(node, at: 0)
            if value < node.data {
                current = node.left
            } else if value > node.data {
                current = node.right
            } else {
                states.append(root)
                return
            }
        }

        var newNode = Node(value)

        while !stack.isEmpty {
            let parent = stack.removeFirst()
            if value < parent.data {
                newNode = Node(parent.data, newNode, parent.right)
            } else {
                newNode = Node(parent.data, parent.left, newNode)
            }
            path.insert(newNode, at: 0)
        }

        if let first = path.first {
            states.append(first)
        }
    }

  
}
// MARK: - Node

final class Node<T: Comparable> {
    let data: T
    let left: Node<T>?
    let right: Node<T>?
    let id: String
    var center: CGPoint = .zero

    init(_ data: T, _ left: Node<T>? = nil, _ right: Node<T>? = nil, id: String? = nil) {
        self.data = data
        self.left = left
        self.right = right
        self.id = id ?? "\(data)"
    }

    func getDepth() -> Int {
        let leftDepth = left?.getDepth() ?? 0
        let rightDepth = right?.getDepth() ?? 0
        return 1 + max(leftDepth, rightDepth)
    }

    var label: String { "\(data)" }
}

// MARK: - State Enum

enum IteratorState<T: Comparable> {
    case processingNode(String)
    case newTree(BstIteratorImpl<T>)
}
