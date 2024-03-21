package binary_search.ui.destinations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import binary_search.PackageLevelAccess
import binary_search.ui.BinarySearchViewModel
import binary_search.ui.components.ArraySection
import binary_search.ui.components.PseudoCodeSection
import binary_search.ui.components._ResultSummary
import layers.ui.common_ui.decorators.SimulationScreenEvent
import layers.ui.common_ui.decorators.SimulationScreenState
import layers.ui.common_ui.decorators.SimulationSlot

@OptIn(PackageLevelAccess::class)//okay to use  within the UI layer
@PackageLevelAccess //avoid to access other layer such domain or data/infrastructure
@Composable
internal fun <T : Comparable<T>> VisualizationDestination(
    modifier: Modifier = Modifier,
    arrayCellSize: Dp,
    viewModel: BinarySearchViewModel<T>,
    onExitRequest: () -> Unit,
    onResetRequest: () -> Unit,
    onAutoPlayRequest: () -> Unit,
) {

    var state by remember { mutableStateOf(SimulationScreenState()) }

    SimulationSlot(
        modifier = modifier,
        state = state,
        resultSummary = { viewModel.endState.collectAsState(null).value?.let { _ResultSummary(it) } },
        pseudoCode = { PseudoCodeSection(viewModel.pseudocode.collectAsState().value) },
        visualization = {
            ArraySection(
                cellSize = arrayCellSize,
                elements = viewModel.elements.collectAsState().value,
                arrayController = viewModel.arrayController.collectAsState().value,
                low = viewModel.low.collectAsState().value,
                high = viewModel.high.collectAsState().value,
                mid = viewModel.mid.collectAsState().value,
            )
        },
        onEvent = { event ->
            when (event) {
                SimulationScreenEvent.AutoPlayRequest -> onAutoPlayRequest()
                SimulationScreenEvent.NextRequest -> viewModel.onNext()
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
