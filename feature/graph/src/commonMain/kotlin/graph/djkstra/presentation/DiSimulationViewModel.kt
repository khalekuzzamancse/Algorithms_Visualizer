@file:Suppress("functionName")
package graph.djkstra.presentation

import core_ui.core.controller.AutoPlayerImpl
import core_ui.graph.common.model.GraphResult
import core_ui.graph.common.model.Node
import core_ui.graph.viewer.controller.GraphViewerController
import graph.DiContainer
import graph.djkstra.domain.model.DijkstraGraphModel
import graph.djkstra.domain.model.EdgeModel
import graph.djkstra.domain.model.NodeModel
import graph.djkstra.domain.model.NodeModel.Companion.INFINITY
import graph.djkstra.domain.model.SimulationState
import graph.djkstra.domain.service.Simulator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DiSimulationViewModel (
    private val color: StatusColor
){
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
        when ( state ) {
            is SimulationState.ProcessingNode -> handleProcessingNode(state.node)
            is SimulationState.ProcessingEdge -> handleProcessingEdge(state.edge)
            is SimulationState.DistanceUpdated -> handleDistanceUpdated(state.nodes)
            is SimulationState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
    }

    fun onReset() {
        graphController = result.controller
        simulator = DiContainer.createSimulator(_createGraph())
        graphController.reset()
        autoPlayer.dismiss()
//        consumer.onReset()
//        consumer = _createConsumer()

    }

    private fun handleProcessingNode(node: NodeModel) {
        graphController.changeNodeColor(id = node.id, color = color.processedNode)
        graphController.blinkNode(node.id)
    }


    private fun handleProcessingEdge(edge: EdgeModel) {
        graphController.changeEdgeColor(id = edge.id, color = color.processingEdge)
    }


    private fun handleDistanceUpdated(nodes: Set<NodeModel>) {
        nodes.forEach {
            val distance = if (it.distance == INFINITY) "∞" else it.distance.toString()
            graphController.updateDistance(id = it.id, distance = distance)
        }
    }

    private fun handleSimulationFinished() {
        graphController.stopBlinkAll()
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
        id = id, label = label
    )



}