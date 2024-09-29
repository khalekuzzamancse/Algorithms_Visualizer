@file:Suppress("functionName")

package tree.ui

import androidx.compose.ui.graphics.Color
import graph.graph.common.model.Edge
import graph.graph.common.model.GraphResult
import graph.graph.common.model.Node
import graph.graph.viewer.GraphViewerController
import graph.tree.TreeResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tree.di.DiContainer
import tree.domain.model.DijkstraGraphModel
import tree.domain.model.EdgeModel
import tree.domain.model.NodeModel
import tree.domain.model.SimulationState
import tree.domain.service.Simulator

class SimulationViewModel {
    lateinit var graphController: GraphViewerController
    private lateinit var simulator: Simulator

    private val _isInputMode = MutableStateFlow(true)
    val isInputMode = _isInputMode.asStateFlow()

    fun onGraphCreated(result: TreeResult) {
        graphController = result.controller
        println(result.root)

        _isInputMode.update { false }

    }

    fun onNext() {
//        when (val state = simulator.next()) {
//            is SimulationState.ProcessingNode -> handleProcessingNode(state.node)
//            is SimulationState.ProcessingEdge -> handleProcessingEdge(state.edge)
//            SimulationState.Finished -> handleSimulationFinished()
//            else -> Unit // No action for other states
//        }
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