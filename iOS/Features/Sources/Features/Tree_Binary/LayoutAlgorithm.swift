import UIKit
import SwiftUI
import CoreGraphics

// MARK: - Node Layout and VisualTree equivalents
struct NodeLayout :Identifiable{
    let center: CGPoint
    let label: String
    let id: String
}

struct VisualLine {
    let start: CGPoint
    let end: CGPoint
}

struct VisualTree {
    let nodes: [NodeLayout]
    let lines: [VisualLine]
}

// MARK: - LayoutAlgorithm Protocol
protocol LayoutAlgorithm {
    associatedtype T: Comparable
    func calculateTreeLayout(root: Node<T>, width: CGFloat, height: CGFloat) -> VisualTree
}

final class DonaldKnuthAlgorithm<T: Comparable>: LayoutAlgorithm {
    func calculateTreeLayout(root: Node<T>, width: CGFloat, height: CGFloat) -> VisualTree {
        let maxDepth = root.getDepth()
        let nodeRadius: CGFloat = 10
        let verticalSpacing: CGFloat =
            maxDepth > 0 ? (height - 2 * nodeRadius) / CGFloat(maxDepth) : 0

        var nodes = [NodeLayout]()
        var visualLines = [VisualLine]()
        var positions = [String: CGPoint]()

        var xIndex: CGFloat = 0
        var horizontalSpacing: CGFloat = 0

        func countNodes(_ node: Node<T>?) -> Int {
            guard let node = node else { return 0 }
            return 1 + countNodes(node.left) + countNodes(node.right)
        }

        // Pass 1: assign node positions
        @discardableResult
        func assignPositions(_ node: Node<T>?, depth: Int) -> CGPoint? {
            guard let node = node else { return nil }

            if horizontalSpacing == 0 {
                let totalNodes = CGFloat(max(1, countNodes(root)))
                if totalNodes <= 1 {
                    horizontalSpacing = 0 // only one node
                } else {
                    horizontalSpacing = (width - 2 * nodeRadius) / (totalNodes - 1)
                }
            }

            var leftCenter: CGPoint?
            if let left = node.left {
                leftCenter = assignPositions(left, depth: depth + 1)
            }

            let totalNodes = CGFloat(max(1, countNodes(root)))
            let x: CGFloat
            if totalNodes == 1 {
                // Single node: center it
                x = width / 2
            } else {
                x = nodeRadius + xIndex * horizontalSpacing
            }

            let y = nodeRadius + CGFloat(depth) * verticalSpacing
            let currentCenter = CGPoint(x: x, y: y)
            positions[node.id] = currentCenter
            nodes.append(NodeLayout(center: currentCenter, label: node.label, id: node.id))
            xIndex += 1

            if let right = node.right {
                _ = assignPositions(right, depth: depth + 1)
            }

            return currentCenter
        }

        // Pass 2: create lines
        func createLines(_ node: Node<T>?) {
            guard let node = node else { return }

            guard let parentCenter = positions[node.id] else { return }

            if let left = node.left, let leftCenter = positions[left.id] {
                visualLines.append(VisualLine(start: parentCenter, end: leftCenter))
                createLines(left)
            } else if let left = node.left {
                createLines(left)
            }

            if let right = node.right, let rightCenter = positions[right.id] {
                visualLines.append(VisualLine(start: parentCenter, end: rightCenter))
                createLines(right)
            } else if let right = node.right {
                createLines(right)
            }
        }

        // Run passes
        _ = assignPositions(root, depth: 0)
        createLines(root)

        return VisualTree(nodes: nodes, lines: visualLines)
    }
}


