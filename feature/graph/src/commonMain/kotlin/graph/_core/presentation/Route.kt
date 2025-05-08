package graph._core.presentation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import core.ui.CodeViewer
import core.ui.core.SimulationScreenEvent
import core.ui.core.SimulationSlot
import core.ui.graph.GraphFactory
import core.ui.graph.common.model.EditorEdgeMode
import core.ui.graph.common.model.EditorNodeModel
import core.ui.graph.editor.model.GraphType
import core.ui.graph.editor.ui.GraphEditor
import core.ui.graph.viewer.GraphViewer


@OptIn(ExperimentalLayoutApi::class)
@Composable
 fun Route(
    viewModel: RouteController,
    supportedType: List<GraphType> = listOf(
        GraphType.Undirected,
        GraphType.Directed,
        GraphType.UnDirectedWeighted,
        GraphType.DirectedWeighted
    ),
    initialGraph: Pair<List<EditorNodeModel>, List<EditorEdgeMode>> = GraphFactory.getDemoGraph(LocalDensity.current.density),
    navigationIcon: @Composable () -> Unit
) {
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
            initialGraph =initialGraph,
            supportedType = supportedType,
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
