@file:Suppress("FunctionName", "unused")

package graphbfs.infrastructure.factory

import graphbfs.domain.model.CodeStateModel
import graphbfs.domain.model.ColorModel
import graphbfs.domain.model.SimulationState
import graphbfs.domain.service.PseudocodeGenerator
import graphbfs.infrastructure.services.Graph
import java.util.LinkedList


class Iterator internal constructor(
    private val graph: Graph
) {
    private val queue = LinkedList<String>()
    private val source = graph.sourceNodeId
    private var model=CodeStateModel()
    private fun CodeStateModel.toCode()=PseudocodeGenerator.generate(this)

    //TODO:Keep the node label and the Id are the same, they not same then define a method to get label by id
    //TODO:Right now assuming that id and label are the same and using id as label
    fun start() = sequence {

        _initialize()
        model=model.copy(source=source,queue = "[ ]")
        yield(SimulationState.Start(code = model.toCode()))


        queue.add(source)

        model=model.copy(source=source,queue = "$queue")
        yield(SimulationState.Start(code = model.toCode()))



        graph.updateColor(source, ColorModel.Gray)
        _onColorChanged(source, ColorModel.Gray)

        while (queue.isNotEmpty()) {
            model=model.killUnvisitedNeighbours()
            val current = queue.poll()

            model=model.copy(source=source, queue = "$queue", current = current)
            yield(SimulationState.ExecutionAt(current,model.toCode()))

            val unvisitedNeighbours = graph.getUnvisitedNeighbourOf(current)
            var neighbourSelectedOrder=unvisitedNeighbours

//            if (unvisitedNeighbours.size > 1) {
//                yield(SimulationState.NeighborSelection(
//                    unvisitedNeighbors = unvisitedNeighbours,
//                    callback = { neighbourSelectedOrder = it },
//                    code = model.toCode()
//                    )
//
//                )
//            }

            model=model.copy( queue = "$queue",unvisitedNeighbours="$neighbourSelectedOrder")
            neighbourSelectedOrder.forEach { neighbour ->
                graph.updateColor(neighbour, ColorModel.Gray)
                queue.add(neighbour)
                graph.findEdge(current, neighbour)?.let { edge ->
                    _onEdgeProcessing(edge.id)
                }

                _onColorChanged(neighbour, ColorModel.Gray)
            }
            model=model.killUnvisitedNeighbours()

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
        model=model.copy( queue = "$queue")
        yield(
            SimulationState.ColorChanged(
                nodeIds.mapNotNull { nodeId ->
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
        model=model.copy( queue = "$queue")
        yield(
            SimulationState.ColorChanged(
                setOfNotNull(
                    graph.getNode(nodeId)?.let { node -> Pair(node, color) },
                ),
                code = model.toCode()
            )
        )
    }

    private suspend fun SequenceScope<SimulationState>._onEdgeProcessing(
        edgeId: String,
    ) {
        model=model.copy( queue = "$queue")
        yield(
            SimulationState.ProcessingEdge(edgeId,model.toCode())
        )
    }
}
