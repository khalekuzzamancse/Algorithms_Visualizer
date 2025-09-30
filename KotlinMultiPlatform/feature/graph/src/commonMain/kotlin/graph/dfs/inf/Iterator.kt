@file:Suppress("FunctionName", "unused")

package graph.dfs.inf
import graph._core.domain.ColorModel
import graph._core.domain.TraversalSimulationState
import graph._core.inf.Graph
import graph.dfs.domain.CodeStateModel
import graph.dfs.domain.DFSCodeGenerator
import java.util.Stack


class Iterator internal constructor(
    private val graph: Graph
) {
    /**Stack of node id*/
    private val stack = Stack<String>()
    private val source = graph.sourceNodeId
    private var model = CodeStateModel()
    private fun CodeStateModel._toCode() = DFSCodeGenerator.generate(this)
    fun start() = sequence {
        _initialize()

        stack.push(source)
        graph.updateColor(source, ColorModel.Gray)
        _onColorChanged(source, ColorModel.Gray)
        model = model.copy(stack = "$stack")
        yield(TraversalSimulationState.Misc(model._toCode()))//This pause is just for show the stack state

        while (stack.isNotEmpty()) {
            val current = stack.peek()
            model = model.copy(stack = "$stack", current = current, stackIsNotEmpty = "true")

            yield(TraversalSimulationState.ExecutionAt(current, code = model._toCode()))
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
                        TraversalSimulationState.NeighborSelection(
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

    private suspend fun SequenceScope<TraversalSimulationState>._initialize() {
        graph.getAllNodeIds().forEach { node ->
            graph.updateColor(node, ColorModel.White)
        }
        _onColorChanged(graph.getAllNodeIds().toList(), ColorModel.White)
    }

    private suspend fun SequenceScope<TraversalSimulationState>._onColorChanged(
        nodeIds: List<String>,
        color: ColorModel
    ) {
        yield(
            TraversalSimulationState.ColorChanged(
                nodeIds.mapNotNull { nodeId ->
                    graph.getNode(nodeId)?.let { node -> Pair(node, color) }
                }.toSet(),
                code = model._toCode()
            ),
        )
    }

    private suspend fun SequenceScope<TraversalSimulationState>._onColorChanged(
        nodeId: String,
        color: ColorModel
    ) {
        yield(
            TraversalSimulationState.ColorChanged(
                setOfNotNull(
                    graph.getNode(nodeId)?.let { node -> Pair(node, color) }
                ),
                code = model._toCode()
            )
        )
    }

    private suspend fun SequenceScope<TraversalSimulationState>._onEdgeProcessing(
        edgeId: String,
    ) {
        yield(
            TraversalSimulationState.ProcessingEdge(edgeId, code = model._toCode())
        )
    }

}