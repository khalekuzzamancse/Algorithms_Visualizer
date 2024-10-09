@file:Suppress("functionName")

package graph.graph.editor.factory

import graph.graph.editor.controller.GraphEditorController
import graph.graph.editor.model.GraphType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class InputControllerImpl(
    override var drawNodeObserver: (label: String, nodeSizePx: Float) -> Unit,
    override var addEdgeRequestObserver: (cost: String?) -> Unit,
    override var graphTypeObserver: (type: GraphType) -> Unit
) : GraphEditorController.InputController {


    private val _showGraphTypeSelectionUI = MutableStateFlow(false)
    private val _showEdgeCostInputUI = MutableStateFlow(false)
    private val _showNodeInputUI = MutableStateFlow(false)
    private val _graphTypeSelected = MutableStateFlow(false)
    private var _isDirected = MutableStateFlow(false)
    private val _graphType = MutableStateFlow<GraphType>(GraphType.Directed)


    //Overridden properties
    override val graphType = _graphType.asStateFlow()
    override val showGraphTypeSelectionUi = _showGraphTypeSelectionUI.asStateFlow()
    override val showEdgeCostInputUi = _showEdgeCostInputUI.asStateFlow()
    override val showNodeInputUi = _showNodeInputUI.asStateFlow()
    override val graphTypeSelected = _graphTypeSelected.asStateFlow()

    //Overridden methods
    override fun onGraphTypeSelectionRequest() = _showGraphTypeSelectionUI.update { true }
    override fun onGraphTypeSelected(type: GraphType) {
        _closeGraphTypeInput()
        _updateGraphType(type)
        _updateDirection(type)
        _notifyGraphTypeObservers(type)
    }

    override fun onNodeDrawRequest() = _showNodeInputUI.update { true }
    override fun onDrawNodeRequest(label: String, nodeSizePx: Float) {
        _notifyDrawNodeObserver(label, nodeSizePx)
        _closeNodeInputUi()
    }

    override fun onEdgeDrawRequest() {
        if (isUnWeightedGraph())
            _onWeightedEdgeDrawRequest()
        else
            _onUnWeightedEdgeDrawRequest()

    }


    override fun onAddNodeCancelRequest() = _closeNodeInputUi()
    override fun onAddEdgeCancelRequest() = _closeCostInputUi()

    override fun onEdgeConstInput(cost: String?) {
        _notifyEdgeDrawObserver(cost)
        _closeCostInputUi()
    }


    override fun isDirected() =
        (_graphType.value == GraphType.Directed || _graphType.value == GraphType.DirectedWeighted)


    //TODO: Helper methods Section -----------------  Helper methods Section
    //TODO: Helper methods Section -----------------  Helper methods Section
    //TODO: Helper methods Section -----------------  Helper methods Section
    //TODO: Helper methods Section -----------------  Helper methods Section

    private fun GraphType._hasDirection() = when (this) {
        GraphType.Undirected -> false
        GraphType.Directed -> true
        GraphType.DirectedWeighted -> true
        GraphType.Tree -> false
        GraphType.UnDirectedWeighted -> false
    }

    private fun _onWeightedEdgeDrawRequest() = _openEdgeCostInputUi()
    private fun _onUnWeightedEdgeDrawRequest() {
        addEdgeRequestObserver(null)
        _closeCostInputUi()
    }


    private fun isUnWeightedGraph() =
        (graphType.value == GraphType.UnDirectedWeighted || _graphType.value == GraphType.DirectedWeighted)

    //Related to Graph type input
    private fun _closeGraphTypeInput() {
        _showGraphTypeSelectionUI.update { false }
        _graphTypeSelected.update { true }
    }

    private fun _updateGraphType(type: GraphType) = _graphType.update { type }
    private fun _updateDirection(type: GraphType) = _isDirected.update { type._hasDirection() }
    private fun _notifyGraphTypeObservers(type: GraphType) = graphTypeObserver(type)


    //Related to Node input
    private fun _notifyDrawNodeObserver(label: String, nodeSizePx: Float) =
        drawNodeObserver(label, nodeSizePx)

    private fun _closeNodeInputUi() = _showNodeInputUI.update { false }


    //Related to
    private fun _closeCostInputUi() = _showEdgeCostInputUI.update { false }
    private fun _notifyEdgeDrawObserver(cost: String?) = addEdgeRequestObserver(cost)
    private fun _openEdgeCostInputUi() = _showEdgeCostInputUI.update { true }
}