@file:Suppress("functionName")

package tree.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core_ui.core.CodeViewer
import core_ui.core.SimulationScreenEvent
import core_ui.core.SimulationScreenState
import core_ui.core.SimulationSlot
import core_ui.core.Token
import core_ui.graph.viewer.GraphViewer
import core_ui.graph.viewer.controller.GraphViewerController
import core_ui.tree.TreeEditor

@Composable
fun TreeSimulationScreen(navigationIcon: @Composable () -> Unit) {
    val viewModel = remember { SimulationViewModel() }
    val showTypeInputDialog=viewModel.traversalType.collectAsState().value==null

    if (viewModel.isInputMode.collectAsState().value) {
        TreeEditor(
            navigationIcon = navigationIcon,
        ) { result ->
            viewModel.onGraphCreated(result)

        }
    } else {
        if(showTypeInputDialog){
            TypeInputDialog {type->
                viewModel.selectTraversalType(type)
            }
        }
        _GraphViewer(
            viewModel = viewModel,
            graphController = viewModel.graphController,
            navigationIcon = navigationIcon
        )

    }


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



