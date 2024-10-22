@file:Suppress("FunctionName", "unused")

package graphtraversal.infrastructure.factory

import graphtraversal.domain.model.CodeStateModel
import graphtraversal.domain.model.ColorModel
import graphtraversal.domain.model.SimulationState
import graphtraversal.domain.service.PseudocodeGenerator
import graphtraversal.infrastructure.services.Graph
import java.util.Stack


class Iterator internal constructor(
    private val graph: Graph
) {
    /**Stack of node id*/
    private val stack = Stack<String>()
    private val source = graph.sourceNodeId
    private var model = CodeStateModel()
    private fun CodeStateModel._toCode() = PseudocodeGenerator.generate(this)
    fun start() = sequence {
        _initialize()

        stack.push(source)
        graph.updateColor(source, ColorModel.Gray)
        _onColorChanged(source, ColorModel.Gray)
        model = model.copy(stack = "$stack")
        yield(SimulationState.Misc(model._toCode()))//This pause is just for show the stack state

        while (stack.isNotEmpty()) {
            val current = stack.peek()
            model = model.copy(stack = "$stack", current = current, stackIsNotEmpty = "true")

            yield(SimulationState.ExecutionAt(current, code = model._toCode()))
            val unvisitedNeighbours = graph.getUnvisitedNeighbourOf(current)

            val hasAllNeighbourVisited = unvisitedNeighbours.isEmpty()
            model = model.copy(hasAllNeighbourProcessed = "$hasAllNeighbourVisited")

            if (hasAllNeighbourVisited) {
                stack.pop()
                graph.updateColor(current, ColorModel.Black)
                _onColorChanged(current, ColorModel.Black)
                model =
                    model.copy(stack = "$stack", current = current, oneUnvisitedNeighbour = "null")
            } else {
                var selectedNeighbour = unvisitedNeighbours.first()
                if (unvisitedNeighbours.size > 1) {
                    yield(
                        SimulationState.NeighborSelection(
                            unvisitedNeighbors = unvisitedNeighbours,
                            callback = {
                                selectedNeighbour = it
                            },
                            code = model._toCode()
                        ),
                    )
                }
                model = model.copy(
                    stack = "$stack",
                    current = current,
                    oneUnvisitedNeighbour = selectedNeighbour
                )

                graph.updateColor(selectedNeighbour, ColorModel.Gray)
                stack.push(selectedNeighbour)
                // parent = current
                graph.findEdge(current, selectedNeighbour)?.let { edge ->
                    _onEdgeProcessing(edge.id)
                }
                _onColorChanged(selectedNeighbour, ColorModel.Gray)
            }
            model = model
                .killCurrent()
                .killOneUnvisitedNeighbour()
                .killStackIsEmpty()
                .killHasAllNeighbourProcessed()

        }
        model = model.copy(stack = "$stack", stackIsNotEmpty = "false")

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
                }.toSet(),
                code = model._toCode()
            ),
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
                ),
                code = model._toCode()
            )
        )
    }

    private suspend fun SequenceScope<SimulationState>._onEdgeProcessing(
        edgeId: String,
    ) {
        yield(
            SimulationState.ProcessingEdge(edgeId, code = model._toCode())
        )
    }

}