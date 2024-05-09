package bfs.ui.viewer

import androidx.compose.ui.geometry.Offset


/**
 * - Color can be changed,while highlight such as traveling
 */
internal data class VisualNode(
    val id: String,
    val label: String,
    val topLeft: Offset = Offset.Zero,
    val exactSizePx: Float,
)