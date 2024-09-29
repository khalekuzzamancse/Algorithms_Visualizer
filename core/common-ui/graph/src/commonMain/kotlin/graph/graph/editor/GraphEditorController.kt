package graph.graph.editor
import androidx.compose.ui.geometry.Offset
import graph.graph.common.model.EditorEdgeMode
import graph.graph.common.model.EditorNodeModel
import graph.graph.common.model.GraphResult
import kotlinx.coroutines.flow.StateFlow

internal interface GraphEditorController {
    val edges: StateFlow<List<EditorEdgeMode>>
    val nodes: StateFlow<Set<EditorNodeModel>>
    val selectedNode: StateFlow<EditorNodeModel?>
    var selectedEdge: StateFlow<EditorEdgeMode?>

    fun onRemovalRequest()
    fun onDone(): GraphResult
    fun onDirectionChanged(directed: Boolean)
    fun onAddNodeRequest(label: String, nodeSizePx: Float)
    fun onEdgeConstInput(cost: String?)
    fun onTap(tappedPosition: Offset)
    fun onDragStart(startPosition: Offset)
    fun onDrag(dragAmount: Offset)
    fun dragEnd()
}