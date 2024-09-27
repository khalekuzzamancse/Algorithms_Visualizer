@file:Suppress("functionName")
package graph

import graph.common.model.EditorEdgeModel
import graph.common.model.EditorNodeModel
import graph.viewer.GraphViewerController
import graph.viewer.GraphViewerControllerImpl

internal object GraphFactory {

    fun createGraphViewerController(
        nodes: Set<EditorNodeModel>,
        edges: Set<EditorEdgeModel>
    ): GraphViewerController = GraphViewerControllerImpl(nodes, edges)

}