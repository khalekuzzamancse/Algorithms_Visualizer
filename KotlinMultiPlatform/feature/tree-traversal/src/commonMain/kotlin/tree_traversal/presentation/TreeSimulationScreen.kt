@file:Suppress("functionName")

package tree_traversal.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import core.lang.ComposeView
import core.lang.Logger
import core.lang.VoidCallback
import core.ui.CodeViewer
import core.ui.TreeEditor
import core.ui.core.SimulationScreenEvent
import core.ui.core.SimulationScreenState
import core.ui.core.SimulationSlot
import core.ui.graph.viewer.GraphViewer
import core.ui.graph.viewer.controller.GraphViewerController
import lineards._core.FeatureNavHost

//TODO: Fix it later, can not persist via remember
var navigateToVisualization:VoidCallback?=  null
@Composable
fun TreeSimulationScreen(navigationIcon:ComposeView) {
    val viewModel = remember { SimulationViewModel() }
    var showGraphType by remember { mutableStateOf(false) }

    FeatureNavHost(
        navigate = { navigateToVisualization=it},
        onBacked = {
            viewModel.resetInputMode()
        },
        inputScreen = {
                TreeEditor(
                    navigationIcon = navigationIcon,
                ) { result ->
                    viewModel.onGraphCreated(result)
                    showGraphType=true
                }

            if(showGraphType){
                TypeInputDialog {type->
                    viewModel.selectTraversalType(type)
                    showGraphType=false
                    navigateToVisualization?.invoke()
                }
            }

        },
        visualizationScreen = {
            _GraphViewer(
                viewModel = viewModel,
                graphController = viewModel.graphController,
                navigationIcon = it
            )
        }
    )
}


@Composable
private fun _GraphViewer(
    modifier: Modifier = Modifier,
    graphController: GraphViewerController,
    navigationIcon: @Composable () -> Unit,
    viewModel: SimulationViewModel,
) {

    var state by remember { mutableStateOf(SimulationScreenState()) }
    val autoPlayer=viewModel.autoPlayer
    SimulationSlot(
        modifier = modifier,
        state = state,
        resultSummary = { },
        navigationIcon =navigationIcon,
        pseudoCode = { mod ->
            val code = viewModel.code.collectAsState().value
            if (!code.isNullOrBlank())
                CodeViewer(
                    modifier = mod,
                    code = code,
                )
        },
        visualization = {
            GraphViewer(
                modifier = Modifier
//                    .background(Color.Gray)
                    .padding(16.dp),
                controller = graphController
            )
        },
        onEvent = { event ->
            when (event) {
                is SimulationScreenEvent.AutoPlayRequest -> {
                   autoPlayer.autoPlayRequest(event.time)
                }

                SimulationScreenEvent.NextRequest -> viewModel.onNext()

                SimulationScreenEvent.NavigationRequest -> {}
                SimulationScreenEvent.ResetRequest -> {
                    viewModel.reset()
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



