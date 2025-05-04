package bubblesort.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import core_ui.CodeViewer
import core_ui.core.ArrayInputDialog

import core_ui.core.SimulationScreenEvent
import core_ui.core.SimulationScreenState
import core_ui.core.SimulationSlot

import core_ui.core.array.VisualArray


@Composable
fun BubbleSortRoute(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit) {
    val color = StatusColor(iPointerLocation = MaterialTheme.colorScheme.secondary)
    val viewModel = remember { BubbleSortViewModel(color = color) }

    val showInputDialog = viewModel.inputMode.collectAsState().value
    val arrayController = viewModel.arrayController.collectAsState().value


    var state by remember { mutableStateOf(SimulationScreenState()) }

    SimulationSlot(
        modifier = modifier,
        state = state,
        disableControls = showInputDialog,
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
            if (showInputDialog) {
                ArrayInputDialog(
                    onConfirm = viewModel::onInputComplete
                )
            } else {
                if (arrayController != null) {
                    VisualArray(
                        controller = arrayController
                    )
                }

            }

        },
        onEvent = { event ->
            when (event) {
                is SimulationScreenEvent.AutoPlayRequest -> {
                    viewModel.autoPlayer.autoPlayRequest(event.time)
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

                }
            }

        },
    )


}