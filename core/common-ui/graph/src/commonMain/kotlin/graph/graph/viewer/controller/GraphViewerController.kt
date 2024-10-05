package graph.graph.viewer.controller

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import graph.graph.common.model.EditorEdgeMode
import graph.graph.common.model.EditorNodeModel
import graph.graph.common.model.Node
import kotlinx.coroutines.flow.StateFlow

/**
 * - Interface does not allow internal fields but we don not want to expose unnecessary information to the consumer module
 * that is why using abstract class instead of interface
 */
abstract class GraphViewerController {
    internal abstract val nodes: StateFlow<Set<EditorNodeModel>>
    internal abstract val edges: StateFlow<Set<EditorEdgeMode>>
    internal abstract val canvasSize: Size
    abstract fun changeNodeColor(id: String, color: Color)
    abstract fun changeEdgeColor(id: String, color: Color)
    abstract fun updateDistance(id: String, distance: String)
    abstract fun getNodesById(ids:List<String>):List<Pair<String,String>>//id,label
    abstract fun resetAllNodeColor()
    abstract fun resetAllEdgeColor()
    abstract fun blinkNode(id: String)
    abstract fun stopBlinkAll()
    abstract fun reset()
    abstract fun filterEdgeByColor(color: Color)//helpful for MST such as prims algo to remove after finding MST

}