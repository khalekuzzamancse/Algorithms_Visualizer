package graph.graph.editor.factory

import graph.graph.editor.controller.GraphEditorController
import graph.graph.editor.model.GraphType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class InputControllerImpl(
    override var addNodeObserver: (label: String, nodeSizePx: Float) -> Unit,
    override var addEdgeRequestObserver: (cost: String?) -> Unit,
    override var graphTypeObserver: (type: GraphType) -> Unit
) : GraphEditorController.InputController {
    //TODO:Related to node and edge input---------------------------
    //TODO:Related to node and edge input---------------------------
    private val _takeGraphTypeInput = MutableStateFlow(false)
    private val _takeEdgeWeightInput = MutableStateFlow(false)
    private val _takeNodeLabelInput = MutableStateFlow(false)
    private val _graphTypeHasTaken=MutableStateFlow(false)

    private var _isDirected = MutableStateFlow(false)
    private val _graphType = MutableStateFlow<GraphType>(GraphType.Directed)


    override val graphType = _graphType.asStateFlow()




    override val takeGraphTypeInput = _takeGraphTypeInput.asStateFlow()
    override val takeEdgeWeightInput = _takeEdgeWeightInput.asStateFlow()
    override val takeNodeValueInput = _takeNodeLabelInput.asStateFlow()

    override fun enableInputMode()=_takeGraphTypeInput.update { true }
    override fun disableInputMode()=_takeGraphTypeInput.update { false }
    override val graphTypeHasTaken=_graphTypeHasTaken.asStateFlow()

    override fun onGraphTypeSelected(type: GraphType) {
        _graphType.update { type }
        when (type) {
            GraphType.Undirected -> _isDirected.update { false }
            GraphType.Directed -> _isDirected.update { true }
            GraphType.DirectedWeighted -> _isDirected.update { true }
            GraphType.Tree -> {}
            GraphType.UnDirectedWeighted -> _isDirected.update { false }

        }
        _takeGraphTypeInput.update { false }
        _graphTypeHasTaken.update { true }
        graphTypeObserver(type)
    }

    override fun onAddNodeRequest() = _takeNodeLabelInput.update { true }

    override fun onAddNodeRequest(label: String, nodeSizePx: Float) {
        addNodeObserver(label, nodeSizePx)
        _takeNodeLabelInput.update { false }
    }

    override fun onAddEdgeRequest() {

        if (_graphType.value == GraphType.UnDirectedWeighted || _graphType.value == GraphType.DirectedWeighted)
            _takeEdgeWeightInput.update { true }
        else
            _takeEdgeWeightInput.update { false }
    }

    override fun onAddNodeCancelRequest()=_takeNodeLabelInput.update { false }
    override fun onAddEdgeCancelRequest()=_takeEdgeWeightInput.update { false }

    override fun onEdgeConstInput(cost: String?) {
        addEdgeRequestObserver(cost)
        _takeEdgeWeightInput.update { false }
    }

    override fun isDirected() =
        (_graphType.value == GraphType.Directed || _graphType.value == GraphType.DirectedWeighted)


}