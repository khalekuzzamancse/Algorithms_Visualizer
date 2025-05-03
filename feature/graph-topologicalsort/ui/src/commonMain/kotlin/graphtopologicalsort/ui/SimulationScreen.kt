@file:Suppress("functionName")
package graphtopologicalsort.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.commonui.CodeViewer
import core.commonui.SimulationScreenEvent
import core.commonui.SimulationScreenState
import core.commonui.SimulationSlot
import core.commonui.Token
import graph.graph.GraphFactory
import graph.graph.editor.model.GraphType
import graph.graph.editor.ui.GraphEditor
import graph.graph.viewer.GraphViewer


@OptIn(ExperimentalLayoutApi::class)
@Composable
 fun TopologicalSort( navigationIcon: @Composable () -> Unit,) {

    val color = StatusColor(
        nodeUndiscovered = MaterialTheme.colorScheme.secondaryContainer,
        nodeDiscovered = MaterialTheme.colorScheme.secondary,
        nodeProcessed = MaterialTheme.colorScheme.primary,
        edgeTraversing = MaterialTheme.colorScheme.tertiary,
        edgeProcessing = MaterialTheme.colorScheme.error
    )

    val viewModel = remember {
        SimulationViewModel(
            color = color
        )
    }


    val neighborSelector = viewModel.neighborSelector
    val autoPlayer = viewModel.autoPlayer
    val showDialog = neighborSelector.neighbors.collectAsState().value.isNotEmpty()
    val inputMode = viewModel.inputMode.collectAsState().value

    if (showDialog) {
        NodeSelectionDialog(
            nodes = neighborSelector.neighbors.value,
            onDismiss = {
                neighborSelector.onSelected(
                    neighborSelector.neighbors.value.map { it.id }
                )
            },
            onConfirm = { ids ->
                neighborSelector.onSelected(ids)
            }
        )
    }
    if (inputMode) {
        GraphEditor(
            //Topological short should not allow undirected
            supportedType = listOf(GraphType.Directed, GraphType.DirectedWeighted),
            initialGraph = GraphFactory.getTopologicalSortDemoGraph()
            ,navigationIcon = navigationIcon,
        ) { result ->
            viewModel.onGraphCreated(result)
            // println(result)
        }
    } else {
        var state by remember { mutableStateOf(SimulationScreenState()) }

        SimulationSlot(
            modifier = Modifier,
            state = state,
            resultSummary = { },
            navigationIcon = navigationIcon,
            pseudoCode = { mod ->
                val code = viewModel.code.collectAsState().value
                if (code != null)
                    CodeViewer(
                        modifier = mod,
                        code = code,
                        token = Token(
                            literal = emptyList(),
                            function = emptyList(),
                            identifier = emptyList()
                        )
                    )
            },
            visualization = {
                FlowRow {
                    GraphViewer(
                        modifier = Modifier
                            //    .background(Color.Blue.copy(alpha = 0.7f))
                            .padding(16.dp),
                        controller = viewModel.graphController
                    )
                    _StatusIndicator(color)

                }

            },
            onEvent = { event ->
                when (event) {
                    is SimulationScreenEvent.AutoPlayRequest -> {

                        autoPlayer.autoPlayRequest(event.time)
                    }

                    SimulationScreenEvent.NextRequest -> viewModel.onNext()

                    SimulationScreenEvent.NavigationRequest -> {}
                    SimulationScreenEvent.ResetRequest -> {
                        viewModel.onReset()
                    }

                    SimulationScreenEvent.CodeVisibilityToggleRequest -> {
                        val isVisible = state.showPseudocode
                        state = state.copy(showPseudocode = !isVisible)
                    }

                    SimulationScreenEvent.ToggleNavigationSection -> {
                        val isVisible = state.showNavTabs
                        state = state.copy(showNavTabs = !isVisible)
                    }
                }

            },
        )


    }


}

@Composable
private fun _StatusIndicator(statusColor: StatusColor) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Node status indicators
        _StatusIndicatorBox(
            color = statusColor.nodeUndiscovered,
            label = "Node Undiscovered"
        )
        _StatusIndicatorBox(
            color = statusColor.nodeDiscovered,
            label = "Node Discovered"
        )
        _StatusIndicatorBox(
            color = statusColor.nodeProcessed,
            label = "Node Processed"
        )

        // Edge status indicators
        _StatusIndicatorBox(
            color = statusColor.edgeTraversing,
            label = "Edge Traversing"
        )
        _StatusIndicatorBox(
            color = statusColor.edgeProcessing,
            label = "Edge Processing"
        )
    }
}

@Composable
private fun _StatusIndicatorBox(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
    }
}
