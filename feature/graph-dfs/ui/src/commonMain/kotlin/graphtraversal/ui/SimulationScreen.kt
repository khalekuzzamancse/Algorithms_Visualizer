@file:Suppress("functionName")

package graphtraversal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.commonui.decorators.SimulationScreenEvent
import core.commonui.decorators.SimulationScreenState
import core.commonui.decorators.SimulationSlot
import graph.graph.GraphFactory
import graph.graph.editor.ui.GraphEditor
import graph.graph.viewer.GraphViewer
import graph.graph.viewer.controller.GraphViewerController


@Composable
fun DfsSimulation() {
    val viewModel = remember { SimulationViewModel() }
    if (viewModel.isInputMode.collectAsState().value) {
        GraphEditor(
            initialGraph = GraphFactory.getDFSDemoGraph()
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
            pseudoCode = { },
            visualization = {
                GraphViewer(
                    modifier = Modifier
                        .background(Color.Blue.copy(alpha = 0.7f))
                        .padding(16.dp),
                    controller = viewModel.graphController
                )
            },
            onEvent = { event ->
                when (event) {
                    is SimulationScreenEvent.AutoPlayRequest -> {

                    }

                    SimulationScreenEvent.NextRequest -> viewModel.onNext()

                    SimulationScreenEvent.NavigationRequest -> {}
                    SimulationScreenEvent.ResetRequest -> {

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

