package graph._core.presentation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import core.lang.ComposeView
import core.lang.VoidCallback
import core.ui.CodeViewer
import core.ui.core.SimulationScreenEvent
import core.ui.core.SimulationSlot
import core.ui.graph.GraphFactory
import core.ui.graph.common.model.EditorEdgeModel
import core.ui.graph.common.model.EditorNodeModel
import core.ui.graph.editor.model.GraphType
import core.ui.graph.editor.ui.GraphEditor
import core.ui.graph.viewer.GraphViewer
import lineards._core.FeatureNavHost

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
    initialGraph: Pair<List<EditorNodeModel>, List<EditorEdgeModel>> =
        GraphFactory.getDemoGraph(LocalDensity.current.density),
    navigationIcon: @Composable () -> Unit,
    nodeStatusUI: ComposeView?=null
) {
    var navigateToVisualization:VoidCallback?= remember { null }
    val neighborSelector = viewModel.neighborSelector
    val autoPlayer = viewModel.autoPlayer


    FeatureNavHost(
        modifier=Modifier,
        navigate = { navigateToVisualization=it},
        onBacked = {
            viewModel.enterInputMode()
        },
        inputScreen = {
            val showDialog = neighborSelector.neighbors.collectAsState().value.isNotEmpty()
            val inputMode = viewModel.inputMode.collectAsState().value
            val graphEditMode=viewModel.graphEditMode.collectAsState().value
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
                ) { result ,type->
                    viewModel.onGraphCreated(result)
                    viewModel.updateLastEditedGraphType(type)
                    navigateToVisualization?.invoke()
                }
            }
            else{
                //on edit mode put the existing graph instead of initial
                GraphEditor(
                    initialGraph = viewModel.lastEditedGraphOrNull()?.first?:initialGraph,
                    supportedType = supportedType,
                    graphType = viewModel.lastEditedGraphOrNull()?.second,
                    navigationIcon = navigationIcon,
                ) { result ,type->
                    viewModel.onGraphCreated(result)
                    viewModel.updateLastEditedGraphType(type)
                    navigateToVisualization?.invoke()
                }
            }



        },
        visualizationScreen = {
            var state=viewModel.state.collectAsState().value
            SimulationSlot(
                modifier = Modifier,
                state = state,
                resultSummary = { },
                navigationIcon = it,
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
                    //No padding should give to graph it will cut off it has scrollable item
                    //or if has not scrollable item then give padding
                    //or give the graph padding as content padding
                    FlowRow {
                        GraphViewer(
                            modifier = Modifier

                            ,
                            controller = viewModel.graphController
                        )
                   if(nodeStatusUI!=null)
                       nodeStatusUI()
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
    )

}

