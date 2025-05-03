package linearsearch.ui.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import core_ui.core.CodeViewer
import core_ui.core.SearchInputDialog
import core_ui.core.SimulationScreenEvent
import core_ui.core.SimulationScreenState
import core_ui.core.SimulationSlot
import core_ui.core.array.VisualArray


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LinearSearchRoute(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit,
) {

    val viewModel = remember { SimulationViewModel() }
    val showInputDialog = viewModel.inputMode.collectAsState().value
    val arrayController = viewModel.arrayController.collectAsState().value

    var state by remember { mutableStateOf(SimulationScreenState(showPseudocode = true)) }

    SimulationSlot(
        modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
        disableControls = showInputDialog,
        state = state,
        navigationIcon = navigationIcon,
        resultSummary = { },
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
                SearchInputDialog(
                    onConfirm = viewModel::onInputComplete
                )
            } else {
                Column {
                    FlowRow {
                        if (arrayController != null) {
                            VisualArray(
                                controller = arrayController
                            )
                        }

                    }


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
