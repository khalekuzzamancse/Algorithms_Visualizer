package graphviewer.domain

import androidx.compose.ui.geometry.Offset


/**
 * - This is for take the input from the client module only
 * - To avoid tight couping do not use it to other  purpose,instead crate separate class using these value
 */
data class GraphViewerNodeModel(
    val id: String,
    val label: String,
    val topLeft: Offset = Offset.Zero,
    val exactSizePx: Float,
)