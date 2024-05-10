package graphviewer.domain

import graphviewer.ui.viewer.GraphViewerEdge
import graphviewer.ui.viewer.GraphViewerNode
import kotlinx.coroutines.flow.StateFlow

interface GraphViewerController {
     val nodes: StateFlow<Set<GraphViewerNode>>
     val edges: StateFlow<Set<GraphViewerEdge>>
    fun highLightNode(id:String)
    fun highLightEdge(id: String)
    fun resetAllNodeColor()
    fun blinkNode(id:String)
}