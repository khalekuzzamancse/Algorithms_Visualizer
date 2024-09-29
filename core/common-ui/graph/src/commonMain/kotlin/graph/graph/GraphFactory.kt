@file:Suppress("functionName")
package graph.graph

import graph.graph.common.model.EditorEdgeMode
import graph.graph.common.model.EditorNodeModel
import graph.graph.viewer.GraphViewerController
import graph.graph.viewer.GraphViewerControllerImpl

internal object GraphFactory {

    fun createGraphViewerController(
        nodes: Set<EditorNodeModel>,
        edges: Set<EditorEdgeMode>
    ): GraphViewerController = GraphViewerControllerImpl(nodes, edges)

}