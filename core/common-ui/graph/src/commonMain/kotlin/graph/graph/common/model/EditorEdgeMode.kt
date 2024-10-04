package graph.graph.common.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import graph.graph.editor.model.EdgePoint
import kotlin.math.atan2

/**
 * - Only id,start,end,control,directed need for drawing means for viewer
 */
data class EditorEdgeMode internal constructor(
    val id: String,
    val start: Offset,
    val end: Offset,
    val control: Offset,
    val cost: String? = null,
    val directed: Boolean = false,
    val pathColor: Color = Color.Black,
    val selectedPointColor: Color = Color.Red,
    val showSelectedPoint: Boolean = false,
    val anchorPointRadius: Dp = 4.dp,
    internal val selectedPoint: EdgePoint = EdgePoint.None,
    val minTouchTargetPx: Float = 30f
) {
    companion object {
        private val pathMeasurer = PathMeasure()
    }

    val arrowHeadPosition: Offset
        get() {
            val arrowLength = 10f
            pathMeasurer.setPath(path, false)
            return if (pathMeasurer.length >= arrowLength)
                pathMeasurer.getPosition(pathMeasurer.length - arrowLength)
            else
                Offset.Unspecified
        }

    val path: Path
        get() = Path().apply {
            moveTo(start.x, start.y)
            quadraticBezierTo(control.x, control.y, end.x, end.y)
        }
    val slop: Float
        get() {
            val (x1, y1) = end
            val (x2, y2) = start
            val slop = atan2(y1 - y2, x1 - x2)
            return Math.toDegrees(slop.toDouble()).toFloat()
        }

    val pathCenter: Offset
        get() {
            pathMeasurer.setPath(path, false)
            val pathLength = pathMeasurer.length
            return if (pathMeasurer.length < 20)
                Offset.Unspecified
            else pathMeasurer.getPosition(pathLength / 2)//path center
        }

    // Custom toString implementation
    override fun toString(): String {
        return """${this.javaClass.simpleName}(id="$id", start=${formatOffset(start)}, end=${
            formatOffset(
                end
            )
        }, control=${formatOffset(control)}, cost=${cost?.let { "\"$it\"" } ?: "null"})"""
    }

    // Method to format Offset as Offset(xf, yf)
    private fun formatOffset(offset: Offset): String {
        return "Offset(${offset.x}f, ${offset.y}f)"
    }
}