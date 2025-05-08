package core.ui.graph.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import core.ui.graph.common.model.EditorNodeModel



internal fun DrawScope.drawNode(
    node: EditorNodeModel,
    measurer: TextMeasurer,
) {

    val radius = node.exactSizePx / 2
    translate(
        left = node.topLeft.x,
        top = node.topLeft.y
    ) {
        drawCircle(
            color = node.color,
            radius = radius,
            center = Offset(radius, radius)
        )
        val distance=if(node.distance!=null) ":${node.distance}" else ""
        val measuredText = measurer.measure(node.label+distance)
        val textHeightPx = measuredText.size.height * 1f
        val textWidthPx = measuredText.size.width * 1f
        val offsetForCenterText = Offset(textWidthPx / 2, textHeightPx / 2)
        drawText(
            text = node.label+distance,
            topLeft = Offset(radius, radius) -offsetForCenterText,
            textMeasurer = measurer,
            style = TextStyle(
                color = if (node.color.luminance() > 0.5) {
                    Color.Black
                } else {
                    Color.White
                }
            )
        )

    }


}
