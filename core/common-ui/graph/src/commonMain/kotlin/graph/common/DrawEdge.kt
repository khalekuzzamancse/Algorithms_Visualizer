package graph.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import graph.editor.ui.component.edge.EdgePoint
import graph.common.model.EditorEdgeMode


internal fun DrawScope.drawEdge(
    edge: EditorEdgeMode,
    textMeasurer: TextMeasurer? = null,
    width:Float,
) {

    //drawEdge
    drawPath(path = edge.path, color = edge.pathColor, style = Stroke(width))
    //draw anchor point
    when (edge.selectedPoint) {
        EdgePoint.Start ->
            drawControlPoints(edge.selectedPointColor, edge.anchorPointRadius, edge.start)

        EdgePoint.End ->
            drawControlPoints(edge.selectedPointColor, edge.anchorPointRadius, edge.end)

        EdgePoint.Control ->
            drawControlPoints(edge.selectedPointColor, edge.anchorPointRadius, edge.pathCenter)

        else -> {}
    }
    //draw edge cost
    if (textMeasurer != null) {
        edge.cost?.let { text ->
            drawEdgeCost(text, textMeasurer, edge.slop, edge.pathCenter)
        }
    }
    if (edge.directed) {
        drawArrowHead(edge.pathColor, edge.arrowHeadPosition, edge.end,width=width)
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

private fun DrawScope.drawControlPoints(color: Color, radius: Dp, center: Offset) {
    drawCircle(color, radius = radius.toPx(), center = center)
}

private fun DrawScope.drawArrowHead(color: Color, arrowHeadPosition: Offset, end: Offset,width: Float) {
    if (arrowHeadPosition != Offset.Unspecified){
        rotate(30f, end) {
            drawLine(color, start = arrowHeadPosition, end = end, width)
        }
        rotate(-30f, end) {
            drawLine(color, arrowHeadPosition, end, width)
        }
    }


}

