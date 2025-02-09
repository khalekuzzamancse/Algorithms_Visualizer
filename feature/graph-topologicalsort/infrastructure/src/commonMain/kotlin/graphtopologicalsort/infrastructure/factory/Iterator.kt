@file:Suppress("FunctionName", "unused")

package graphtopologicalsort.infrastructure.factory

import graphtopologicalsort.domain.model.ColorModel
import graphtopologicalsort.domain.model.SimulationState
import graphtopologicalsort.domain.service.CodeStateModel
import graphtopologicalsort.domain.service.PseudocodeGenerator
import graphtopologicalsort.infrastructure.services.Graph


import java.util.*

class Iterator internal constructor(
    private val graph: Graph
) {
    private var model= CodeStateModel()
    private fun  CodeStateModel.toCode()= PseudocodeGenerator.generate(this)

    /** Stack for DFS traversal */
    private val stack = Stack<String>()
    /** List to store the topological order */
    private val topologicalOrder = mutableListOf<String>()
    /** Set to keep track of visited nodes */
    private val visited = mutableSetOf<String>()

    fun start() = sequence {

        _initialize()
        //model=model.copy(pendingNodes = "$notAddedToMST", source = startNodeId)
        // Iterate over all nodes in the graph
        for (nodeId in graph.getAllNodeIds()) {
            if (nodeId !in visited) {
                yield(SimulationState.StartingNode(nodeId=nodeId, code = model.toCode()))
                _dfs(nodeId)
            }
        }

        yield(SimulationState.TopologicalOrder(order = topologicalOrder.reversed() ,code = model.toCode()))
    }

    private suspend fun SequenceScope<SimulationState>._dfs(nodeId: String) {
        stack.push(nodeId)
        visited.add(nodeId)
        graph.updateColor(nodeId, ColorModel.Gray)
        _onColorChanged(nodeId, ColorModel.Gray)

        yield(SimulationState.ExecutionAt(nodeId=nodeId,code = model.toCode()))

        val neighbors = graph.getNeighborsOf(nodeId)
        for (neighbor in neighbors) {
            if (neighbor !in visited) {
                graph.findEdge(nodeId, neighbor)?.let { edge ->
                    _onEdgeProcessing(edge.id)
                }
                val edgeId=graph.findEdge(nodeId, neighbor)?.let {id->
                    yield(SimulationState.TraversingEdge(id=id.id,code = model.toCode()))
                }

                _dfs(neighbor)
            }
        }

        stack.pop()
        graph.updateColor(nodeId, ColorModel.Black)
        _onColorChanged(nodeId, ColorModel.Black)
        topologicalOrder.add(nodeId)
        yield(SimulationState.NodeProcessed(nodeId=nodeId,code = model.toCode()))
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
                nodeColors = nodeIds.mapNotNull { nodeId ->
                    graph.getNode(nodeId)?.let { node -> Pair(node, color) }
                }.toSet(),
                code = model.toCode()
            )
        )
    }

    private suspend fun SequenceScope<SimulationState>._onColorChanged(
        nodeId: String,
        color: ColorModel
    ) {
        yield(
            SimulationState.ColorChanged(
          nodeColors =       setOfNotNull(
                    graph.getNode(nodeId)?.let { node -> Pair(node, color) }
                )
                ,code = model.toCode()
            )
        )
    }

    private suspend fun SequenceScope<SimulationState>._onEdgeProcessing(
        edgeId: String,
    ) {
        yield(
            SimulationState.ProcessingEdge(edgeId=edgeId,code = model.toCode())
        )
    }
}
