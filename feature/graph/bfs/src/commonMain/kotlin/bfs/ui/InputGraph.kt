package bfs.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import bfs.ui.viewer.GraphViewer
import bfs.ui.viewer.VisualEdge
import bfs.ui.viewer.VisualNode
import graph_editor.domain.VisualEdgeModel
import graph_editor.domain.VisualNodeModel
import graph_editor.ui.GraphEditor

@Composable
fun InputGraph(

) {
    var nodes by remember { mutableStateOf<Set<VisualNode>>(emptySet()) }
    var edges by remember { mutableStateOf<Set<VisualEdge>>(emptySet()) }
    val isInputMode = nodes.isEmpty()
    if (isInputMode) {
        GraphEditor { result ->
            val graph = result.visualGraph
            nodes = graph.nodes.map { it._toVisualNode() }.toSet()
            edges = graph.edges.map { it._toVisualEdge() }.toSet()
            println("InputGraphLog:: $graph")
        }
    }
    //after inputting graph,pass to viewer to view the graph
    if (nodes.isNotEmpty()) {
        GraphViewer(nodes, edges)
    }


}

@Suppress("FunctionName")
private fun VisualNodeModel._toVisualNode() = VisualNode(
    id = id,
    label = label,
    topLeft = topLeft,
    exactSizePx = sizePx
)
@Suppress("FunctionName")
private fun VisualEdgeModel._toVisualEdge() = VisualEdge(
    id = id,
    start = start,
    end = end,
    control = control,
    cost = cost,
    isDirected = false
)