package topological_sort.infrastructure

import topological_sort.domain.AlgorithmicGraph
import topological_sort.domain.AlgorithmicNode
import topological_sort.domain.GraphNodeColor
import topological_sort.domain.SimulationState
import java.util.Stack


@Suppress("ClassName")
internal class _DFSSequenceBuilder(
    graph: AlgorithmicGraph,
    private val startNode: AlgorithmicNode
) {
    // Using a mutable map to keep track of nodes and their updated colors
    private val adjacencyList =
        graph.adjacencyList.mapValues { it.value.toMutableList() }.toMutableMap()
    private var peekNode: AlgorithmicNode? = null
    val iterator: Sequence<SimulationState> = sequence {

        val stack = Stack<AlgorithmicNode>()

        // Initialize the DFS traversal
        val initialStartNode = startNode.copy(color = GraphNodeColor.Gray) // Make it visited
        updateAdjacencyList(startNode, initialStartNode)
        stack.push(initialStartNode)


        while (stack.isNotEmpty()) {
            peekNode = stack.peek() //update the pick node so that it highlighted in the UI


            val top = stack.peek()
            val unvisitedNeighbor = getOneUnvisitedNeighbor(top)

            if (unvisitedNeighbor == null) {
                val updatedTop = top.copy(color = GraphNodeColor.Black)
                stack.pop() // Backtracking
                updateAdjacencyList(top, updatedTop)

                //push update to UI
                pushUpdate(SimulationState.Running(updatedTop, pseudocode = emptyList(), peekNode = peekNode))


            } else {
                val updatedNeighbor =
                    unvisitedNeighbor.copy(color = GraphNodeColor.Gray) // Make it visited
                stack.push(updatedNeighbor)
                updateAdjacencyList(unvisitedNeighbor, updatedNeighbor)


                pushUpdate(SimulationState.Running(updatedNeighbor, emptyList(),peekNode))  //push update to UI
            }
        }


    }

    private fun getOneUnvisitedNeighbor(
        node: AlgorithmicNode,
    ): AlgorithmicNode? {
        val neighbors = adjacencyList[node] ?: listOf()
        return neighbors.firstOrNull { it.color == GraphNodeColor.White }
    }

    private fun updateAdjacencyList(
        oldNode: AlgorithmicNode,
        newNode: AlgorithmicNode
    ) {
        val neighbors = adjacencyList.remove(oldNode) ?: return
        adjacencyList[newNode] =
            neighbors.map { if (it == oldNode) newNode else it }.toMutableList()
        for ((key, value) in adjacencyList) {
            adjacencyList[key] = value.map { if (it == oldNode) newNode else it }.toMutableList()
        }
    }

    private suspend fun SequenceScope<SimulationState>.pushUpdate(state: SimulationState) {
        yield(state)
    }
}


@Suppress("ClassName")
private class _BFSSequenceBuilder(
    private val graph: AlgorithmicGraph,
    private val startNode: AlgorithmicNode
) {

    val iterator: Sequence<SimulationState> = sequence {
        val visited = mutableSetOf<AlgorithmicNode>()
        val queue = ArrayDeque<AlgorithmicNode>()


        //TODO:check the node is exits or note in the graph...user might send a node that does not belongs to this graph

        // Start with the root node
        queue.add(startNode)
        visited.add(startNode)


        while (queue.isNotEmpty()) {
            val currentNode = queue.removeFirst()


            // Get the adjacent nodes from the graph
            val neighbors = graph.adjacencyList[currentNode] ?: listOf()

            // Visit each neighbor that hasn't been visited
            for (neighbor in neighbors) {
                if (neighbor !in visited) {
                    visited.add(neighbor)
                    queue.add(neighbor)
                }
            }

        }

    }


    private suspend fun SequenceScope<SimulationState>.pushUpdate(state: SimulationState) {
        yield(state)
    }

}
