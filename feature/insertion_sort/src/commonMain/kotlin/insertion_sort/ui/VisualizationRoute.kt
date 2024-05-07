package insertion_sort.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import insertion_sort.domain.LineForPseudocode
import layers.ui.common_ui.decorators.SimulationScreenEvent
import layers.ui.common_ui.decorators.SimulationScreenState
import layers.ui.common_ui.decorators.SimulationSlot
import layers.ui.common_ui.dialogue.ArrayInputDialog
import layers.ui.common_ui.pseudocode.CodeLine
import layers.ui.common_ui.pseudocode.PseudoCodeExecutor

/**
 * Todo(Have to store the key at a temporary variable)
 * Todo(Show the shifting effect of the element position as animated state)
 *
 */
@Composable
fun InsertionSortSimulator() {

    var isInputMode by remember { mutableStateOf(true) }
    var controller by remember { mutableStateOf(Controller(listOf(10, 5, 4, 13, 8))) }


    if (isInputMode) {
        ArrayInputDialog(
            onDismiss = {
                //  onExitRequest
            },
            onConfirm = { list ->
                controller = Controller(list)
                isInputMode = false
            }
        )
    } else {
        VisualizationRoute(
            cellSize = 64.dp,
            controller = controller,
            onResetRequest = {
                isInputMode = true
            },
            onExitRequest = {},
            onAutoPlayRequest = {}
        )
    }

}

@Composable
internal fun <T : Comparable<T>> VisualizationRoute(
    modifier: Modifier = Modifier,
    cellSize: Dp,
    controller: Controller<T>,
    onExitRequest: () -> Unit,
    onResetRequest: () -> Unit,
    onAutoPlayRequest: () -> Unit,
) {
    var state by remember { mutableStateOf(SimulationScreenState()) }
    val arrayController = controller.arrayController
    val algoController = controller.algoController
    val i = controller.i.collectAsState(null).value
    val j = controller.j.collectAsState(null).value
    val shiftedIndex = controller.shiftedIndex.collectAsState(null).value


    SimulationSlot(
        modifier = modifier,
        state = state,
        resultSummary = {
        },
        pseudoCode = { _PseudoCodeSection(emptyList()) },
        visualization = {
            ArraySection(
                list = controller.list,
                cellSize = cellSize,
                arrayController = arrayController,
                i = i,
                j = j,
                shiftedIndex = shiftedIndex
            )
        },
        onEvent = { event ->
            when (event) {
                SimulationScreenEvent.AutoPlayRequest -> onAutoPlayRequest()
                SimulationScreenEvent.NextRequest -> {
                    algoController.next()
                    //  viewModel.onNext()
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
        }
    )


}

@Composable
private fun _PseudoCodeSection(
    code: List<LineForPseudocode>
) {
    PseudoCodeExecutor(
        modifier = Modifier.padding(8.dp),
        code = code.map {
            CodeLine(line = it.line, highLighting = it.highLighting, lineNumber = 0)
        }
    )
}

