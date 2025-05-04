package graph._core.presentation

import androidx.compose.ui.graphics.Color
import core_ui.GlobalColors
import core_ui.graph.common.model.GraphResult
import graph.DiContainer
import graph._core.domain.ColorModel
import graph._core.domain.GraphModel
import graph._core.domain.NodeModel
import graph._core.domain.TraversalSimulationState
import graph._core.domain.TraversalSimulator
import kotlinx.coroutines.flow.update

internal abstract  class TraversalRouteController : BaseRouteController(){
    abstract  fun createSimulator(graph: GraphModel):TraversalSimulator
    private lateinit  var traversalSimulator:TraversalSimulator
    override fun onGraphCreated(result: GraphResult) {
        super.onGraphCreated(result)
        traversalSimulator = DiContainer.createBFSSimulator(_createGraph())
    }
    override fun onNext() {
        val state = traversalSimulator.next()
        consume(state)
    }
    private fun consume(state: TraversalSimulationState) {
        _code.update { state.code }
        when (state) {
            is TraversalSimulationState.ColorChanged -> onColorChanged(state.nodes)
            is TraversalSimulationState.ProcessingEdge -> handleProcessingEdge(state.id)
            is TraversalSimulationState.NeighborSelection -> {
                val neighbourIds: List<String> = state.unvisitedNeighbors
                val neighbours = graphController.getNodesById(neighbourIds)
                    .map { UiNodeModel(it.first, it.second) }
                    .toSet()
                state.callback(neighbourIds.first())
            }

            is TraversalSimulationState.ExecutionAt -> {
                handleControlAt(state.nodeId)
            }

            is TraversalSimulationState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
    }

    override fun onReset() {
        super.onReset()
        traversalSimulator = DiContainer.createBFSSimulator(_createGraph())
    }

    private fun onColorChanged(pairs: Set<Pair<NodeModel, ColorModel>>) {
        pairs.forEach { (node, color) ->
            val nodeColor = when (color) {
                ColorModel.White -> GlobalColors.GraphColor.UNDISCOVERD
                ColorModel.Gray -> GlobalColors.GraphColor.DISCOVERED
                ColorModel.Black -> GlobalColors.GraphColor.PROCCED
            }
            graphController.changeNodeColor(id = node.id, color = nodeColor)

        }

    }

    private fun handleControlAt(nodeId: String) {
        graphController.blinkNode(nodeId)
    }

    private fun handleProcessingEdge(id: String) {
        graphController.changeEdgeColor(id = id, color = Color.Green)
    }


    private fun handleSimulationFinished() {
        graphController.stopBlinkAll()
        autoPlayer.dismiss()
    }
}