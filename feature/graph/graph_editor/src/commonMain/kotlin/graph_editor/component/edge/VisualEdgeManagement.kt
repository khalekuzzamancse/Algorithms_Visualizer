package graph_editor.ui.components.edge

import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.flow.StateFlow


interface GraphEditorVisualEdgeManger {
    val edges: StateFlow<List<GraphEditorVisualEdgeImp>>
    fun onTap(tappedPosition: Offset)
    fun dragOngoing(dragAmount: Offset, position: Offset)
    fun dragEnded()
    fun setEdge(edges: List<GraphEditorVisualEdgeImp>)

}

