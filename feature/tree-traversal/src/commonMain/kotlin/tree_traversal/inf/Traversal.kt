@file:Suppress("FunctionName", "ClassName")

package tree_traversal.inf

import tree_traversal.domain.model.BFSCodeStateModel
import tree_traversal.domain.model.DFSCodeStateModel
import tree_traversal.domain.model.SimulationState
import tree_traversal.domain.model.TraversalType
import tree_traversal.domain.model.TreeNode
import tree_traversal.domain.service.PseudocodeGenerator
import java.util.LinkedList
import java.util.Queue
import java.util.Stack

class Traversal(
    private val root: TreeNode,
    private val type: TraversalType // Accepts traversal type from the user
) {
    fun traverse(): Sequence<SimulationState> = when (type) {
        TraversalType.BFS -> _BFSTraversal(root).traversal()
        else -> _DFSTraversal(root, type).traverse()
    }

}

class _BFSTraversal(private val root: TreeNode) {
    private var model = BFSCodeStateModel()
    private fun BFSCodeStateModel._toCode() = PseudocodeGenerator.generate(TraversalType.BFS, this)
    private fun getCode() = model._toCode()

    fun traversal() = sequence {
        val queue: Queue<TreeNode> = LinkedList()
        val visited = mutableSetOf<TreeNode>()

        yield(SimulationState.Misc(getCode()))
        queue.add(root)
        visited.add(root)

        model = model.copy(queue="${queue.map { it.id }}", visited = "${visited.map { it.id }}", queueIsNotEmpty = "${queue.isNotEmpty()}")
        yield(SimulationState.Misc(getCode()))

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            model = model.copy(current= current.id)

            yield(SimulationState.ProcessingNode(current, code = getCode()))

            current.children.forEach { child ->
                val isNotVisited=!(visited.contains(child))
                model=model.copy(isNotVisited = "$child: $isNotVisited")
                if (isNotVisited) {
                    visited.add(child)
                    queue.add(child)
                    model=model.copy(
                        queue="${queue.map { it.id }}",
                        visited  = "${visited.map { it.id }}",
                    )
                }
            }
            model=model.copy(
                isNotVisited = BFSCodeStateModel.OUT_OF_SCOPE,
                current = BFSCodeStateModel.OUT_OF_SCOPE,queueIsNotEmpty = "${queue.isNotEmpty()}"
            )
        }

        yield(
            SimulationState.Finished(
                code = PseudocodeGenerator.generate(
                    TraversalType.BFS,
                    BFSCodeStateModel()
                )
            )
        )

    }
}

class _DFSTraversal(
    private val root: TreeNode,
    private val type: TraversalType // Accepts traversal type from the user
) {

    private var model = DFSCodeStateModel()
    private fun DFSCodeStateModel._toCode() = PseudocodeGenerator.generate(type, this)
    private fun getCode() = model._toCode()

    // The traverse function handles all traversal types
    fun traverse() = sequence {
        // DFS-based traversal
        val stack = Stack<TreeNode>()
        val visited = mutableSetOf<TreeNode>()
        stack.push(root)
        model = model.copy(
            stack = "${stack.map { it.id }}",
            stackIsNotEmpty = "${stack.isNotEmpty()}"
        )
        yield(SimulationState.Misc(code = getCode()))

        while (stack.isNotEmpty()) {
            model = model.copy(
                stack = "${stack.map { it.id }}",
            )
            yield(SimulationState.Misc(code = getCode()))

            val current = stack.peek()
            model = model.copy(current = current.id)

            when (type) {
                TraversalType.PRE_ORDER -> {
                    // Pre-order: Visit node before its children
                    val isNotVisited = !visited.contains(current)
                    model = model.copy(isNotVisited = "$isNotVisited")

                    if (isNotVisited) {
                        yield(
                            SimulationState.ProcessingNode(
                                current,
                                code = getCode()
                            )
                        )//visited
                        visited.add(current)
                    }
                    model = model.copy(visited = "${visited.map { it.id }}")

                    stack.pop()
                    val children = current.children.toList()//copy
                    children.asReversed()
                        .forEach { stack.push(it) } // Push children in reverse order
                    model = model.copy(
                        reverseOrderChildren = "${current.id}:${
                            children.asReversed().map { it.id }
                        }"
                    )
                    yield(SimulationState.Misc(code = getCode()))
                    model = model.copy(reverseOrderChildren = DFSCodeStateModel.OUT_OF_SCOPE)

                }

                TraversalType.POST_ORDER -> {
                    // Post-order: Visit node after its children
                    val isVisited = visited.contains(current)
                    model = model.copy(isVisited = "$isVisited")

                    if (visited.contains(current)) {
                        yield(SimulationState.ProcessingNode(current, code = getCode()))
                        stack.pop()
                    } else {
                        visited.add(current)
                        model = model.copy(visited = "${visited.map { it.id }}")
                        val children = current.children.toList()//copy
                        children.asReversed()
                            .forEach { stack.push(it) } // Push children in reverse order
                        model = model.copy(
                            reverseOrderChildren = "${current.id}:${
                                children.asReversed().map { it.id }
                            }"
                        )
                        yield(SimulationState.Misc(code = getCode()))
                        model = model.killReverseOrderChildren()
                    }
                }

//                TraversalType.DFS -> {
//                    ///TODO:Has a problem with this implementation
//                    // DFS: Visit node after its children have been processed
//                    if (!visited.contains(current)) {
//                        current.children.asReversed().forEach { stack.push(it) }
//                        visited.add(current)
//                    } else {
//                        yield(SimulationState.ProcessingNode(current, code = ""))//visited
//                        stack.pop()
//                    }
//                }

                TraversalType.BFS -> {}

            }
            model = model.killIsNotVisited().killIsVisited()
        }
        model = model.copy(stack = "${stack.map { it.id }}", stackIsNotEmpty = "false")
        yield(SimulationState.Misc(getCode()))
        model = model.killStack().killStackIsEmpty().killReverseOrderChildren().killStack()
            .killVisited()

        yield(SimulationState.Finished(getCode()))
    }
}


