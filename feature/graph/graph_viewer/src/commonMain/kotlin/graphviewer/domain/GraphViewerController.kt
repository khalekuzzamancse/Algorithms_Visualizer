package graphviewer.domain

import androidx.compose.ui.graphics.Color
import graphviewer.ui.viewer.GraphViewerEdge
import graphviewer.ui.viewer.GraphViewerNode
import kotlinx.coroutines.flow.StateFlow

/**
 * - Client module only should depend on this abstraction,to avoid loose couping and allowing Dependency injection
 */
interface GraphViewerController {
     val nodes: StateFlow<Set<GraphViewerNode>>
     val edges: StateFlow<Set<GraphViewerEdge>>
    fun changeNodeColor(id:String,color: Color)
    fun changeEdgeColor(id: String,color: Color)
     fun updateDistance(id: String, distance: String)
    fun resetAllNodeColor()
    fun blinkNode(id:String)

}