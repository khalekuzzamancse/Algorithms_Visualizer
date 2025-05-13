package lineards._core
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import core.ui.CodeViewer
import core.ui.core.ArrayInputView
import core.ui.core.SearchInputView
import core.ui.core.SimulationScreenEvent
import core.ui.core.SimulationSlot
import core.ui.core.array.VisualArray


@OptIn(ExperimentalLayoutApi::class)
@Composable
internal  fun Route(
    modifier: Modifier = Modifier,
    viewModel: RouteController,
    navigationIcon: @Composable () -> Unit,
) {

    val showInputDialog = viewModel.inputMode.collectAsState().value
    val arrayController=viewModel.arrayController.collectAsState().value
    var state=viewModel.state.collectAsState().value

    SimulationSlot(
        modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
        disableControls = showInputDialog,
        enableNext = !showInputDialog,
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
                if (viewModel is SearchRouteControllerBase) {
                    SearchInputView(
                    onConfirm = {array,target->
                        viewModel.onTargetInputted(target)
                        viewModel.onListInputted(array)
                    }
                )
                } else if (viewModel is SortRouteControllerBase) {
                    ArrayInputView(onConfirm = viewModel::onListInputted)
                }
            } else {
                Column {
                    FlowRow {
                        if (arrayController != null) {
                            VisualArray(
                                controller = arrayController
                            )
                        }
                        else{
                            Text("Array Controller is NULL")
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
