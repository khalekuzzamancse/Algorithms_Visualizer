@file:Suppress("functionName", "className")

package graph._core.presentation

import androidx.compose.ui.graphics.Color
import core_ui.GlobalColors
import core_ui.GlobalMessenger
import core_ui.core.SimulationScreenState
import core_ui.graph.common.model.GraphResult
import core_ui.graph.common.model.Node
import core_ui.graph.viewer.controller.GraphViewerController
import graph._core.domain.ColorModel
import graph._core.domain.EdgeModel
import graph._core.domain.GraphModel
import graph._core.domain.NodeModel
import graph.bfs.domain.PseudocodeGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


internal typealias UiNodeModel = graph._core.presentation.NodeModel

interface RouteController {
    val inputMode: StateFlow<Boolean>
    val code: StateFlow<String?>
    val state: StateFlow<SimulationScreenState>
    var graphController: GraphViewerController
    val neighborSelector: NeighborSelectorImpl
    val autoPlayer: Controller.AutoPlayer
    fun onGraphCreated(result: GraphResult)
    fun onNext()
    fun onReset()
}

internal abstract class BaseRouteController : RouteController {
    protected val _inputMode = MutableStateFlow(true)
    override val inputMode = _inputMode.asStateFlow()
    private val _array = MutableStateFlow(listOf(10, 5, 4, 13, 8))
    protected val list = _array.asStateFlow()
    protected val _code = MutableStateFlow<String?>(null)
    override  lateinit var graphController: GraphViewerController
    override val code = _code.asStateFlow()
    private val _state = MutableStateFlow(SimulationScreenState(showPseudocode = true))
    override val state = _state.asStateFlow()
    override val autoPlayer = AutoPlayerImpl(::onNext)
    private lateinit var result: GraphResult
    override val neighborSelector=NeighborSelectorImpl()

    override fun onReset() {
        graphController.reset()
        graphController = result.controller
        autoPlayer.dismiss()
        _code.update { PseudocodeGenerator.rawCode }
    }

    protected open fun _onFinished() {
        autoPlayer.dismiss()
        GlobalMessenger.updateAsEndedMessage()
    }

    override fun onGraphCreated(result: GraphResult) {
        this.result = result
        graphController = result.controller
        _inputMode.update { false }
    }

    protected fun _createGraph(): GraphModel {
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

    private fun Node._toNodeModel() = NodeModel(id = id)
    protected fun onColorChanged(pairs: Set<Pair<NodeModel, ColorModel>>) {
        pairs.forEach { (node, color) ->
            val nodeColor = when (color) {
                ColorModel.White -> GlobalColors.GraphColor.UNDISCOVERED
                ColorModel.Gray -> GlobalColors.GraphColor.DISCOVERED
                ColorModel.Black -> GlobalColors.GraphColor.PROCESSED
            }
            graphController.changeNodeColor(id = node.id, color = nodeColor)

        }

    }
    protected fun handleControlAt(nodeId: String) {
        graphController.blinkNode(nodeId)
    }

    protected fun handleProcessingEdge(id: String) {
        graphController.changeEdgeColor(id = id, color = Color.Green)
    }


    protected fun handleSimulationFinished() {
        graphController.stopBlinkAll()
        autoPlayer.dismiss()
    }

}

