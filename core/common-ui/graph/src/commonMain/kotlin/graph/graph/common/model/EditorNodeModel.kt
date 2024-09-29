@file:Suppress("unused")

package graph.graph.common.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
/**
* - Only id,label,topLeft ,exactSizePx ,distance , need for drawing means for viewer
*/
internal data class EditorNodeModel(
    val id: String,
    val distance:String?=null,
    val label: String,
    val topLeft: Offset = Offset.Zero,
    val exactSizePx: Float,
    val color: Color = Color.Red,
) {

    val bottomRight = Offset(topLeft.x + exactSizePx, topLeft.y + exactSizePx)
    fun isInsideNode(position: Offset): Boolean {
        val minX = topLeft.x
        val minY = topLeft.y
        val maxX = minX + exactSizePx
        val maxY = minY + exactSizePx
        return position.x in minX..maxX && position.y in minY..maxY
    }

    // Custom toString implementation
    override fun toString(): String {
        return """${this.javaClass.simpleName}(id="$id", label="$label", topLeft=${formatOffset(topLeft)}, exactSizePx=${exactSizePx}f)"""
    }

    // Method to format Offset as Offset(xf, yf)
    private fun formatOffset(offset: Offset): String {
        return "Offset(${offset.x}f, ${offset.y}f)"
    }
}