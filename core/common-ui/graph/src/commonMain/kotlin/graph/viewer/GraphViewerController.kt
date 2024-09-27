package graph.viewer

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import graph.common.model.EditorEdgeModel
import graph.common.model.EditorNodeModel
import kotlinx.coroutines.flow.StateFlow

/**
 * - Interface does not allow internal fields but we don not want to expose unnecessary information to the consumer module
 * that is why using abstract class instead of interface
 */
abstract class GraphViewerController {
    internal abstract val nodes: StateFlow<Set<EditorNodeModel>>
    internal abstract val edges: StateFlow<Set<EditorEdgeModel>>
    internal abstract val canvasSize: Size
    abstract fun changeNodeColor(id: String, color: Color)
    abstract fun changeEdgeColor(id: String, color: Color)
    abstract fun updateDistance(id: String, distance: String)
    abstract fun resetAllNodeColor()
    abstract fun blinkNode(id: String)

}