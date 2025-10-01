@file:Suppress("functionName")

package core.ui.graph.viewer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.graph.common.drawEdge
import core.ui.graph.common.drawNode
import core.ui.graph.common.model.EditorEdgeModel
import core.ui.graph.common.model.EditorNodeModel
import core.ui.graph.editor.ui.getMaxXY
import core.ui.graph.viewer.controller.GraphViewerController


/**
 * @param modifier , do not give padding instead use [contentPaddingTop] and [contentPaddingLeft] to avoid
 * cut-out effect during scrolling
 */
@Composable
fun GraphViewer(
    modifier: Modifier,
    controller: GraphViewerController,
    contentPaddingTop: Dp=4.dp,
    contentPaddingLeft: Dp=4.dp
) {


    Box(
        modifier = modifier
    ) {
        _GraphViewer2(
            nodes = controller.nodes.collectAsState().value,
            edges = controller.edges.collectAsState().value,
            contentPaddingTop=contentPaddingTop,
            contentPaddingLeft=contentPaddingLeft
        )
//        _GraphDrawer(
//            nodes = controller.nodes.collectAsState().value,
//            edges = controller.edges.collectAsState().value
//        )
    }


}

@Composable
private fun _GraphViewer2(
    nodes: Set<EditorNodeModel>,
    edges: Set<EditorEdgeModel>,
    contentPaddingTop: Dp,
    contentPaddingLeft: Dp
) {
    val canvasUtils = remember(nodes, edges) {
        getMaxXY(nodes = nodes.map { it.topLeft },
            starts = edges.map { it.start }, ends = edges.map { it.end },
            controls = edges.map { it.control })
    }
    val density = LocalDensity.current
    val topPaddingPx= with(density){contentPaddingTop.toPx()}
    val leftPaddingPx= with(density){contentPaddingLeft.toPx()}

    val nodeMaxSize = remember { 64.dp }
    val extra =
        remember { 20.dp }//possible that edge cost at end and after the node or control point
    //Dealing with topLeft so need to add the node size to get the canvas exact size
    val canvasHeight =
        remember(nodes, edges) { with(density) { canvasUtils.second.toDp() + nodeMaxSize + extra } }
    val canvasWidth =
        remember(nodes, edges) { with(density) { canvasUtils.first.toDp() + nodeMaxSize + extra } }
    val textMeasurer = rememberTextMeasurer() //
    val edgeWith = with(LocalDensity.current) { 1.dp.toPx() }
    Column(
        modifier = Modifier
            .height(canvasHeight)
            .width(canvasWidth)
            .horizontalScroll(rememberScrollState())
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.width(canvasWidth))//placeholder for scrollable
        Box(modifier = Modifier.width(canvasHeight))//placeholder for scrollable
        Box(modifier = Modifier
            .height(canvasHeight)
            .width(canvasWidth)
            .drawBehind {
                translate(
                    top = topPaddingPx,
                    left = leftPaddingPx
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
    val nodeMaxSize = remember { 64.dp }
    val extra =
        remember { 20.dp }//possible that edge cost at end and after the node or control point
    //Dealing with topLeft so need to add the node size to get the canvas exact size
    val canvasHeight =
        remember(nodes, edges) { with(density) { canvasUtils.second.toDp() + nodeMaxSize + extra } }
    val canvasWidth =
        remember(nodes, edges) { with(density) { canvasUtils.first.toDp() + nodeMaxSize + extra } }

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

