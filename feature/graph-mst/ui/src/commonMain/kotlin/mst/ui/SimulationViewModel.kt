@file:Suppress("functionName")

package mst.ui

import androidx.compose.ui.graphics.Color
import graph.common.model.Edge
import graph.common.model.GraphResult
import graph.common.model.Node
import graph.viewer.GraphViewerController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mst.di.DiContainer
import mst.domain.model.DijkstraGraphModel
import mst.domain.model.EdgeModel
import mst.domain.model.NodeModel
import mst.domain.model.SimulationState
import mst.domain.service.Simulator

class SimulationViewModel {
    lateinit var graphController: GraphViewerController
    private lateinit var simulator: Simulator

    private val _isInputMode = MutableStateFlow(true)
    val isInputMode = _isInputMode.asStateFlow()

    fun onGraphCreated(result: GraphResult) {
        graphController = result.controller
        simulator = DiContainer.createSimulator(_createDijkstraGraph(result.nodes, result.edges))
        _isInputMode.update { false }

    }

    fun onNext() {
        when (val state = simulator.next()) {
            is SimulationState.ProcessingNode -> handleProcessingNode(state.node)
            is SimulationState.ProcessingEdge -> handleProcessingEdge(state.edge)
            SimulationState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
    }


    private fun handleProcessingNode(node: NodeModel) {
        graphController.changeNodeColor(id = node.id, color = Color.Blue)
    }


    private fun handleProcessingEdge(edge: EdgeModel) {
        graphController.changeEdgeColor(id = edge.id, color = Color.Green)
    }


    private fun handleSimulationFinished() {
        graphController.filterEdgeByColor(color = Color.Green)
    }


    private fun _createDijkstraGraph(nodes: Set<Node>, edges: Set<Edge>): DijkstraGraphModel {
        val nodeModels = nodes.map { it._toNodeModel() }.toSet()
        val edgeModels = edges.map {
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