package graph_editor.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import graph_editor.domain.GraphEditorController
import graph_editor.infrastructure.GraphEditorControllerImpl
import graph_editor.ui.component.DataInputDialogue
import graph_editor.ui.component.GraphTypeInput
import graph_editor.ui.component.node.drawNode
import graph_editor.ui.component.edge.drawEdge

@Composable
fun GraphEditor(
    isTree: Boolean = false,//if tree then edge must be undirected
    density: Float = 1f,
     controller:GraphEditorController = remember { GraphEditorControllerImpl(density) }
) {
    val hostState = remember { SnackbarHostState() }

    var takeDirection by remember {
        mutableStateOf(true)
    }
    GraphTypeInput(takeDirection) {
        takeDirection = false
        if (it == "Undirected") {
            controller.onDirectionChanged(false)
        }

    }
    val textMeasurer = rememberTextMeasurer()


    var openAddNodePopup by remember { mutableStateOf(false) }
    var openAddEdgePopup by remember { mutableStateOf(false) }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
            _TopBar(
                enabledRemoveNode = controller.selectedNode.collectAsState().value != null
                        || controller.selectedEdge.collectAsState().value != null,
                onAddNodeRequest = {
                    openAddNodePopup = true
                },
                onAddEdgeRequest = {
                    openAddEdgePopup = true
                },
                onSaveRequest = {
                    controller.onSave()
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

            DataInputDialogue(
                isOpen = openAddNodePopup,
                message = "Enter Node Value"
            ) { inputtedText ->
                val textSizePx = _calculateTextSizePx(inputtedText, textMeasurer)
                controller.onAddNodeRequest(label = inputtedText, nodeSizePx = textSizePx)
                openAddNodePopup = false
            }
            DataInputDialogue(
                isOpen = openAddEdgePopup,
                message = "Enter Edge Cost"
            ) {
                controller.onEdgeConstInput(it)
                openAddEdgePopup = false
            }

            Editor(controller)
        }

    }

}


@Composable
fun Editor(
    controller: GraphEditorController,
    ) {
    val textMeasurer = rememberTextMeasurer() //
    val nodes = controller.nodes.collectAsState().value
    val edges = controller.edges.collectAsState().value

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
            drawEdge(it, textMeasurer)
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
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
            }
        },
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