@file:Suppress("functionName","className")

package graphbfs.ui.ui

import graphbfs.di.DiContainer
import androidx.compose.ui.graphics.Color
import graph.graph.common.model.GraphResult
import graph.graph.common.model.Node
import graph.graph.viewer.controller.GraphViewerController
import graphbfs.domain.model.ColorModel
import graphbfs.domain.model.EdgeModel
import graphbfs.domain.model.GraphModel
import graphbfs.domain.model.NodeModel
import graphbfs.domain.model.SimulationState
import graphbfs.domain.service.Simulator
import graphbfs.ui.presentationlogic.controller.Controller
import graphbfs.ui.presentationlogic.factory.AutoPlayerImpl
import graphbfs.ui.presentationlogic.factory.NeighborSelectorImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SimulationViewModel(
    private val color:NodeStatusColor
) {
    private lateinit var simulator: Simulator
    private lateinit var result: GraphResult
    private lateinit var consumer: _StateConsumer
    private val _isInputMode = MutableStateFlow(true)
    val inputMode = _isInputMode.asStateFlow()
    lateinit var graphController: GraphViewerController
    val autoPlayer = AutoPlayerImpl(::onNext)
    val neighborSelector = NeighborSelectorImpl()


    fun onGraphCreated(result: GraphResult) {
        this.result = result
        graphController = result.controller
        consumer = _createConsumer()

        simulator = DiContainer.createSimulator(_createGraph())
        _isInputMode.update { false }

    }

    fun onReset() {
        graphController = result.controller
        simulator = DiContainer.createSimulator(_createGraph())
        consumer.onReset()
        consumer = _createConsumer()

    }

    fun onNext() {
        val state = simulator.next()
        consumer.consume(state)
    }


    private fun _createGraph(): GraphModel {
        val nodeModels = result.nodes.map { it._toNodeModel() }.toSet()
        val edgeModels = result.edges.map {
            EdgeModel(
                id = it.id,
                u = it.from._toNodeModel(),
                v = it.to._toNodeModel(),
            )
        }.toSet()
        return GraphModel(
            isDirected = result.directed,
            nodes = nodeModels,
            edges = edgeModels,
            source = nodeModels.first()
        )
    }

    private fun Node._toNodeModel() = NodeModel(
        id = id,
    )

    private fun _createConsumer() = _StateConsumer(
        graphController = result.controller,
        autoPlayer = autoPlayer,
        neighborSelector = neighborSelector,
        statusColor = color
    )
}







private typealias UiNodeModel= graphbfs.ui.presentationlogic.model.NodeModel
private class _StateConsumer(
    private val statusColor: NodeStatusColor,
    private val autoPlayer: Controller.AutoPlayer,
    private val neighborSelector: Controller.NeighborSelector,
    private val graphController: GraphViewerController
) {

    fun consume(state: SimulationState) {
        val code=state.code
        println("Code\n:$code")
        println("-------------------------")
        when (state) {
            is SimulationState.ColorChanged -> onColorChanged(state.nodes)
            is SimulationState.ProcessingEdge -> handleProcessingEdge(state.id)
            is SimulationState.NeighborSelection -> {
                val neighbourIds: List<String> = state.unvisitedNeighbors
                val neighbours=graphController.getNodesById(neighbourIds)
                    .map { UiNodeModel(it.first, it.second) }
                    .toSet()

                if (!autoPlayer.isAutoPlayMode()) {
                    neighborSelector.onSelectionRequest(
                        nodes = neighbours,
                        callback = state.callback
                    )
                } else {
                    state.callback(neighbourIds)
                }

            }

            is SimulationState.ExecutionAt -> {
                handleControlAt(state.nodeId)
            }

           is SimulationState.Finished -> handleSimulationFinished()
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
                ColorModel.White -> statusColor.undiscovered
                ColorModel.Gray -> statusColor.discovered
                ColorModel.Black -> statusColor.processed
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