package bfs.ui.viewer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText


internal fun DrawScope.drawEdge(
    edge: VisualEdge,
    textMeasurer: TextMeasurer? = null,
) {
    val pathColor: Color = Color.Black
    //drawEdge
    drawPath(path = edge.path, color = pathColor, style = Stroke(3f))

    //draw edge cost
    if (textMeasurer != null) {
        edge.cost?.let { text ->
            drawEdgeCost(text, textMeasurer, edge.slop, edge.pathCenter)
        }
    }
    if (edge.isDirected) {
        drawArrowHead(pathColor, edge.arrowHeadPosition, edge.end)
    }

}

private fun DrawScope.drawEdgeCost(
    cost: String,
    textMeasurer: TextMeasurer,
    slop: Float,
    pathCenter: Offset
) {
    if(pathCenter!= Offset.Unspecified){
        val textHalfWidth = textMeasurer.measure(cost).size.width / 2
        rotate(slop, pathCenter) {
            drawText(
                text = cost,
                topLeft = pathCenter - Offset(textHalfWidth.toFloat(), 0f),
                textMeasurer = textMeasurer
            )
        }
    }

}


private fun DrawScope.drawArrowHead(color: Color, arrowHeadPosition: Offset, end: Offset) {
    if (arrowHeadPosition != Offset.Unspecified){
        rotate(30f, end) {
            drawLine(color, start = arrowHeadPosition, end = end, 3f)
        }
        rotate(-30f, end) {
            drawLine(color, arrowHeadPosition, end, 3f)
        }
    }


}

