package core.ui.graph.editor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import graph.graph.GraphFactory
import graph.graph.common.model.EditorEdgeMode
import graph.graph.common.model.EditorNodeModel
import graph.graph.common.model.GraphResult
import graph.graph.editor.controller.GraphEditorController
import graph.graph.editor.model.GraphType
import graph.graph.editor.ui.component.GraphTypeInputDialog
import graph.graph.editor.ui.component.InputDialog
import graph.graph.editor.ui.component.Editor
import graph.graph.editor.ui.component.GraphEditorToolBar
import graph.graph.editor.ui.component.Instruction
import graph.graph.editor.ui.component._calculateTextSizePx

/**
 * @param hasDistance is the graph node has distance such as Node for Dijkstra algorithm. if the graph node has distance
 * then we should make the nodes bigger enough to fit the label+distance to it that is why this parameter is necessary.
 * try to keep the distance within 2-3 digits
 * @param allowToChooseGraphType  pass `false` , for tree
 */

@Composable
fun GraphEditor(
    density: Float = LocalDensity.current.density,
    hasDistance: Boolean = false,
    graphType: GraphType? = null,
    supportedType: List<GraphType> = listOf(
        GraphType.Undirected,
        GraphType.Directed,
        GraphType.UnDirectedWeighted,
        GraphType.DirectedWeighted
    ),
    initialGraph: Pair<List<EditorNodeModel>, List<EditorEdgeMode>> = Pair(
        emptyList(),
        emptyList()
    ),
    navigationIcon: @Composable () -> Unit,
    onDone: (GraphResult) -> Unit
) {
    val hostState = remember { SnackbarHostState() }
    val controller: GraphEditorController = remember {
        GraphFactory.createGraphEditorController(
            density = density,
            initialGraph = initialGraph
        )
    }
    //Just do one time for the graph input to avoid
    LaunchedEffect(Unit){
        //if graphTypeDecided
        if (graphType != null) {
            controller.inputController.onGraphTypeSelected(graphType)
        }
    }
//    if (graphType != null) {
//        controller.inputController.onGraphTypeSelected(graphType)
//    }

    val showGraphTypeInputDialog = controller.inputController.showGraphTypeSelectionUi.collectAsState().value
    val showEdgeCostDialog = controller.inputController.showEdgeCostInputUi.collectAsState().value
    val showNodeInputDialog = controller.inputController.showNodeInputUi.collectAsState().value
    val nodeMinSizeDp = if (hasDistance) 64.dp else 48.dp

    val graphTypeHasTaken = (controller.inputController.graphTypeSelected.collectAsState().value)

    val textMeasurer = rememberTextMeasurer()
    //defining the min size of node,because node can be so small if it has small string in it label
    val minNodeSizePx = with(LocalDensity.current) { nodeMinSizeDp.toPx() }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
            GraphEditorToolBar(
                navigationIcon = navigationIcon,
                enabledRemoveNode = controller.selectedNode.collectAsState().value != null
                        || controller.selectedEdge.collectAsState().value != null,
                onAddNodeRequest = controller.inputController::onNodeDrawRequest,
                onAddEdgeRequest = {
                    controller.inputController.onEdgeDrawRequest()
                },
                onSaveRequest = {
                    val result = controller.onGraphInputCompleted()
                    onDone(result)
                },
                onRemoveNodeRequest = { controller.onRemovalRequest() },
                disableAll = !(controller.inputController.graphTypeSelected.collectAsState().value),
                onClearSelectionRequest = {
                    controller.clearSelection();
                }
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {

            if (graphType == null && !graphTypeHasTaken) {
                Instruction(
                    onGraphTypeInputRequest = {
                        controller.inputController.onGraphTypeSelectionRequest()
                    }
                )
            } else {
                Editor(controller)
            }
            if (showGraphTypeInputDialog) {
                GraphTypeInputDialog(
                    supportedTypes = supportedType,
                    controller.inputController::onGraphTypeSelected
                )
            }

            if (showNodeInputDialog) {
                InputDialog(
                    label = "Node",
                    onDismissRequest = controller.inputController::onAddNodeCancelRequest
                ) { inputtedText ->
                    val textSizePx = _calculateTextSizePx(inputtedText, textMeasurer)
                    controller.inputController.onDrawNodeRequest(
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

        }

    }

}



