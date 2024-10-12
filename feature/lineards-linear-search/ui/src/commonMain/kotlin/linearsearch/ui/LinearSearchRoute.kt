package linearsearch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.commonui.CodeViewer
import core.commonui.SearchInputDialog
import core.commonui.SimulationScreenEvent
import core.commonui.SimulationScreenState
import core.commonui.SimulationSlot
import core.commonui.Token
import core.commonui.array.VisualArray

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LinearSearchRoute(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit,
) {
    val color = StatusColor(
        iPointerLocation = MaterialTheme.colorScheme.secondary,
        foundAt = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f)
    )
    val viewModel = remember { SimulationViewModel(color = color) }


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
                    token = Token(
                        literal = viewModel.token.literal,
                        function = viewModel.token.function,
                        identifier = viewModel.token.identifier
                    )
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
                        _StatusIndicator(color)
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


@Composable
private fun _StatusIndicator(nodeStatusColor: StatusColor) {
    Column(modifier = Modifier.padding(16.dp)) {
        _StatusIndicatorBox(
            color = nodeStatusColor.iPointerLocation,
            label = "Current"
        )
        _StatusIndicatorBox(
            color = nodeStatusColor.foundAt,
            label = "Found at"
        )
    }
}

@Composable
private fun _StatusIndicatorBox(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
    }
}
