package feature.search.ui.destinations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import feature.search.PackageLevelAccess
import feature.search.ui.LinearSearchViewModel
import feature.search.ui.components.ArraySection
import feature.search.ui.components.PseudoCodeSection
import feature.search.ui.components._ResultSummary
import layers.ui.common_ui.decorators.SimulationScreenEvent
import layers.ui.common_ui.decorators.SimulationScreenState
import layers.ui.common_ui.decorators.SimulationSlot


@OptIn(PackageLevelAccess::class)//okay to use  within the UI layer
@PackageLevelAccess //avoid to access other layer such domain or data/infrastructure
@Composable
internal fun <T : Comparable<T>> VisualizationDestination(
    modifier: Modifier = Modifier,
    arrayCellSize: Dp,
    viewModel: LinearSearchViewModel<T>,
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
            val arrayController = viewModel.arrayController.collectAsState().value
            val currentIndex = viewModel.currentIndex.collectAsState(null).value
            val arrayElements = viewModel.elements.collectAsState().value
            ArraySection(arrayElements, arrayCellSize, arrayController, currentIndex)
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
