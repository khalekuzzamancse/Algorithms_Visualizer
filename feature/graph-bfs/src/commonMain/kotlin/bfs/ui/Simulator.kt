package bfs.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import bfs.di_containter.BFSFactory
import bfs.domain.AlgorithmicEdge
import bfs.domain.AlgorithmicGraph
import bfs.domain.AlgorithmicNode
import graph_editor.model.Edge
import graph_editor.model.Graph
import graph_editor.model.Node
import graph_editor.model.ViewerEdgeModel
import graph_editor.model.ViewerNodeModel
import graph_editor.ui.GraphEditor
import graphviewer.di_container.GraphViewerFactory
import graphviewer.domain.GraphViewerController
import graphviewer.domain.GraphViewerEdgeModel
import graphviewer.domain.GraphViewerNodeModel


@Composable
internal fun GraphInput(
    onGraphInput: (GraphViewerController) -> Unit = {},
    canvasSize: (Size) -> Unit = {},
) {

    var isInputMode by remember { mutableStateOf(true) }

    if (isInputMode) {
        GraphEditor { result ->
            isInputMode = false
            val graph = result.visualGraph


            val nodes = graph.nodes.map { it._toVisualNode() }.toSet()
            val edges = graph.edges.map { it._toVisualEdge() }.toSet()
            //updating the graph viewer controller
            val controller = GraphViewerFactory.createGraphViewerController(nodes, edges)
            BFSFactory.setGraph(result.graph._toAlgorithmicGraph())
            canvasSize(graph.calculateCanvasSize())
            onGraphInput(controller)

        }
    }
    //after inputting graph,pass to viewer to view the graph


}

@Suppress("FunctionName")
private fun ViewerNodeModel._toVisualNode() = GraphViewerNodeModel(
    id = id,
    label = label,
    topLeft = topLeft,
    exactSizePx = sizePx
)

@Suppress("FunctionName")
private fun ViewerEdgeModel._toVisualEdge() = GraphViewerEdgeModel(
    id = id,
    start = start,
    end = end,
    control = control,
    cost = cost,
    isDirected = false
)

//TODO
@Suppress("FunctionName")
private fun Graph._toAlgorithmicGraph(): AlgorithmicGraph {
    return AlgorithmicGraph(
        undirected = undirected,
        nodes = nodes.map { it._toAlgorithmicNode() },
        edges = edges.map { it._toAlgorithmicEdge() }
    )
}

@Suppress("FunctionName")
private fun Node._toAlgorithmicNode() = AlgorithmicNode(
    id = id, label = label
)

@Suppress("FunctionName")
private fun Edge._toAlgorithmicEdge() = AlgorithmicEdge(
    from = from._toAlgorithmicNode(),
    to = to._toAlgorithmicNode(),
    cost = cost
)


