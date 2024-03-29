package bubble_sort.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import bubble_sort.ui.components.ArraySection
import bubble_sort.ui.components.PseudoCodeSection
import layers.ui.common_ui.decorators.SimulationScreenEvent
import layers.ui.common_ui.decorators.SimulationScreenState
import layers.ui.common_ui.decorators.SimulationSlot


@Composable
internal fun <T : Comparable<T>> VisualizationDestination(
    modifier:Modifier=Modifier,
    cellSize: Dp,
    viewModel: BubbleSortViewModel<T>,
    onExitRequest: () -> Unit={},
    onResetRequest: () -> Unit={},
    onAutoPlayRequest: () -> Unit={},
) {

    var state by remember { mutableStateOf(SimulationScreenState()) }

    SimulationSlot(
        modifier = modifier,
        state = state,
        resultSummary = {
        },
        pseudoCode = { PseudoCodeSection(viewModel.pseudocode.collectAsState().value) },
        visualization = {
            ArraySection(
                list = viewModel.list.collectAsState().value,
                cellSize = cellSize,
                arrayController = viewModel.arrayController.collectAsState().value,
                i = viewModel.i.collectAsState(null).value,
                j = viewModel.j.collectAsState(null).value,
            )
        },
        onEvent = { event ->
            when (event) {
                SimulationScreenEvent.AutoPlayRequest -> onAutoPlayRequest()
                SimulationScreenEvent.NextRequest -> {
                    viewModel.onNext()
                }
                SimulationScreenEvent.NavigationRequest -> onExitRequest()
                SimulationScreenEvent.ResetRequest -> onResetRequest()
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

