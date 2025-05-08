package core.ui.graph.common.model

import core.ui.graph.viewer.controller.GraphViewerController


/**
 * - This is just transfer the edited graph result to the client module
 * - Client module should not use it own purpose that is why constructor is internal
 */
data class GraphResult internal constructor(
    val directed: Boolean,
    val controller: GraphViewerController,
    val nodes: Set<Node>,
    val edges: Set<Edge>
)