//// MARK: - Donald Knuth Algorithm (two-pass)
//final class DonaldKnuthAlgorithm<T: Comparable>: LayoutAlgorithm {
//    func calculateTreeLayout(root: Node<T>, width: CGFloat, height: CGFloat) -> VisualTree {
//        // Basic spacing and sizing
//        let maxDepth = root.getDepth()
//        let nodeRadius: CGFloat = 10
//        let verticalSpacing: CGFloat =
//            maxDepth > 0 ? (height - 2 * nodeRadius) / CGFloat(maxDepth) : 0
//
//        // Storage for nodes and lines
//        var nodes = [NodeLayout]()                    // result NodeLayout list
//        var visualLines = [VisualLine]()              // result lines
//
//        // Map from node id -> center point (filled in first pass)
//        var positions = [String: CGPoint]()
//
//        // We will assign x positions using an xIndex during an inorder-like traversal
//        var xIndex: CGFloat = 0
//        var horizontalSpacing: CGFloat = 0
//
//        // Count nodes recursively (used to compute horizontal spacing)
//        func countNodes(_ node: Node<T>?) -> Int {
//            guard let node = node else { return 0 }
//            return 1 + countNodes(node.left) + countNodes(node.right)
//        }
//
//        // ===== PASS 1: assign positions to every node and fill `nodes` + `positions` =====
//        // We do a left->node->right traversal so sibling ordering on x will match the BST in-order layout.
//        @discardableResult
//        func assignPositions(_ node: Node<T>?, depth: Int) -> CGPoint? {
//            guard let node = node else { return nil }
//
//            // Ensure horizontal spacing computed once (based on total node count)
//            if horizontalSpacing == 0 {
//                let totalWidthNodes = CGFloat(max(1, countNodes(root)))
//                horizontalSpacing = totalWidthNodes > 1
//                    ? (width - 2 * nodeRadius) / (totalWidthNodes - 1)
//                    : 0
//            }
//
//            // Traverse left child (inorder)
//            var leftCenter: CGPoint?
//            if let left = node.left {
//                leftCenter = assignPositions(left, depth: depth + 1)
//            }
//
//            // Compute current node center after left is fully processed (inorder)
//            let x = nodeRadius + xIndex * horizontalSpacing
//            let y = nodeRadius + CGFloat(depth) * verticalSpacing
//            let currentCenter = CGPoint(x: x, y: y)
//
//            // Record this node's layout and position map
//            positions[node.id] = currentCenter
//            nodes.append(NodeLayout(center: currentCenter, label: node.label, id: node.id))
//
//            // Move x index forward
//            xIndex += 1
//
//            // Traverse right child
//            var rightCenter: CGPoint?
//            if let right = node.right {
//                rightCenter = assignPositions(right, depth: depth + 1)
//            }
//
//            // Return current center (not strictly necessary for this pass, but keep it consistent)
//            return currentCenter
//        }
//
//        // ===== PASS 2: using positions map, create lines parent -> child centers =====
//        func createLines(_ node: Node<T>?) {
//            guard let node = node else { return }
//            let parentCenterOpt = positions[node.id]
//
//            if let left = node.left, let parentCenter = parentCenterOpt, let leftCenter = positions[left.id] {
//                visualLines.append(VisualLine(start: parentCenter, end: leftCenter))
//                createLines(left)
//            } else if let left = node.left {
//                // If positions missing unexpectedly, still traverse the child to continue building lines
//                createLines(left)
//            }
//
//            if let right = node.right, let parentCenter = parentCenterOpt, let rightCenter = positions[right.id] {
//                visualLines.append(VisualLine(start: parentCenter, end: rightCenter))
//                createLines(right)
//            } else if let right = node.right {
//                createLines(right)
//            }
//        }
//
//        // Run both passes
//        _ = assignPositions(root, depth: 0)
//        createLines(root)
//
//        // Return composed VisualTree
//        return VisualTree(nodes: nodes, lines: visualLines)
//    }
//}

//// MARK: - Donald Knuth Algorithm Implementation
//final class DonaldKnuthAlgorithm<T: Comparable>: LayoutAlgorithm {
//    func calculateTreeLayout(root: Node<T>, width: CGFloat, height: CGFloat) -> VisualTree {
//        let maxDepth = root.getDepth()
//        let nodeRadius: CGFloat = 10
//        let verticalSpacing: CGFloat =
//            maxDepth > 0 ? (height - 2 * nodeRadius) / CGFloat(maxDepth) : 0
//        var horizontalSpacing: CGFloat = 0
//
//        var nodes = [NodeLayout]()
//        var visualLines = [VisualLine]()
//        var xIndex: CGFloat = 0
//
//        // Count total nodes recursively
//        func countNodes(_ node: Node<T>?) -> Int {
//            guard let node = node else { return 0 }
//            return 1 + countNodes(node.left) + countNodes(node.right)
//        }
//
//        // Recursive traversal - returns only the current position
//        func traverse(node: Node<T>, depth: Int) -> CGPoint {
//            // Traverse left first (in-order traversal)
//            var leftChildPos: CGPoint?
//            if let left = node.left {
//                leftChildPos = traverse(node: left, depth: depth + 1)
//            }
//
//            // Calculate current node position
//            if horizontalSpacing == 0 {
//                let totalWidthNodes = CGFloat(countNodes(root))
//                horizontalSpacing =
//                    totalWidthNodes > 1
//                        ? (width - 2 * nodeRadius) / (totalWidthNodes - 1)
//                        : 0
//            }
//
//            let x = nodeRadius + xIndex * horizontalSpacing
//            let y = nodeRadius + CGFloat(depth) * verticalSpacing
//            let currentPos = CGPoint(x: x, y: y)
//            xIndex += 1
//
//            // Traverse right
//            var rightChildPos: CGPoint?
//            if let right = node.right {
//                rightChildPos = traverse(node: right, depth: depth + 1)
//            }
//
//            // Add lines from current node to children
//            // This must happen AFTER we know the current node's position
//            if let leftPos = leftChildPos {
//                visualLines.append(VisualLine(start: currentPos, end: leftPos))
//            }
//            if let rightPos = rightChildPos {
//                visualLines.append(VisualLine(start: currentPos, end: rightPos))
//            }
//
//            // Add the current node
//            nodes.append(NodeLayout(center: currentPos, label: node.label, id: node.id))
//            
//            return currentPos
//        }
//
//        _ = traverse(node: root, depth: 0)
//        return VisualTree(nodes: nodes, lines: visualLines)
//    }
//}
//
//// MARK: - Donald Knuth Algorithm Implementation
//final class DonaldKnuthAlgorithm<T: Comparable>: LayoutAlgorithm {
//    func calculateTreeLayout(root: Node<T>, width: CGFloat, height: CGFloat) -> VisualTree {
//        let maxDepth = root.getDepth()
//        let nodeRadius: CGFloat = 10
//        let verticalSpacing: CGFloat =
//            maxDepth > 0 ? (height - 2 * nodeRadius) / CGFloat(maxDepth) : 0
//        var horizontalSpacing: CGFloat = 0
//
//        var nodes = [NodeLayout]()
//        var visualLines = [VisualLine]()
//        var xIndex: CGFloat = 0
//
//        // MARK: - Count total nodes
//        func countNodes(_ node: Node<T>?) -> Int {
//            guard let node = node else { return 0 }
//            return 1 + countNodes(node.left) + countNodes(node.right)
//        }
//
//        // MARK: - Recursive traversal
//        @discardableResult
//        func traverse(node: Node<T>, depth: Int) -> (CGPoint, CGPoint) {
//            var leftPos: CGPoint?
//            var rightPos: CGPoint?
//
//            // Traverse left subtree
//            if let left = node.left {
//                let (_, leftEnd) = traverse(node: left, depth: depth + 1)
//                leftPos = leftEnd
//            }
//
//            // Calculate current node position
//            if horizontalSpacing == 0 {
//                let totalWidthNodes = CGFloat(countNodes(root))
//                horizontalSpacing = (width - 2 * nodeRadius) / max(totalWidthNodes - 1, 1)
//            }
//
//            let x = nodeRadius + xIndex * horizontalSpacing
//            let y = nodeRadius + CGFloat(depth) * verticalSpacing
//            let currentPos = CGPoint(x: x, y: y)
//            xIndex += 1
//
//            // Traverse right subtree
//            if let right = node.right {
//                let (_, rightEnd) = traverse(node: right, depth: depth + 1)
//                rightPos = rightEnd
//            }
//
//            // Add connecting lines
//            if let left = leftPos {
//                visualLines.append(VisualLine(start: currentPos, end: left))
//            }
//            if let right = rightPos {
//                visualLines.append(VisualLine(start: currentPos, end: right))
//            }
//
//            // Add node layout
//            nodes.append(NodeLayout(center: currentPos, label: node.label, id: node.id))
//            return (currentPos, currentPos)
//        }
//
//        // Start traversal
//        _ = traverse(node: root, depth: 0)
//
//        // Return final layout
//        return VisualTree(nodes: nodes, lines: visualLines)
//    }
//}


