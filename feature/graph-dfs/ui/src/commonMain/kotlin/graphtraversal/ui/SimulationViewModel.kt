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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SimulationViewModel {
    lateinit var graphController: GraphViewerController
    private lateinit var simulator: Simulator
    private lateinit var graph: GraphModel
    private lateinit var result: GraphResult
    private val autoPlayTimeInSeconds = MutableStateFlow<Int?>(null)

    init {
        CoroutineScope(Dispatchers.Default).launch {
            autoPlayTimeInSeconds.collect { time ->
                if (time != null) {
                    if (time > 0) {
                        while (true) {
                            delay(time * 1000L)
                            onNext()
                        }
                    }
                }

            }
        }
    }


    private val _isInputMode = MutableStateFlow(true)
    val isInputMode = _isInputMode.asStateFlow()
    private val _unvisitedNeighbours = MutableStateFlow<List<Node>>(emptyList())
    val unvisitedNeighbours = _unvisitedNeighbours.asStateFlow()
    private var callback: ((String) -> Unit)? = null

    fun onNeighbourSelected(id: String) {
        _unvisitedNeighbours.update { emptyList() }
        callback?.let { callback ->
            callback(id)
        }
    }


    fun onGraphCreated(result: GraphResult) {
        this.result = result

        graphController = result.controller
        graph = _createGraph(directed = result.directed, nodes = result.nodes, result.edges)
        simulator = DiContainer.createSimulator(graph)
        _isInputMode.update { false }

    }

    fun onReset() {
        simulator = DiContainer.createSimulator(graph)
        graphController.resetAllNodeColor()
        graphController.resetAllEdgeColor()
        graphController.stopBlinkAll()
        autoPlayTimeInSeconds.update { null }
    }

    fun onNext() {
        when (val state = simulator.next()) {
            is SimulationState.ColorChanged -> onColorChanged(state.nodes)
            is SimulationState.ProcessingEdge -> handleProcessingEdge(state.id)
            is SimulationState.NeighborSelection -> {
                val neighbourIds: List<String> = state.unvisitedNeighbors
                callback = state.callback
                val isNotAutoPlayMode = (autoPlayTimeInSeconds.value == null)
                if (isNotAutoPlayMode) {
                    _unvisitedNeighbours.update {
                        result.nodes.filter { node -> node.id in neighbourIds }
                    }
                }
                else{
                    callback?.let { it(neighbourIds.first()) }
                }

            }

            is SimulationState.ExecutionAt -> {
                handleControlAt(state.nodeId)
            }

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

    private fun handleControlAt(nodeId: String) {
        graphController.blinkNode(nodeId)
    }

    private fun handleProcessingEdge(id: String) {
        graphController.changeEdgeColor(id = id, color = Color.Green)
    }


    private fun handleSimulationFinished() {
        graphController.stopBlinkAll()
        autoPlayTimeInSeconds.update { null }
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

     fun onAutoPlayRequest(time: Int) {
        autoPlayTimeInSeconds.update { time }

    }


}