package dfs.infrastructure

import dfs.domain.AlgoSimulator
import dfs.domain.AlgorithmicGraph
import dfs.domain.AlgorithmicNode
import dfs.domain.GraphNodeColor
import dfs.domain.SimulationState
import java.util.Stack

internal class AlgoSimulatorImpl(
    graph: AlgorithmicGraph,
    startNode: AlgorithmicNode
) : AlgoSimulator {

    private val sequenceBuilder = _DFSSequenceBuilder(graph, startNode)
    private val iterator = sequenceBuilder.iterator.iterator()

    override fun next(): SimulationState {
        return if (iterator.hasNext()) {
            val res = iterator.next()
            log(res.toString())
            res
        } else
            SimulationState.Finished
    }

    @Suppress("Unused")
    private fun log(message: String, methodName: String? = null) {
        val tag = "${this@AlgoSimulatorImpl::class.simpleName}Log:"
        val method = if (methodName == null) "" else "$methodName()'s "
        val msg = "$method:-> $message"
        println("$tag::$msg")
    }

}

@Suppress("ClassName")
private class _DFSSequenceBuilder(
    private val graph: AlgorithmicGraph,
    private val startNode: AlgorithmicNode
) {
    // Using a mutable map to keep track of nodes and their updated colors
    val adjacencyList =
        graph.adjacencyList.mapValues { it.value.toMutableList() }.toMutableMap()
    val iterator: Sequence<SimulationState> = sequence {


        val stack = Stack<AlgorithmicNode>()


        // Initialize the DFS traversal
        val initialStartNode = startNode.copy(color = GraphNodeColor.Gray) // Make it visited
        updateAdjacencyList(startNode, initialStartNode)
        stack.push(initialStartNode)
        pushUpdate(SimulationState.Running(initialStartNode, emptyList()))

        while (stack.isNotEmpty()) {
            val top = stack.peek()
            val unvisitedNeighbor = getOneUnvisitedNeighbor(top)

            if (unvisitedNeighbor == null) {
                val updatedTop = top.copy(color = GraphNodeColor.Black)
                stack.pop() // Backtracking
                updateAdjacencyList(top, updatedTop)
                pushUpdate(SimulationState.Running(updatedTop, emptyList()))
            } else {
                val updatedNeighbor =
                    unvisitedNeighbor.copy(color = GraphNodeColor.Gray) // Make it visited
                stack.push(updatedNeighbor)
                updateAdjacencyList(unvisitedNeighbor, updatedNeighbor)
                pushUpdate(SimulationState.Running(updatedNeighbor, emptyList()))
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
