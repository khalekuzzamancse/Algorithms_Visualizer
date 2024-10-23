@file:Suppress("functionName")

package mst.ui

import androidx.compose.ui.graphics.Color
import graph.graph.common.model.GraphResult
import graph.graph.common.model.Node
import graph.graph.viewer.controller.GraphViewerController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mst.di.DiContainer
import mst.domain.model.DijkstraGraphModel
import mst.domain.model.EdgeModel
import mst.domain.model.NodeModel
import mst.domain.model.SimulationState
import mst.domain.service.Simulator
import mst.presentationlogic.factory.AutoPlayerImpl

class SimulationViewModel(
    private val color: StatusColor
) {
    lateinit var graphController: GraphViewerController
    private lateinit var simulator: Simulator
    val autoPlayer = AutoPlayerImpl(::onNext)
    private lateinit var result: GraphResult

    private val _isInputMode = MutableStateFlow(true)
    val isInputMode = _isInputMode.asStateFlow()
    private val _code = MutableStateFlow<String?>(null)
    val code = _code.asStateFlow()


    fun onGraphCreated(result: GraphResult) {
        this.result = result
        graphController = result.controller
        simulator = DiContainer.createSimulator(_createGraph())
        _isInputMode.update { false }

    }

    fun onNext() {
        val state = simulator.next()
        _code.update { state.code }

        when (state) {
            is SimulationState.ProcessingNode -> handleProcessingNode(state.node)
            is SimulationState.ProcessingEdge -> handleProcessingEdge(state.edge)
           is SimulationState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
    }


    private fun handleProcessingNode(node: NodeModel) {
        graphController.changeNodeColor(id = node.id, color = color.processedNode)
        graphController.blinkNode(node.id)
    }


    private fun handleProcessingEdge(edge: EdgeModel) {
        graphController.changeEdgeColor(id = edge.id, color = color.processingEdge)
    }


    private fun handleSimulationFinished() {
        graphController.filterEdgeByColor(color = color.processingEdge)
        graphController.stopBlinkAll()
    }


    fun onReset() {
        graphController = result.controller
        simulator = DiContainer.createSimulator(_createGraph())
        graphController.reset()
//        consumer.onReset()
//        consumer = _createConsumer()

    }

    private fun _createGraph(): DijkstraGraphModel {
        val nodeModels = result.nodes.map { it._toNodeModel() }.toSet()
        val edgeModels = result.edges.map {
            EdgeModel(
                id = it.id,
                u = it.from._toNodeModel(),
                v = it.to._toNodeModel(),
                cost = it.cost?.toInt() ?: 1
            )
        }.toSet()
        return DijkstraGraphModel(nodeModels, edgeModels, nodeModels.first())
    }


    private fun Node._toNodeModel() = NodeModel(
        id = id,
    )

}