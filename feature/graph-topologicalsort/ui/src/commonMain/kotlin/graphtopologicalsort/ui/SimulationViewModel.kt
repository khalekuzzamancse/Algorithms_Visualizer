@file:Suppress("functionName", "className")
package graphtopologicalsort.ui
import core_ui.graph.common.model.GraphResult
import core_ui.graph.common.model.Node
import core_ui.graph.viewer.controller.GraphViewerController
import graphtopologicalsort.di.DiContainer
import graphtopologicalsort.domain.model.ColorModel
import graphtopologicalsort.domain.model.EdgeModel
import graphtopologicalsort.domain.model.GraphModel
import graphtopologicalsort.domain.model.NodeModel
import graphtopologicalsort.domain.model.SimulationState
import graphtopologicalsort.domain.service.Simulator
import graphtopologicalsort.presentationlogic.controller.Controller
import graphtopologicalsort.presentationlogic.factory.AutoPlayerImpl
import graphtopologicalsort.presentationlogic.factory.NeighborSelectorImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SimulationViewModel(
    private val color: StatusColor
) {
    private lateinit var simulator: Simulator
    private lateinit var result: GraphResult
    private lateinit var consumer: _StateConsumer
    private val _isInputMode = MutableStateFlow(true)
    val inputMode = _isInputMode.asStateFlow()
    lateinit var graphController: GraphViewerController
    val autoPlayer = AutoPlayerImpl(::onNext)
    val neighborSelector = NeighborSelectorImpl()
    private val _code = MutableStateFlow<String?>(null)
    val code = _code.asStateFlow()

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
        _code.update { state.code }
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
            edges = edgeModels
            // Removed the source node since it's not needed
        )
    }

    private fun Node._toNodeModel() = NodeModel(
        id = id,
    )

    private fun _createConsumer() = _StateConsumer(
        graphController = result.controller,
        autoPlayer = autoPlayer,
        statusColor = color
    )
}
private typealias UiNodeModel = graphtopologicalsort.presentationlogic.model.NodeModel

private class _StateConsumer(
    private val statusColor: StatusColor,
    private val autoPlayer: Controller.AutoPlayer,
    private val graphController: GraphViewerController
) {

    fun consume(state: SimulationState) {
        when (state) {
            is SimulationState.ColorChanged -> onColorChanged(state.nodeColors)
            is SimulationState.ProcessingEdge -> handleProcessingEdge(state.edgeId)
            is SimulationState.ExecutionAt -> handleExecutionAt(state.nodeId)
            is SimulationState.NodeProcessed -> handleNodeProcessed(state.nodeId)
            is SimulationState.StartingNode -> handleStartingNode(state.nodeId)
            is SimulationState.TraversingEdge -> handleTraversingEdge(state.id)
            is SimulationState.TopologicalOrder -> handleTopologicalOrder(state.order)
           is  SimulationState.Finished -> handleSimulationFinished()
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
                ColorModel.White -> statusColor.nodeUndiscovered
                ColorModel.Gray -> statusColor.nodeDiscovered
                ColorModel.Black -> statusColor.nodeProcessed
            }
            graphController.changeNodeColor(id = node.id, color = nodeColor)
        }
    }

    private fun handleExecutionAt(nodeId: String) {
        graphController.blinkNode(nodeId)
    }

    private fun handleNodeProcessed(nodeId: String) {
        graphController.changeNodeColor(id = nodeId, color = statusColor.nodeProcessed)
    }

    private fun handleStartingNode(nodeId: String) {
        graphController.blinkNode(nodeId)
    }

    private fun handleTraversingEdge(id: String) {
        graphController.changeEdgeColor(id = id, color = statusColor.edgeTraversing)

    }

    private fun handleProcessingEdge(edgeId: String) {
        graphController.changeEdgeColor(id = edgeId, color = statusColor.edgeProcessing)
    }

    private fun handleTopologicalOrder(order: List<String>) {
        // Display the topological order to the user
    }

    private fun handleSimulationFinished() {
        graphController.stopBlinkAll()
        autoPlayer.dismiss()
    }
}
