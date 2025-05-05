@file:Suppress("functionName")

package graph.prims.presentation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core_ui.CodeViewer
import core_ui.core.SimulationScreenEvent
import core_ui.core.SimulationScreenState
import core_ui.core.SimulationSlot
import core_ui.graph.GraphFactory
import core_ui.graph.editor.model.GraphType
import core_ui.graph.editor.ui.GraphEditor
import core_ui.graph.viewer.GraphViewer


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PrimsSimulationScreen(navigationIcon: @Composable () -> Unit) {
    val viewModel = remember { SimulationViewModel() }
    val autoPlayer = viewModel.autoPlayer
    if (viewModel.inputMode.collectAsState().value) {
        GraphEditor(
            //MST should not allow unweighted
            supportedType = listOf(GraphType.UnDirectedWeighted, GraphType.DirectedWeighted),
            initialGraph = GraphFactory.getMSTDemoGraph(),
            navigationIcon = navigationIcon,
        ) { result ->
            viewModel.onGraphCreated(result)
            // println(result)
        }
    } else {
        var state by remember { mutableStateOf(SimulationScreenState()) }

        SimulationSlot(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            state = state,
            resultSummary = { },
            navigationIcon = navigationIcon,
            pseudoCode = { mod ->
                val code = viewModel.code.collectAsState().value
                if (code != null)
                    CodeViewer(
                        modifier = mod,
                        code = code,

                    )
            },
            visualization = {
                FlowRow {
                    GraphViewer(
                        modifier = Modifier
                            .padding(16.dp),
                        controller = viewModel.graphController
                    )

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


