@file:Suppress("FunctionName", "unused")

package graphtraversal.infrastructure.factory

import graphtraversal.domain.model.ColorModel
import graphtraversal.domain.model.SimulationState
import graphtraversal.infrastructure.services.Graph
import java.util.Stack


class DFSSimulation internal constructor(
    private val graph: Graph
) {
    /**Stack of node id*/
    private val stack = Stack<String>()
    private val source = graph.sourceNodeId

    fun start() = sequence {

        _initialize()

        stack.push(source)
        graph.updateColor(source, ColorModel.Gray)

        _onColorChanged(source, ColorModel.Gray)
        while (stack.isNotEmpty()) {
            val current = stack.peek()
            yield(SimulationState.ExecutionAt(current))
            val unvisitedNeighbours = graph.getUnvisitedNeighbourOf(current)

            val hasAllNeighbourVisited = unvisitedNeighbours.isEmpty()
            if (hasAllNeighbourVisited) {
                stack.pop()
                graph.updateColor(current, ColorModel.Black)
                _onColorChanged(current, ColorModel.Black)
            } else {
                var selectedNeighbour = unvisitedNeighbours.first()
                if (unvisitedNeighbours.size > 1) {
                    yield(SimulationState.NeighborSelection(
                        unvisitedNeighbors = unvisitedNeighbours,
                        callback = {
                            selectedNeighbour = it
                        }
                    ))
                }

                graph.updateColor(selectedNeighbour, ColorModel.Gray)
                stack.push(selectedNeighbour)
                // parent = current
                graph.findEdge(current, selectedNeighbour)?.let { edge ->
                    _onEdgeProcessing(edge.id)
                }
                _onColorChanged(selectedNeighbour, ColorModel.Gray)
            }
        }

//        while (stack.isNotEmpty()) {
//            val current = stack.peek()
//            val neighbour = graph.getOneUnvisitedNeighbourOf(current)
//
//            val hasAllNeighbourVisited = (neighbour == null)
//            if (hasAllNeighbourVisited) {
//                stack.pop()
//                graph.updateColor(current, ColorModel.Black)//mark as processed
//                _onColorChanged(current, ColorModel.Black)
//            } else {
//
//                graph.updateColor(neighbour!!, ColorModel.Gray)
//                stack.push(neighbour)
//                //parent=current
//               graph.findEdge(current, neighbour)?.let { edge->
//                   _onEdgeProcessing(edge.id)
//               }
//                _onColorChanged(neighbour, ColorModel.Gray)
//
//
//            }
//        }


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