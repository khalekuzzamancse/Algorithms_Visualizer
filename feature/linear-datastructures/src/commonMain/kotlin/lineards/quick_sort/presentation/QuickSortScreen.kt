package lineards.quick_sort.presentation


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import core.ui.CodeViewer
import core.ui.core.ArrayInputDialog
import core.ui.core.SimulationScreenEvent
import core.ui.core.SimulationScreenState
import core.ui.core.SimulationSlot
import core.ui.core.array.VisualArray


@Composable
fun QuickSortScreen(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit) {
    val color = StatusColor(iPointerLocation = MaterialTheme.colorScheme.secondary)
    val quickSortViewModel = remember { QuickSortViewModel() }

    val showInputDialog = quickSortViewModel.inputMode.collectAsState().value
    val arrayController = quickSortViewModel.arrayController.collectAsState().value


    var state by remember { mutableStateOf(SimulationScreenState()) }

    SimulationSlot(
        modifier = modifier,
        state = state,
        disableControls = showInputDialog,
        navigationIcon = navigationIcon,
        resultSummary = { },
        pseudoCode = { mod ->
            val code = quickSortViewModel.code.collectAsState().value
            if (code != null)
                CodeViewer(
                    modifier = mod,
                    code = code,

                )
        },
        visualization = {
            if (showInputDialog) {
                ArrayInputDialog(
                    onConfirm = quickSortViewModel::onInputComplete
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
                    quickSortViewModel.autoPlayer.autoPlayRequest(event.time)
                }

                SimulationScreenEvent.NextRequest -> quickSortViewModel.onNext()

                SimulationScreenEvent.NavigationRequest -> {}
                SimulationScreenEvent.ResetRequest -> {
                    quickSortViewModel.onReset()
                }

                SimulationScreenEvent.CodeVisibilityToggleRequest -> {
                    val isVisible = state.showPseudocode
                    state = state.copy(showPseudocode = !isVisible)
                }

                SimulationScreenEvent.ToggleNavigationSection -> {

                }

                else -> {}
            }

        },
    )


}