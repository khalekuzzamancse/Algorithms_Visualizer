package graph_editor.ui.component

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color


/**
 *
 */
data class VisualNode(
    val id: String,
    val label: String,
    val topLeft: Offset = Offset.Zero,
    val exactSizePx: Float,
    val color: Color = Color.Red,
){
    override fun toString(): String {
        return "VisualNode(id=$id, label=$label, topLeft=$topLeft, exactSize=$exactSizePx)"
    }
    val bottomRight=Offset(topLeft.x+exactSizePx,topLeft.y+exactSizePx)
     fun isInsideNode(position: Offset): Boolean {
        val minX = topLeft.x
        val minY = topLeft.y
        val maxX = minX + exactSizePx
        val maxY = minY + exactSizePx
        return position.x in minX..maxX && position.y in minY..maxY
    }
}