@file:Suppress("functionName")

package mst.ui

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
import core.commonui.decorators.SimulationScreenEvent
import core.commonui.decorators.SimulationScreenState
import core.commonui.decorators.SimulationSlot
import graph.graph.GraphFactory
import graph.graph.editor.ui.GraphEditor
import graph.graph.viewer.GraphViewer


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PrimsSimulationScreen( navigationIcon: @Composable () -> Unit,) {
    val color = StatusColor(
        processingEdge = MaterialTheme.colorScheme.primary,
        processedNode = MaterialTheme.colorScheme.tertiary
    )
    val viewModel = remember { SimulationViewModel(color) }
    val autoPlayer = viewModel.autoPlayer

    if (viewModel.isInputMode.collectAsState().value) {
        GraphEditor(
            initialGraph = GraphFactory.getMSTDemoGraph()
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
            pseudoCode = { },
            visualization = {
                FlowRow {
                    GraphViewer(
                        modifier = Modifier
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
private fun _NodeStatusIndicator(nodeStatusColor: StatusColor) {
    Column(modifier = Modifier.padding(16.dp)) {
        _StatusIndicatorBox(
            color = nodeStatusColor.processedNode,
            label = "Node Added to MST(processed)"
        )
        _StatusIndicatorBox(
            color = nodeStatusColor.processingEdge,
            label = "Edge added to MST"
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


