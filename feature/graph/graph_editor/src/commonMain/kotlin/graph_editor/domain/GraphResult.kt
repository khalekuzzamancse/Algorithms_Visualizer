package graph_editor.domain

import androidx.compose.ui.geometry.Size

/**
 * - This is just transfer the edited graph result to the client module
 * - Client module should not use it own purpose that is why constructor is internal
 */
data class GraphResult internal  constructor(
    val undirected:Boolean,
    val visualGraph: VisualGraphModel,
    val graph: Graph,
    val canvasSizePx:Size=visualGraph.calculateCanvasSize()
)