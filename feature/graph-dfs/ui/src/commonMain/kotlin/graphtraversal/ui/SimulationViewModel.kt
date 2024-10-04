@file:Suppress("functionName")

package graphtraversal.ui

import DiContainer
import androidx.compose.ui.graphics.Color
import graph.graph.common.model.Edge
import graph.graph.common.model.GraphResult
import graph.graph.common.model.Node
import graph.graph.viewer.controller.GraphViewerController
import graphtraversal.domain.model.ColorModel
import graphtraversal.domain.model.EdgeModel
import graphtraversal.domain.model.GraphModel
import graphtraversal.domain.model.NodeModel
import graphtraversal.domain.model.SimulationState
import graphtraversal.domain.service.Simulator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SimulationViewModel {
    lateinit var graphController: GraphViewerController
    private lateinit var simulator: Simulator


    private val _isInputMode = MutableStateFlow(true)
    val isInputMode = _isInputMode.asStateFlow()
    private val _autoPlayTime = MutableStateFlow<Int?>(null)



    fun onGraphCreated(result: GraphResult) {
        graphController = result.controller

        simulator = DiContainer.createSimulator(
            _createGraph(directed = result.directed, nodes = result.nodes, edges = result.edges)
        )
        _isInputMode.update { false }

    }

    fun onNext() {
        when (val state = simulator.next()) {
            is SimulationState.ColorChanged -> onColorChanged(state.nodes)
            is SimulationState.ProcessingEdge -> handleProcessingEdge(state.id)
            SimulationState.Finished -> handleSimulationFinished()
            else -> Unit // No action for other states
        }
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


    private fun handleProcessingEdge(id: String) {
        graphController.changeEdgeColor(id = id, color = Color.Green)
    }


    private fun handleSimulationFinished() {

    }


    private fun _createGraph(directed: Boolean, nodes: Set<Node>, edges: Set<Edge>): GraphModel {
        val nodeModels = nodes.map { it._toNodeModel() }.toSet()
        val edgeModels = edges.map {
            EdgeModel(
                id = it.id,
                u = it.from._toNodeModel(),
                v = it.to._toNodeModel(),
            )
        }.toSet()
        return GraphModel(
            isDirected = directed,
            nodes = nodeModels,
            edges = edgeModels,
            source = nodeModels.first()
        )
    }

    private fun Node._toNodeModel() = NodeModel(
        id = id,
    )


}