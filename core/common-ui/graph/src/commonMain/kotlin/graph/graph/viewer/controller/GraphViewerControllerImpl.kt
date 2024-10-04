package graph.graph.viewer.controller

import androidx.compose.ui.graphics.Color
import graph.graph.common.model.EditorEdgeMode
import graph.graph.common.model.EditorNodeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * - Client should not create direct instance of it to avoid coupling
 * - Instead client should use DI container to get the instance with is abstraction
 */
class GraphViewerControllerImpl internal constructor(
    nodes: Set<EditorNodeModel>,
    edges: Set<EditorEdgeMode>
) : GraphViewerController() {

    private val canvasUtils = CanvasUtils(nodes, edges).trimExtraSpace().calculateCanvasSize()
    private val _nodes = MutableStateFlow(canvasUtils.nodes)
    private val _edges = MutableStateFlow(canvasUtils.edges)

    override val canvasSize = canvasUtils.canvasSize
    override val nodes: StateFlow<Set<EditorNodeModel>> = _nodes.asStateFlow()
    override val edges: StateFlow<Set<EditorEdgeMode>> = _edges.asStateFlow()


    override fun updateDistance(id: String, distance: String) {
        _nodes.update { nodes ->
            nodes.map { node ->
                if (node.id == id) {
                    node.copy(distance = distance)
                } else {
                    node
                }
            }.toSet()
        }
    }

    override fun changeNodeColor(id: String, color: Color) {
        _nodes.update { nodes ->
            nodes.map { node ->
                if (node.id == id)
                    node.copy(color = color)
                else node
            }.toSet()
        }
    }

    override fun changeEdgeColor(id: String, color: Color) {
        _edges.update { edges ->
            edges.map { edge ->
                if (edge.id == id) {
                    edge.copy(pathColor = color)
                } else edge
            }.toSet()
        }
    }

    override fun resetAllNodeColor() {
        _nodes.update { nodes ->
            nodes.map { node ->
                node.copy(color = Color.Red)
            }.toSet()
        }
    }

    override fun blinkNode(id: String) {
        //Stop if any node is blinking
        //then blink that

    }

    override fun filterEdgeByColor(color: Color) {
        _edges.update { edges ->
            edges.filter { it.pathColor == color }.toSet()
        }
    }


}