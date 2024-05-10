package graphviewer.di_container

import graphviewer.domain.VisualEdge
import graphviewer.domain.VisualNode
import graphviewer.domain.GraphViewerController
import graphviewer.infrastructure.GraphViewerControllerImpl

object GraphViewerFactory {

    fun createGraphViewerController(nodes: Set<VisualNode>, edges: Set<VisualEdge>): GraphViewerController {
        return GraphViewerControllerImpl(nodes, edges)
    }

}