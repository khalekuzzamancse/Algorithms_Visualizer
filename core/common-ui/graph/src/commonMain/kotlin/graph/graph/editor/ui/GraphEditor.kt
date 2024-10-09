package graph.graph.editor.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Input
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.Moving
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import graph.graph.GraphFactory
import graph.graph.common.drawEdge
import graph.graph.common.drawNode
import graph.graph.common.model.EditorEdgeMode
import graph.graph.common.model.EditorNodeModel
import graph.graph.common.model.GraphResult
import graph.graph.editor.controller.GraphEditorController
import graph.graph.editor.ui.component.GraphTypeInputDialog
import graph.graph.editor.ui.component.InputDialog

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
    navigationIcon:@Composable ()->Unit,
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

    val graphTypeHasTaken = controller.inputController.graphTypeHasTaken.collectAsState().value


    val textMeasurer = rememberTextMeasurer()
    //defining the min size of node,because node can be so small if it has small string in it label
    val minNodeSizePx = with(LocalDensity.current) { nodeMinSizeDp.toPx() }



    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
            _TopBar(
                navigationIcon = navigationIcon,
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
                onRemoveNodeRequest = { controller.onRemovalRequest() },
                disableAll = !(controller.inputController.graphTypeHasTaken.collectAsState().value)
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()

        ) {

            if (showGraphTypeInputDialog) {
                GraphTypeInputDialog(controller.inputController::onGraphTypeSelected)
            }

            if (!graphTypeHasTaken) {
                Instruction(
                    onGraphTypeInputRequest = {
                        controller.inputController.enableInputMode()
                    }
                )
            }



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

@Composable
private fun Instruction(
    modifier: Modifier = Modifier,
    onGraphTypeInputRequest: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ,
    ) {
        // Input Instructions Title
        Text(
            text = "Instructions",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Instruction Details
        Text(
            text = "Follow these steps to build the graph:",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))

        InstructionRow(
            icon = Icons.AutoMirrored.Filled.Input,
            text = "Select the Graph type first"
        )
        InstructionRow(
            icon = Icons.Filled.AddCircleOutline,
            text = "Add a node to the graph"
        )
        InstructionRow(
            icon = Icons.Filled.Moving,
            text = "Create an edge between nodes"
        )
        InstructionRow(
            icon = Icons.Filled.RemoveCircleOutline,
            text = "Remove a selected node or edge"
        )
        InstructionRow(
            icon = Icons.Filled.ArrowCircleRight,
            text = "Start Visualization"
        )


        Spacer(modifier = Modifier.height(24.dp))

        // Button to Select Graph Type
        Button(
            onClick = onGraphTypeInputRequest,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Input,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Choose Graph Type",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun InstructionRow(
    icon: ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

/*
TODO: Top Bar Section -------------Top Bar Section -------  Top Bar Section
TODO: Top Bar Section -------------Top Bar Section -------  Top Bar Section
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun _TopBar(
    disableAll: Boolean,
    enabledRemoveNode: Boolean,
    onAddNodeRequest: () -> Unit,
    onAddEdgeRequest: () -> Unit,
    onSaveRequest: () -> Unit,
    onRemoveNodeRequest: () -> Unit,
    navigationIcon:@Composable ()->Unit,
) {

    TopAppBar(
        title = {},
        navigationIcon = navigationIcon,
        actions = {
            // Add Node Button
            TopBarIconButton(
                enabled = !disableAll,
                icon = Icons.Filled.AddCircleOutline,
                onClick = onAddNodeRequest
            )

            // Add Edge Button
            TopBarIconButton(
                enabled = !disableAll,
                icon = Icons.Filled.Moving,
                onClick = onAddEdgeRequest
            )

            // Remove Node Button
            TopBarIconButton(
                enabled = !disableAll && enabledRemoveNode,
                icon = Icons.Filled.RemoveCircleOutline,
                onClick = onRemoveNodeRequest
            )

            //
            TopBarIconButton(
                enabled = !disableAll,
                icon = Icons.Filled.ArrowCircleRight,
                onClick = onSaveRequest
            )
        }
    )
}

@Composable
private fun TopBarIconButton(
    enabled: Boolean,
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (enabled) MaterialTheme.colorScheme.primary
            else Color.Gray
        )
    }
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