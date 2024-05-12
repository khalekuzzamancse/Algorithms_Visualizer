package graph_editor.domain

import androidx.compose.ui.geometry.Size

data class VisualGraphModel(
    val nodes: Set<VisualNodeModel>,
    val edges: Set<VisualEdgeModel>
){
    fun calculateCanvasSize(): Size {
        var maxWidth = 0f
        var maxHeight = 0f


        // Check nodes to find the furthest extents in x and y directions
        nodes.forEach { node ->
            maxWidth = maxOf(maxWidth, node.topLeft.x)+ node.sizePx
            maxHeight = maxOf(maxHeight, node.topLeft.y)+ node.sizePx
        }

        // Check edges to ensure that control points are also within the canvas
        edges.forEach { edge ->
            listOf(edge.start, edge.end, edge.control).forEach { point ->
                maxWidth = maxOf(maxWidth, point.x)
                maxHeight = maxOf(maxHeight, point.y)
            }
        }

        return Size(width = maxWidth, height = maxHeight)
    }
}