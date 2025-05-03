package core_ui.tree

import core_ui.graph.viewer.controller.GraphViewerController

/**
 * - This is just transfer the edited graph result to the client module
 * - Client module should not use it own purpose that is why constructor is internal
 */
data class TreeResult internal constructor(
    val controller: GraphViewerController,
    val root: TreeNode
)