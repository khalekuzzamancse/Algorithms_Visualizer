package bfs.ui.viewer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer

@Composable
internal fun GraphViewer(
    nodes: Set<VisualNode>,
    edges: Set<VisualEdge>

) {
    val textMeasurer = rememberTextMeasurer() //

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        edges.forEach {
            _drawEdge(it, textMeasurer)
        }
        nodes.forEach {
            _drawNode(it, textMeasurer)
        }


    }
}

/*
TODO: Draw Node function
 */


private fun DrawScope._drawNode(
    node: VisualNode,
    measurer: TextMeasurer,
) {
    val color = Color.Red

    val radius = node.exactSizePx / 2
    translate(
        left = node.topLeft.x,
        top = node.topLeft.y
    ) {
        drawCircle(
            color = color,
            radius = radius,
            center = Offset(radius, radius)
        )
        val measuredText = measurer.measure(node.label)
        val textHeightPx = measuredText.size.height * 1f
        val textWidthPx = measuredText.size.width * 1f
        val offsetForCenterText = Offset(textWidthPx / 2, textHeightPx / 2)
        drawText(
            text = node.label,
            topLeft = Offset(radius, radius) - offsetForCenterText,
            textMeasurer = measurer,
            style = TextStyle(
                color = if (color.luminance() > 0.5) {
                    Color.Black
                } else {
                    Color.White
                }
            )
        )

    }


}


/*
TODO: Draw edge function
 */

private fun DrawScope._drawEdge(
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
    if (pathCenter != Offset.Unspecified) {
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
    if (arrowHeadPosition != Offset.Unspecified) {
        rotate(30f, end) {
            drawLine(color, start = arrowHeadPosition, end = end, 3f)
        }
        rotate(-30f, end) {
            drawLine(color, arrowHeadPosition, end, 3f)
        }
    }


}


