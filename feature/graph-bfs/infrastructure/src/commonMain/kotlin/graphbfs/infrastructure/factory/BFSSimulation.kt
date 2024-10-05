@file:Suppress("FunctionName", "unused")

package graphbfs.infrastructure.factory

import graphbfs.domain.model.ColorModel
import graphbfs.domain.model.SimulationState
import graphbfs.infrastructure.services.Graph
import java.util.LinkedList


class BFSSimulation internal constructor(
    private val graph: Graph
) {
    private val queue = LinkedList<String>()
    private val source = graph.sourceNodeId

    fun start() = sequence {

        _initialize()

        queue.add(source)
        graph.updateColor(source, ColorModel.Gray)

        _onColorChanged(source, ColorModel.Gray)

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            yield(SimulationState.ExecutionAt(current))

            val unvisitedNeighbours = graph.getUnvisitedNeighbourOf(current)
            var neighbourSelectedOrder=unvisitedNeighbours

            if (unvisitedNeighbours.size > 1) {
                yield(SimulationState.NeighborSelection(
                    unvisitedNeighbors = unvisitedNeighbours,
                    callback = {
                        neighbourSelectedOrder = it
                    }
                ))
            }

            neighbourSelectedOrder.forEach { neighbour ->
                graph.updateColor(neighbour, ColorModel.Gray)
                queue.add(neighbour)
                graph.findEdge(current, neighbour)?.let { edge ->
                    _onEdgeProcessing(edge.id)
                }

                _onColorChanged(neighbour, ColorModel.Gray)
            }

            graph.updateColor(current, ColorModel.Black)
            _onColorChanged(current, ColorModel.Black)
        }
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
