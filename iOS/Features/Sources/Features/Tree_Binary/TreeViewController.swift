import Foundation
import SwiftUI

@MainActor
final class TreeViewControllerImpl<T: Comparable>: ObservableObject {

    private let algorithm = DonaldKnuthAlgorithm<T>()
    private let delayOnInsertionComplete: UInt64
    private let processingTime: UInt64

    private var canvasWidth: CGFloat = 400
    private var canvasHeight: CGFloat = 400
    private var bst: BstIteratorImpl<T>?

    // Published equivalents of MutableStateFlow
    @Published private(set) var root: Node<T>?
    @Published private(set) var nodes: [NodeLayout] = [
        
    ]
    @Published private(set) var lines: [VisualLine] = [
//        VisualLine(start: CGPoint(x: 10,y: 10), end: CGPoint(x: 50,y: 50)),
//        VisualLine(start: CGPoint(x: 50,y: 50), end: CGPoint(x: 10,y: 100)),
//        VisualLine(start: CGPoint(x: 10,y: 100), end: CGPoint(x: 200,y: 350)),
    ]

    init(
        delayOnInsertionComplete: UInt64,
        processingTime: UInt64
    ) {
    
        self.delayOnInsertionComplete = delayOnInsertionComplete * 1_000_000 // ms → ns
        self.processingTime = processingTime * 1_000_000
    }

    // MARK: - Canvas update

    func onCanvasSizeChanged(_ width: CGFloat, _ height: CGFloat) {
        guard width != canvasWidth || height != canvasHeight else { return }

        canvasWidth = width
        canvasHeight = height

        guard let root = root else { return }
        let visual = algorithm.calculateTreeLayout(root: root, width: width, height: height)
        nodes = visual.nodes
        lines = visual.lines
    }

    // MARK: - Insert value (like suspend insert in Kotlin)

    func insert(
        value: T,
        onRunning: @escaping () -> Void = {},
        onFinish: @escaping () -> Void = {}
    ) {
        Task {
            guard let bst = bst else {
                // First node case
                let newNode = Node(value)
                self.bst = BstIteratorImpl(newNode)
                self.root = newNode
                self.updateTree(newNode, newlyAddedNodeId: "\(value)")
                return
            }

            bst.prepareInsert(value: value)
            await self.withStateMachine(
                iterator: bst,
                insertionNode: value,
                onRunning: onRunning,
                onFinish: onFinish
            )
        }
    }

    // MARK: - Reset colors

    func resetColor() {
        nodes = nodes.map { node in
            NodeLayout(center: node.center, label: node.label, id: node.id)
        }
    }

    // MARK: - Tree Update Helpers

    private func updateTree(_ root: Node<T>, newlyAddedNodeId: String? = nil) {
        let visual = algorithm.calculateTreeLayout(root: root, width: canvasWidth, height: canvasHeight)
        var updatedNodes = visual.nodes

        if let id = newlyAddedNodeId {
            updatedNodes = updatedNodes.map {
                if $0.id == id {
                    NodeLayout(center: $0.center, label: $0.label, id: $0.id)
                } else {
                    $0
                }
            }
        }

        nodes = updatedNodes
        lines = visual.lines

        Task {
            try? await Task.sleep(nanoseconds: delayOnInsertionComplete)
            self.resetColor()
        }
    }

    private func highlightTarget(_ id: String) {
        changeColor(id, to: .green)
    }

    private func onProcessing(_ id: String) async {
        changeColor(id, to: .orange)
        try? await Task.sleep(nanoseconds: processingTime)
    }

    private func changeColor(_ id: String, to color: Color) {
        nodes = nodes.map { node in
            if node.id == id {
                NodeLayout(center: node.center, label: node.label, id: node.id)
            } else {
                node
            }
        }
    }

    // MARK: - State Machine

    private func withStateMachine(
        iterator: BstIteratorImpl<T>,
        insertionNode: T? = nil,
        onRunning: @escaping () -> Void,
        onFinish: @escaping () -> Void
    ) async {
        while let state = iterator.nextInsertStep() {
            onRunning()

            switch state {
            case .processingNode(let id):
                await onProcessing(id)
            case .newTree(let newTree):
                bst = newTree
                if let newRoot = newTree.root {
                    root = newRoot
                    updateTree(newRoot, newlyAddedNodeId: "\(insertionNode ?? newRoot.data)")
                }
            }
        }
        onFinish()
    }
}

