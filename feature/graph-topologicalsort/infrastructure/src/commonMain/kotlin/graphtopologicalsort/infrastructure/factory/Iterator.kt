@file:Suppress("FunctionName", "unused")

package graphtopologicalsort.infrastructure.factory

import graphtopologicalsort.domain.model.ColorModel
import graphtopologicalsort.domain.model.SimulationState
import graphtopologicalsort.infrastructure.services.Graph


import java.util.*

class Iterator internal constructor(
    private val graph: Graph
) {
    /** Stack for DFS traversal */
    private val stack = Stack<String>()
    /** List to store the topological order */
    private val topologicalOrder = mutableListOf<String>()
    /** Set to keep track of visited nodes */
    private val visited = mutableSetOf<String>()

    fun start() = sequence {
        _initialize()

        // Iterate over all nodes in the graph
        for (nodeId in graph.getAllNodeIds()) {
            if (nodeId !in visited) {
                yield(SimulationState.StartingNode(nodeId))
                _dfs(nodeId)
            }
        }

        yield(SimulationState.TopologicalOrder(topologicalOrder.reversed()))
    }

    private suspend fun SequenceScope<SimulationState>._dfs(nodeId: String) {
        stack.push(nodeId)
        visited.add(nodeId)
        graph.updateColor(nodeId, ColorModel.Gray)
        _onColorChanged(nodeId, ColorModel.Gray)

        yield(SimulationState.ExecutionAt(nodeId))

        val neighbors = graph.getNeighborsOf(nodeId)
        for (neighbor in neighbors) {
            if (neighbor !in visited) {
                graph.findEdge(nodeId, neighbor)?.let { edge ->
                    _onEdgeProcessing(edge.id)
                }
                val edgeId=graph.findEdge(nodeId, neighbor)?.let {id->
                    yield(SimulationState.TraversingEdge(id.id))
                }

                _dfs(neighbor)
            }
        }

        stack.pop()
        graph.updateColor(nodeId, ColorModel.Black)
        _onColorChanged(nodeId, ColorModel.Black)
        topologicalOrder.add(nodeId)
        yield(SimulationState.NodeProcessed(nodeId))
    }

    private suspend fun SequenceScope<SimulationState>._initialize() {
        graph.getAllNodeIds().forEach { node ->
            graph.updateColor(node, ColorModel.White)
        }
        _onColorChanged(graph.getAllNodeIds().toList(), ColorModel.White)
    }

    private suspend fun SequenceScope<SimulationState>._onColorChanged(
        nodeIds: List<String>,
        color: ColorModel
    ) {
        yield(
            SimulationState.ColorChanged(
                nodeIds.mapNotNull { nodeId ->
                    graph.getNode(nodeId)?.let { node -> Pair(node, color) }
                }.toSet()
            )
        )
    }

    private suspend fun SequenceScope<SimulationState>._onColorChanged(
        nodeId: String,
        color: ColorModel
    ) {
        yield(
            SimulationState.ColorChanged(
                setOfNotNull(
                    graph.getNode(nodeId)?.let { node -> Pair(node, color) }
                )
            )
        )
    }

    private suspend fun SequenceScope<SimulationState>._onEdgeProcessing(
        edgeId: String,
    ) {
        yield(
            SimulationState.ProcessingEdge(edgeId)
        )
    }
}
