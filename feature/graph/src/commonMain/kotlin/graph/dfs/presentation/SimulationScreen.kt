@file:Suppress("functionName")

package graph.dfs.presentation
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import core_ui.CodeViewer
import core_ui.core.SimulationScreenEvent
import core_ui.core.SimulationSlot
import core_ui.graph.GraphFactory
import core_ui.graph.editor.ui.GraphEditor
import core_ui.graph.viewer.GraphViewer
import graph._core.presentation.NodeSelectionDialog
import graph._core.presentation.RouteController
import graph._core.presentation._NodeStatusIndicator


@Composable
fun DFSSimulation(navigationIcon: @Composable () -> Unit){
val viewModel= remember { DFSRouteController() }
    _DFSSimulation(viewModel=viewModel, navigationIcon = navigationIcon)
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
 fun _DFSSimulation(modifier: Modifier=Modifier, viewModel: RouteController, navigationIcon: @Composable () -> Unit) {
 val density= LocalDensity.current

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
            initialGraph = GraphFactory.getDemoGraph(density.density),
            navigationIcon = navigationIcon,
        ) { result ->
            viewModel.onGraphCreated(result)
            // println(result)
        }
    } else {
        var state=viewModel.state.collectAsState().value

        SimulationSlot(
            modifier = Modifier,
            state = state,
            resultSummary = { },
            navigationIcon = navigationIcon,
            pseudoCode = {
                    mod ->
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
                    _NodeStatusIndicator()

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
