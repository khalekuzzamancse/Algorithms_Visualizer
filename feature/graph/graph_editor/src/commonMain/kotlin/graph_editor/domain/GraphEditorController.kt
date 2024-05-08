package graph_editor.domain
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.flow.StateFlow

interface GraphEditorController {
    val edges: StateFlow<List<VisualEdge>>
    val nodes: StateFlow<Set<VisualNode>>
    val selectedNode: StateFlow<VisualNode?>
    var selectedEdge: StateFlow<VisualEdge?>

    fun onRemovalRequest()
    fun onSave()
    fun onDirectionChanged(hasDirection: Boolean)
    fun onAddNodeRequest(label: String, nodeSizePx: Float)
    fun onEdgeConstInput(cost: String)
    fun onTap(tappedPosition: Offset)
    fun onDragStart(startPosition: Offset)
    fun onDrag(dragAmount: Offset)
    fun dragEnd()
    fun getGraph():Graph
}