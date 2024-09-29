package tree.infrastructure.factory

import tree.domain.model.SimulationState
import tree.domain.model.TraversalType
import tree.domain.model.TreeNode
import java.util.LinkedList
import java.util.Queue
import java.util.Stack

class DFSTraversal(
    private val root: TreeNode,
    private val type: TraversalType // Accepts traversal type from the user
) {

    // The traverse function handles all traversal types
    fun traverse() = sequence<SimulationState> {
        when (type) {
            TraversalType.BFS -> {
                bfsTraversal()
            }
            else -> {
                // DFS-based traversal
                val stack = Stack<TreeNode>()
                val visited = mutableSetOf<TreeNode>()

                stack.push(root)

                while (stack.isNotEmpty()) {
                    val node = stack.peek()

                    when (type) {
                        TraversalType.PRE_ORDER -> {
                            // Pre-order: Visit node before its children
                            if (!visited.contains(node)) {
                                yield(SimulationState.ProcessingNode(node))//visited
                                visited.add(node)
                            }
                            stack.pop()
                            node.children.asReversed().forEach { stack.push(it) } // Push children in reverse order
                        }
                        TraversalType.POST_ORDER -> {
                            // Post-order: Visit node after its children
                            if (visited.contains(node)) {
                                yield(SimulationState.ProcessingNode(node))//visited
                                stack.pop()
                            } else {
                                visited.add(node)
                                node.children.asReversed().forEach { stack.push(it) } // Push children in reverse order
                            }
                        }
                        TraversalType.DFS -> {
                            // DFS: Visit node after its children have been processed
                            if (!visited.contains(node)) {
                                node.children.asReversed().forEach { stack.push(it) }
                                visited.add(node)
                            } else {
                                yield(SimulationState.ProcessingNode(node))//visited
                                stack.pop()
                            }
                        }

                        TraversalType.BFS -> {

                        }
                    }
                }
                yield(SimulationState.Finished)
            }
        }
    }

    // BFS traversal function that yields a sequence of nodes
    private suspend fun SequenceScope<SimulationState>.bfsTraversal() {
        val queue: Queue<TreeNode> = LinkedList()
        val visited = mutableSetOf<TreeNode>()

        queue.add(root)
        visited.add(root)

        while (queue.isNotEmpty()) {
            val node = queue.poll()

            yield(SimulationState.ProcessingNode(node))

            node.children.forEach { child ->
                if (!visited.contains(child)) {
                    visited.add(child)
                    queue.add(child)
                }
            }
        }

        yield(SimulationState.Finished)
    }
}

