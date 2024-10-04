@file:Suppress("functionName")

package graph.graph.viewer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import graph.graph.common.drawEdge
import graph.graph.common.drawNode
import graph.graph.common.model.EditorEdgeMode
import graph.graph.common.model.EditorNodeModel
import graph.graph.viewer.controller.GraphViewerController


@Composable
fun GraphViewer(
    modifier: Modifier,
    controller: GraphViewerController
) {
    val density = LocalDensity.current
    val canvasHeight =
        remember(controller) { with(density) { controller.canvasSize.height.toDp() } }
    val canvasWidth = remember(controller) { with(density) { controller.canvasSize.width.toDp() } }

    Box(
        modifier = modifier
            .width(canvasWidth) //TODO:Careful can causes crashes,directly use padding can cause crashes
            .height(canvasHeight) //TODO:Careful can causes crashes

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
    edges: Set<EditorEdgeMode>
) {


    val textMeasurer = rememberTextMeasurer() //
    val edgeWith = with(LocalDensity.current) { 1.dp.toPx() }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        edges.forEach {
            drawEdge(
                edge = it, textMeasurer = textMeasurer,
                width = edgeWith
            )
        }
        nodes.forEach {
            drawNode(it, textMeasurer)
        }


    }
}

