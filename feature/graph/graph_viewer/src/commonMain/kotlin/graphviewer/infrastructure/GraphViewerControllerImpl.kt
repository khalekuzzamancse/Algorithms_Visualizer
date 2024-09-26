package graphviewer.infrastructure

import androidx.compose.ui.graphics.Color
import graphviewer.domain.GraphViewerController
import graphviewer.domain.GraphViewerEdgeModel
import graphviewer.domain.GraphViewerNodeModel
import graphviewer.ui.viewer.GraphViewerEdge
import graphviewer.ui.viewer.GraphViewerNode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * - Client should not create direct instance of it to avoid coupling
 * - Instead client should use DI container to get the instance with is abstraction
 */
@Suppress("FunctionName")
class GraphViewerControllerImpl internal constructor(
    nodes: Set<GraphViewerNodeModel>,
    edges: Set<GraphViewerEdgeModel>
) : GraphViewerController {
    private val _nodes = MutableStateFlow(nodes.map { it._toGraphViewerNode() }.toSet())
    private val _edges = MutableStateFlow(edges.map { it._toGraphViewerEdge() }.toSet())
    override val nodes: StateFlow<Set<GraphViewerNode>> = _nodes.asStateFlow()
    override val edges: StateFlow<Set<GraphViewerEdge>> = _edges.asStateFlow()


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
                if (edge.id == id){
                    edge.copy(color =color)
                }

                else edge
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


    //TODO: Helper method section -- TODO: Helper method section -- TODO: Helper method section
    //TODO: Helper method section -- TODO: Helper method section -- TODO: Helper method section

    private fun GraphViewerNodeModel._toGraphViewerNode() = GraphViewerNode(
        id = id,
        label = label,
        topLeft = topLeft,
        exactSizePx = exactSizePx,
        color = Color.Red
    )

    private fun GraphViewerEdgeModel._toGraphViewerEdge() = GraphViewerEdge(
        id = id,
        start = start,
        end = end,
        control = control,
        cost = cost,
        isDirected = isDirected,
        color = Color.Black
    )
}