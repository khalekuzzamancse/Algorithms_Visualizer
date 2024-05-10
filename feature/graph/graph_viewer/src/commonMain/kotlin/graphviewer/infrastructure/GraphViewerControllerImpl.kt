package graphviewer.infrastructure

import androidx.compose.ui.graphics.Color
import graphviewer.domain.GraphViewerController
import graphviewer.domain.VisualEdge
import graphviewer.domain.VisualNode
import graphviewer.ui.viewer.GraphViewerEdge
import graphviewer.ui.viewer.GraphViewerNode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Suppress("FunctionName")
class GraphViewerControllerImpl(
    nodes: Set<VisualNode>,
    edges: Set<VisualEdge>
) : GraphViewerController {
    private val highLightedColor = Color.Green
    private val _nodes = MutableStateFlow(nodes.map { it._toGraphViewerNode() }.toSet())
    private val _edges = MutableStateFlow(edges.map { it._toGraphViewerEdge() }.toSet())
    override val nodes: StateFlow<Set<GraphViewerNode>> = _nodes.asStateFlow()
    override val edges: StateFlow<Set<GraphViewerEdge>> = _edges.asStateFlow()

    override fun highLightNode(id: String) {
        _nodes.update { nodes ->
            nodes.map { node ->
                if (node.id == id)
                {
                    println("Contorller:Idfound:${node.label}")
                    node.copy(color = highLightedColor)
                }
                else node
            }.toSet()
        }
    }

    override fun highLightEdge(id: String) {
        _edges.update { edges ->
            edges.map { edge ->
                if (edge.id == id)
                    edge.copy(color = highLightedColor)
                else edge
            }.toSet()
        }
    }

    override fun resetAllNodeColor() {
        _nodes.update { nodes ->
            nodes.map { node ->
                node.copy(color = Color.Unspecified)
            }.toSet()
        }
    }

    override fun blinkNode(id: String) {
        TODO("Not yet implemented")
    }

    //TODO: Helper method section -- TODO: Helper method section -- TODO: Helper method section
    //TODO: Helper method section -- TODO: Helper method section -- TODO: Helper method section

    private fun VisualNode._toGraphViewerNode() = GraphViewerNode(
        id = id,
        label = label,
        topLeft = topLeft,
        exactSizePx = exactSizePx,
        color = Color.Red
    )

    private fun VisualEdge._toGraphViewerEdge() = GraphViewerEdge(
        id = id,
        start = start,
        end = end,
        control = control,
        cost = cost,
        isDirected = isDirected,
        color = Color.Red
    )
}