//
//// MARK: - Donald Knuth Algorithm Implementation
//final class DonaldKnuthAlgorithm<T: Comparable>: LayoutAlgorithm {
//    func calculateTreeLayout(root: Node<T>, width: CGFloat, height: CGFloat) -> VisualTree {
//        let maxDepth = root.getDepth()
//        let nodeRadius: CGFloat = 10
//        let verticalSpacing: CGFloat =
//            maxDepth > 0 ? (height - 2 * nodeRadius) / CGFloat(maxDepth) : 0
//        var horizontalSpacing: CGFloat = 0
//
//        var nodes = [NodeLayout]()
//        var visualLines = [VisualLine]()
//        var xIndex: CGFloat = 0
//
//        // Count total nodes recursively
//        func countNodes(_ node: Node<T>?) -> Int {
//            guard let node = node else { return 0 }
//            return 1 + countNodes(node.left) + countNodes(node.right)
//        }
//
//        // Recursive traversal
//        @discardableResult
//        func traverse(node: Node<T>, depth: Int) -> CGPoint {
//            var leftPos: CGPoint?
//            if let left = node.left {
//                let leftPosResult = traverse(node: left, depth: depth + 1)
//                leftPos = leftPosResult
//                visualLines.append(VisualLine(start: leftPosResult, end: CGPoint.zero)) // placeholder
//            }
//
//            if horizontalSpacing == 0 {
//                let totalWidthNodes = CGFloat(countNodes(root))
//                horizontalSpacing =
//                    totalWidthNodes > 1
//                        ? (width - 2 * nodeRadius) / (totalWidthNodes - 1)
//                        : 0
//            }
//
//            let x = nodeRadius + xIndex * horizontalSpacing
//            let y = nodeRadius + CGFloat(depth) * verticalSpacing
//            let currentPos = CGPoint(x: x, y: y)
//            xIndex += 1
//
//            var rightPos: CGPoint?
//            if let right = node.right {
//                let rightPosResult = traverse(node: right, depth: depth + 1)
//                rightPos = rightPosResult
//                visualLines.append(VisualLine(start: currentPos, end: rightPosResult))
//            }
//
//            if let leftPos = leftPos {
//                visualLines.append(VisualLine(start: currentPos, end: leftPos))
//            }
//            if let rightPos = rightPos {
//                visualLines.append(VisualLine(start: currentPos, end: rightPos))
//            }
//
//            nodes.append(NodeLayout(center: currentPos, label: node.label, id: node.id))
//            return currentPos
//        }
//
//        _ = traverse(node: root, depth: 0)
//        return VisualTree(nodes: nodes, lines: visualLines)
//    }
//}
