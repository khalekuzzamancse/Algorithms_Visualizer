package graph_editor

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
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import graph_editor.component.GraphTypeInput
import graph_editor.component.DataInput
import graph_editor.ui.ui.edge.drawEdge
import graph_editor.component.node.drawNode
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphEditor(
     isTree:Boolean = false,
     density: Float = 1f,
) {

    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //
    //

    val viewModel = remember {
      GraphEditorManger(density)
    }
    var takeDirection by remember {
        mutableStateOf(true)
    }
    GraphTypeInput(takeDirection) {
        takeDirection = false
        if (it == "Undirected"){
           viewModel.onDirectionChanged(false)
        }

    }


    var openAddNodePopup by remember { mutableStateOf(false) }
    var openAddEdgePopup by remember { mutableStateOf(false) }


    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
            MediumTopAppBar(
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
                        onClick = { openAddNodePopup = true },


                        ) {
                        Icon(imageVector = Icons.Filled.AddCircleOutline, null)
                    }

                    IconButton(
                        onClick = {
                            openAddEdgePopup = true
                        },
                        ) {
                        Icon(imageVector = Icons.Filled.Moving, null)
                    }
                    IconButton(
                        onClick = {
                            viewModel.onRemovalRequest()
                        },
                        enabled = viewModel.selectedNode.collectAsState().value != null
                                ||viewModel.selectedEdge.collectAsState().value != null,
                    ) {
                        Icon(imageVector = Icons.Filled.RemoveCircleOutline, null)

                    }
                    IconButton(
                        onClick = {
                            viewModel.onSave()
                            scope.launch {
                                hostState.showSnackbar("Saved Successfully")

                            }
                        },
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
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()

        ) {

            DataInput(
                isOpen = openAddNodePopup,
                message = "Enter Node Value"
            ) {
                viewModel.onAddNodeRequest(cost = it)
                openAddNodePopup = false
            }
            DataInput(
                isOpen = openAddEdgePopup,
                message = "Enter Edge Cost"
            ) {
                viewModel.onEdgeConstInput(it)
                openAddEdgePopup = false
            }

            Editor(viewModel)
        }

    }

}

@Composable
fun Editor(
    viewModel: GraphEditorManger,
) {
    val textMeasurer = rememberTextMeasurer() //
    //
    val nodes = viewModel.nodes.collectAsState().value
    val edges = viewModel.edges.collectAsState().value

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { touchedPosition ->
                        viewModel.onTap(touchedPosition) //adding the node on tap
                    })
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {

                        viewModel.onDragStart(it)
                    },
                    onDrag = { _, dragAmount ->

                        viewModel.onDrag(dragAmount)
                    },
                    onDragEnd = {

                        viewModel.dragEnd()
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

