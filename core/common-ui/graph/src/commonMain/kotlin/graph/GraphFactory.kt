@file:Suppress("functionName")
package graph

import graph.common.model.EditorEdgeMode
import graph.common.model.EditorNodeModel
import graph.viewer.GraphViewerController
import graph.viewer.GraphViewerControllerImpl

internal object GraphFactory {

    fun createGraphViewerController(
        nodes: Set<EditorNodeModel>,
        edges: Set<EditorEdgeMode>
    ): GraphViewerController = GraphViewerControllerImpl(nodes, edges)

}