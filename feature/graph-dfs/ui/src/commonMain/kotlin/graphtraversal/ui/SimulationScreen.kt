@file:Suppress("functionName")

package graphtraversal.ui

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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import core_ui.core.CodeViewer
import core_ui.core.SimulationScreenEvent
import core_ui.core.SimulationScreenState
import core_ui.core.SimulationSlot
import core_ui.core.Token
import core_ui.graph.GraphFactory
import core_ui.graph.editor.ui.GraphEditor
import core_ui.graph.viewer.GraphViewer


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DfsSimulation(navigationIcon: @Composable () -> Unit) {
//    val density = LocalDensity.current.density
    val density = LocalDensity.current.density

    val color = NodeStatusColor(
        undiscovered = MaterialTheme.colorScheme.secondaryContainer,
        discovered = MaterialTheme.colorScheme.secondary,
        processed = MaterialTheme.colorScheme.primary
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
                    neighborSelector.neighbors.value.first().id
                )
            },
            onConfirm = { ids ->
                neighborSelector.onSelected(ids)
            }
        )
    }
    if (inputMode) {
        GraphEditor(
            initialGraph = GraphFactory.getDemoGraph(density),
            navigationIcon = navigationIcon,
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
                    _NodeStatusIndicator(color)

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
private fun _NodeStatusIndicator(nodeStatusColor: NodeStatusColor) {
    Column(modifier = Modifier.padding(16.dp)) {
        _StatusIndicatorBox(
            color = nodeStatusColor.undiscovered,
            label = "Undiscovered"
        )
        _StatusIndicatorBox(
            color = nodeStatusColor.discovered,
            label = "Discovered"
        )
        _StatusIndicatorBox(
            color = nodeStatusColor.processed,
            label = "Processed"
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
