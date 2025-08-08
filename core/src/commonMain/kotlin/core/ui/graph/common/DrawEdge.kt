package core.ui.graph.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import core.ui.graph.common.model.EditorEdgeModel
import core.ui.graph.editor.model.EdgePoint


internal fun DrawScope.drawEdge(
    hideControllerPoints: Boolean,
    edge: EditorEdgeModel,
    textMeasurer: TextMeasurer? = null,
    width:Float,
) {

    //drawEdge
    drawPath(path = edge.path, color = edge.pathColor, style = Stroke(width))

    //TODO: Edge are thin and hard to select show the controll point always to select it, but do it only in the view mode
    //but this causes some other bugs
//    if(!hideControllerPoints){
//        drawControlPoints(edge.selectedPointColor, edge.anchorPointRadius, edge.start)
//        drawControlPoints(edge.selectedPointColor, edge.anchorPointRadius, edge.end)
//        drawControlPoints(edge.selectedPointColor, edge.anchorPointRadius, edge.pathCenter)
//    }


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
                textMeasurer = textMeasurer,
                style = TextStyle(
                    //This color work well in both dark and light theme,since edge cost make sense to use same color as edge
                    color = EditorEdgeModel.pathDefaultColor
                )

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

