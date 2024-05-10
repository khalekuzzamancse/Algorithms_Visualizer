package graphviewer.di_container

import graphviewer.domain.GraphViewerEdgeModel
import graphviewer.domain.GraphViewerNodeModel
import graphviewer.domain.GraphViewerController
import graphviewer.infrastructure.GraphViewerControllerImpl

object GraphViewerFactory {

    fun createGraphViewerController(nodes: Set<GraphViewerNodeModel>, edges: Set<GraphViewerEdgeModel>): GraphViewerController {
        return GraphViewerControllerImpl(nodes, edges)
    }

}