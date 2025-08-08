@file:Suppress("functionName")

package core.ui.graph.viewer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import core.ui.graph.common.drawEdge
import core.ui.graph.common.drawNode
import core.ui.graph.common.model.EditorEdgeModel
import core.ui.graph.common.model.EditorNodeModel
import core.ui.graph.editor.ui.component.getMaxXY
import core.ui.graph.viewer.controller.GraphViewerController


@Composable
fun GraphViewer(
    modifier: Modifier,
    controller: GraphViewerController
) {

    Box(
        modifier = modifier
    ) {
        _GraphDrawer(
            nodes = controller.nodes.collectAsState().value,
            edges = controller.edges.collectAsState().value
        )
    }


}


@Composable
private fun _GraphDrawer(
    nodes: Set<EditorNodeModel>,
    edges: Set<EditorEdgeModel>
) {
    val canvasUtils = remember(nodes, edges) {
        getMaxXY(nodes = nodes.map { it.topLeft },
            starts = edges.map { it.start }, ends = edges.map { it.end },
            controls = edges.map { it.control })
    }
    val density = LocalDensity.current
    val nodeMaxSize= remember { 64.dp }
    val extra= remember { 20.dp }//possible that edge cost at end and after the node or control point
    //Dealing with topLeft so need to add the node size to get the canvas exact size
    val canvasHeight = remember(nodes, edges) { with(density) { canvasUtils.second.toDp()+nodeMaxSize +extra} }
    val canvasWidth = remember(nodes, edges) { with(density) { canvasUtils.first.toDp() +nodeMaxSize+extra} }

    val textMeasurer = rememberTextMeasurer() //
    val edgeWith = with(LocalDensity.current) { 1.dp.toPx() }
    //TODO:Find reason why graph not render if use size(height,weight) modifier before scroll modifier
    Canvas(
        modifier = Modifier
            //.horizontalScroll(rememberScrollState())
         //   .verticalScroll(rememberScrollState())
            .width(canvasWidth) //TODO:Careful can may crashes,directly use padding can cause crashes
            .height(canvasHeight) //TODO:Careful may causes crashes
    ) {
        edges.forEach {
            drawEdge(
                hideControllerPoints = true,
                edge = it, textMeasurer = textMeasurer,
                width = edgeWith
            )
        }
        nodes.forEach {
            drawNode(it, textMeasurer)
        }


    }
}

