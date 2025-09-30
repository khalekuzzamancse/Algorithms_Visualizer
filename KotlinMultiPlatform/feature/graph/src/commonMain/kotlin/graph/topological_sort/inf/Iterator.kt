@file:Suppress("FunctionName", "unused")

package graph.topological_sort.inf


import graph._core.domain.ColorModel
import graph._core.inf.Graph
import graph.topological_sort.domain.CodeStateModel
import graph.topological_sort.domain.TopologicalCodeGenerator
import graph.topological_sort.domain.TopologicalSortState
import java.util.Stack

class Iterator internal constructor(
    private val graph: Graph
) {
    private var model= CodeStateModel()
    private fun CodeStateModel.toCode()= TopologicalCodeGenerator.generate(this)

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
                yield(TopologicalSortState.StartingNode(nodeId=nodeId, code = model.toCode()))
                _dfs(nodeId)
            }
        }

        yield(TopologicalSortState.TopologicalOrder(order = topologicalOrder.reversed() ,code = model.toCode()))
    }

    private suspend fun SequenceScope<TopologicalSortState>._dfs(nodeId: String) {
        stack.push(nodeId)
        visited.add(nodeId)
        graph.updateColor(nodeId, ColorModel.Gray)
        _onColorChanged(nodeId, ColorModel.Gray)

        yield(TopologicalSortState.ExecutionAt(nodeId=nodeId,code = model.toCode()))

        val neighbors = graph.getNeighborsOf(nodeId)
        for (neighbor in neighbors) {
            if (neighbor !in visited) {
                graph.findEdge(nodeId, neighbor)?.let { edge ->
                    _onEdgeProcessing(edge.id)
                }
                val edgeId=graph.findEdge(nodeId, neighbor)?.let {id->
                    yield(TopologicalSortState.TraversingEdge(id=id.id,code = model.toCode()))
                }

                _dfs(neighbor)
            }
        }

        stack.pop()
        graph.updateColor(nodeId, ColorModel.Black)
        _onColorChanged(nodeId, ColorModel.Black)
        topologicalOrder.add(nodeId)
        yield(TopologicalSortState.NodeProcessed(nodeId=nodeId,code = model.toCode()))
    }

    private suspend fun SequenceScope<TopologicalSortState>._initialize() {
        graph.getAllNodeIds().forEach { node ->
            graph.updateColor(node, ColorModel.White)
        }
        _onColorChanged(graph.getAllNodeIds().toList(), ColorModel.White)
    }

    private suspend fun SequenceScope<TopologicalSortState>._onColorChanged(
        nodeIds: List<String>,
        color: ColorModel
    ) {
        yield(
            TopologicalSortState.ColorChanged(
                nodeColors = nodeIds.mapNotNull { nodeId ->
                    graph.getNode(nodeId)?.let { node -> Pair(node, color) }
                }.toSet(),
                code = model.toCode()
            )
        )
    }

    private suspend fun SequenceScope<TopologicalSortState>._onColorChanged(
        nodeId: String,
        color: ColorModel
    ) {
        yield(
            TopologicalSortState.ColorChanged(
          nodeColors =       setOfNotNull(
                    graph.getNode(nodeId)?.let { node -> Pair(node, color) }
                )
                ,code = model.toCode()
            )
        )
    }

    private suspend fun SequenceScope<TopologicalSortState>._onEdgeProcessing(
        edgeId: String,
    ) {
        yield(
            TopologicalSortState.ProcessingEdge(edgeId=edgeId,code = model.toCode())
        )
    }
}
