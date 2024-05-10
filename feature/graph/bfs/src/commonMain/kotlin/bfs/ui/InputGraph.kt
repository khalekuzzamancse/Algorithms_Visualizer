package bfs.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import graph_editor.domain.VisualEdgeModel
import graph_editor.domain.VisualNodeModel
import graph_editor.ui.GraphEditor
import graphviewer.di_container.GraphViewerFactory
import graphviewer.domain.VisualEdge
import graphviewer.domain.VisualNode
import graphviewer.ui.viewer.GraphViewer
import graphviewer.domain.GraphViewerController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InputGraph() {
    var controller: GraphViewerController? by  remember { mutableStateOf(null) }
    var isInputMode by remember { mutableStateOf(true) }
    if (isInputMode) {
        GraphEditor { result ->
            isInputMode = false
            val graph = result.visualGraph
            val nodes = graph.nodes.map { it._toVisualNode() }.toSet()
            val edges = graph.edges.map { it._toVisualEdge() }.toSet()
            //updating the graph viewer controller
            controller = GraphViewerFactory.createGraphViewerController(nodes, edges)
//            println("GraphViewerTest:: $graph")
//            println("GraphViewerTestControll:: ${controller!!.nodes.value}")
        }
    }
    //after inputting graph,pass to viewer to view the graph
    val scope= rememberCoroutineScope()
    controller?.let { viewerController ->
        Column {
            Button(onClick = {
            scope.launch {
              viewerController.nodes.value.forEach {
                  viewerController.highLightNode(it.id)
                  delay(2_000)
              }

            }
            }){
                Text("HighLight")
            }
            GraphViewer(viewerController)
        }

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