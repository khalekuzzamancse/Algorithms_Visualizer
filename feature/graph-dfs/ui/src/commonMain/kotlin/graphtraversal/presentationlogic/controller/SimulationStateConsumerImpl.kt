package graphtraversal.presentationlogic.controller

import androidx.compose.ui.graphics.Color
import graph.graph.common.model.Node
import graph.graph.viewer.controller.GraphViewerController
import graphtraversal.domain.model.ColorModel
import graphtraversal.domain.model.NodeModel
import graphtraversal.domain.model.SimulationState

class SimulationStateConsumerImpl(
     val graphController: GraphViewerController,
    private val autoPlayer: Controller.AutoPlayer,
    private val neighborSelector: Controller.NeighborSelector,
    private val nodes: List<Node>
) {

    fun consume(state: SimulationState) {
        when (state) {
            is SimulationState.ColorChanged -> onColorChanged(state.nodes)
            is SimulationState.ProcessingEdge -> handleProcessingEdge(state.id)
            is SimulationState.NeighborSelection -> {
                val neighbourIds: List<String> = state.unvisitedNeighbors

                if (!autoPlayer.isAutoPlayMode()) {
                    neighborSelector.onSelectionRequest(
                        nodes = nodes
                            .filter { node -> node.id in neighbourIds }
                            .map { node -> Pair(node.id, node.label) }
                            .toSet(),
                        callback = state.callback
                    )
                } else {
                    state.callback(neighbourIds.first())
                }

            }

            is SimulationState.ExecutionAt -> {
                handleControlAt(state.nodeId)
            }

            SimulationState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
    }

    fun onReset() {
        graphController.reset()
        autoPlayer.dismiss()
    }
    private fun onColorChanged(pairs: Set<Pair<NodeModel, ColorModel>>) {
        pairs.forEach { (node, color) ->
            val nodeColor = when (color) {
                ColorModel.White -> Color.White.copy(alpha = 0.8f)
                ColorModel.Gray -> Color.Gray
                ColorModel.Black -> Color.Black
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