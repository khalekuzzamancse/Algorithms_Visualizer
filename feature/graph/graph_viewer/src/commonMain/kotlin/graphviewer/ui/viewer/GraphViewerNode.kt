package graphviewer.ui.viewer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color



data class GraphViewerNode(
    val id: String,
    val label: String,
    val topLeft: Offset = Offset.Zero,
    val exactSizePx: Float,
    val color: Color
)