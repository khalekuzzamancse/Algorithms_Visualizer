package graph.graph.editor.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Moving
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import graph.graph.GraphFactory
import graph.graph.editor.controller.GraphEditorController
import graph.graph.editor.factory.GraphEditorControllerImpl
import graph.graph.editor.ui.component.InputDialog
import graph.graph.editor.ui.component.GraphTypeInput
import graph.graph.common.drawEdge
import graph.graph.common.drawNode
import graph.graph.common.model.EditorEdgeMode
import graph.graph.common.model.EditorNodeModel
import graph.graph.common.model.GraphResult

/**
 * @param hasDistance is the graph node has distance such as Node for Dijkstra algorithm. if the graph node has distance
 * then we should make the nodes bigger enough to fit the label+distance to it that is why this parameter is necessary.
 * try to keep the distance within 2-3 digits
 *
 */

@Composable
fun GraphEditor(
    density: Float = 1f,
    hasDistance: Boolean = false,
    initialGraph: Pair<List<EditorNodeModel>, List<EditorEdgeMode>> = Pair(
        emptyList(),
        emptyList()
    ),
    onDone: (GraphResult) -> Unit,
) {
    val hostState = remember { SnackbarHostState() }
    val controller: GraphEditorController = remember {
        GraphFactory.createGraphEditorController(
            density = density,
            initialGraph = initialGraph
        )
    }
    val showGraphTypeInputDialog =
        controller.inputController.takeGraphTypeInput.collectAsState().value
    val showEdgeCostDialog = controller.inputController.takeEdgeWeightInput.collectAsState().value
    val showNodeInputDialog = controller.inputController.takeNodeValueInput.collectAsState().value
    val nodeMinSizeDp = if (hasDistance) 64.dp else 48.dp

    if (showGraphTypeInputDialog) {
        GraphTypeInput(controller.inputController::onGraphTypeSelected)
    }


    val textMeasurer = rememberTextMeasurer()
    //defining the min size of node,because node can be so small if it has small string in it label
    val minNodeSizePx = with(LocalDensity.current) { nodeMinSizeDp.toPx() }





    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
            _TopBar(
                enabledRemoveNode = controller.selectedNode.collectAsState().value != null
                        || controller.selectedEdge.collectAsState().value != null,
                onAddNodeRequest = controller.inputController::onAddNodeRequest,
                onAddEdgeRequest = {
                    controller.inputController.onAddEdgeRequest()
//                    if (isWightedGraph){
//                        println("wegithed")
//                        showEdgeWeightInputDialog = true
//                    }
//                    else{
//                        controller.onEdgeConstInput(cost = null)//Adding edge with null cost
//                    }
                    // controller.onEdgeConstInput(cost = null)
                },
                onSaveRequest = {
                    val result = controller.onGraphInputCompleted()
                    onDone(result)
                },
                onRemoveNodeRequest = { controller.onRemovalRequest() }
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()

        ) {

            if (showNodeInputDialog) {
                InputDialog(
                    label = "Node",
                    onDismissRequest = controller.inputController::onAddNodeCancelRequest
                ) { inputtedText ->
                    val textSizePx = _calculateTextSizePx(inputtedText, textMeasurer)
                    controller.inputController.onAddNodeRequest(
                        label = inputtedText,
                        nodeSizePx = maxOf(textSizePx, minNodeSizePx)
                    )
                }
            }
            if (showEdgeCostDialog) {
                InputDialog(
                    label = "Edge Cost",
                    type = KeyboardType.Number,
                    onDismissRequest = controller.inputController::onAddEdgeCancelRequest
                ) {
                    controller.inputController.onEdgeConstInput(it)
                }
            }
            Editor(controller)
        }

    }

}


@Composable
private fun Editor(
    controller: GraphEditorController,
) {
    val textMeasurer = rememberTextMeasurer() //
    val nodes = controller.nodes.collectAsState().value
    val edges = controller.edges.collectAsState().value
    val edgeWith = with(LocalDensity.current) { 1.dp.toPx() }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { touchedPosition ->
                        controller.onTap(touchedPosition) //adding the node on tap
                    })
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        controller.onDragStart(it)
                    },
                    onDrag = { _, dragAmount ->

                        controller.onDrag(dragAmount)
                    },
                    onDragEnd = {

                        controller.dragEnd()
                    }
                )
            }
    ) {
        edges.forEach {
            drawEdge(it, textMeasurer, width = edgeWith)
        }
        nodes.forEach {
            drawNode(it, textMeasurer)
        }


    }
}


/*
TODO: Top Bar Section -------------Top Bar Section -------  Top Bar Section
TODO: Top Bar Section -------------Top Bar Section -------  Top Bar Section
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun _TopBar(
    enabledRemoveNode: Boolean,
    onAddNodeRequest: () -> Unit,
    onAddEdgeRequest: () -> Unit,
    onSaveRequest: () -> Unit,
    onRemoveNodeRequest: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Graph Editor",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(
                onClick = onAddNodeRequest
            ) {
                Icon(imageVector = Icons.Filled.AddCircleOutline, null)
            }

            IconButton(
                onClick = onAddEdgeRequest,
            ) {
                Icon(imageVector = Icons.Filled.Moving, null)
            }
            IconButton(
                onClick = onRemoveNodeRequest,
                enabled = enabledRemoveNode,
            ) {
                Icon(imageVector = Icons.Filled.RemoveCircleOutline, null)

            }
            IconButton(
                onClick = onSaveRequest,
            ) {
                Icon(imageVector = Icons.Filled.Save, null)
            }
            IconButton(
                onClick = {
                    //   printPdf = true

                },
            ) {
                Icon(imageVector = Icons.Filled.Print, null)
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        )

    )

}

/**
 * - Size will be Square so taking the maxOf(Height,Width)
 * - Measuring the text without any style such as font size or color or other...,so
 * if you need to change the style then apply the style before calculating text size
 * - Node size will be the same as the text size so that text can fit into it
 */
@Suppress("FunctionName")
private fun _calculateTextSizePx(label: String, measurer: TextMeasurer): Float {
    val measuredText = measurer.measure(label)
    val textHeightPx = measuredText.size.height * 1f
    val textWidthPx = measuredText.size.width * 1f
//Size will be Square so taking the maxOf(Height,Width)
    return maxOf(textHeightPx, textWidthPx)
}