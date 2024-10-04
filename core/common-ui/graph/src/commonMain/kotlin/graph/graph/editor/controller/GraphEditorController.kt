package graph.graph.editor.controller

import androidx.compose.ui.geometry.Offset
import graph.graph.common.model.EditorEdgeMode
import graph.graph.common.model.EditorNodeModel
import graph.graph.common.model.GraphResult
import graph.graph.editor.model.GraphType
import kotlinx.coroutines.flow.StateFlow

internal interface GraphEditorController {
    val inputController: InputController
    /**Forcing to implement a separate controller to reduce responsible and code length*/
    interface InputController {
        val takeGraphTypeInput: StateFlow<Boolean>
        val takeEdgeWeightInput: StateFlow<Boolean>
        val takeNodeValueInput: StateFlow<Boolean>
        val graphType:StateFlow<GraphType>
        fun isDirected():Boolean
        fun onAddNodeRequest()
        fun onAddEdgeRequest()
        fun onAddNodeCancelRequest()
        fun onAddEdgeCancelRequest()


        var graphTypeObserver:(type: GraphType)->Unit//one time event
        fun onGraphTypeSelected(type: GraphType){
            graphTypeObserver(type)
            //TODO:Hide the graph type input form or dialog or ...
        }

        var addNodeObserver: (label: String, nodeSizePx: Float) -> Unit
        fun onAddNodeRequest(label: String, nodeSizePx: Float){
            addNodeObserver(label,nodeSizePx)
            //TODO:Hide the node input form or dialog or ...
        }
        var addEdgeRequestObserver:(cost: String?)->Unit
        fun onEdgeConstInput(cost: String?){
            addEdgeRequestObserver(cost)
            //TODO:Hide the edge cost input form or dialog or ...
        }


    }

    //
    val edges: StateFlow<List<EditorEdgeMode>>
    val nodes: StateFlow<Set<EditorNodeModel>>
    val selectedNode: StateFlow<EditorNodeModel?>
    var selectedEdge: StateFlow<EditorEdgeMode?>
    fun onRemovalRequest()
    fun onGraphInputCompleted(): GraphResult
    fun onTap(tappedPosition: Offset)
    fun onDragStart(startPosition: Offset)
    fun onDrag(dragAmount: Offset)
    fun dragEnd()
}