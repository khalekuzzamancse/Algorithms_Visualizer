@file:Suppress("unused")

package core.ui.graph.common.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

/**
 * - Only id,label,topLeft ,exactSizePx ,distance , need for drawing means for viewer
 */
data class EditorNodeModel(
    val id: String,
    val distance: String? = null,
    val label: String,
    val topLeft: Offset,
    val exactSizePx: Float,
    val color: Color = defaultColor
) {
    companion object {
        /** limeGreen ,work both dark and light theme,can be used when reset such as deselect */
        val defaultColor = Color(0xFFC0CA33)
    }

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
        return """${this.javaClass.simpleName}(id="$id", label="$label", topLeft=${
            formatOffset(
                topLeft
            )
        }, exactSizePx=${exactSizePx}f)"""
    }

    // Method to format Offset as Offset(xf, yf)
    private fun formatOffset(offset: Offset): String {
        return "Offset(${offset.x}f, ${offset.y}f)"
    }
}