package selection_sort.ui

import androidx.compose.foundation.layout.Box
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
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.components.CellPointerComposable
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableArrayController
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableVisualArray
import layers.ui.common_ui.decorators.SimulationScreenEvent
import layers.ui.common_ui.decorators.SimulationScreenState
import layers.ui.common_ui.decorators.SimulationSlot
import layers.ui.common_ui.dialogue.ArrayInputDialog
import layers.ui.common_ui.pseudocode.CodeLine
import layers.ui.common_ui.pseudocode.PseudoCodeExecutor
import selection_sort.domain.LineForPseudocode

@Composable
fun SelectionSortSimulator(
    onExitRequest: () -> Unit,
) {

    val cellSize = 64.dp
    var isInputMode by remember { mutableStateOf(true) }
    var simulateController by remember { mutableStateOf(UIController(listOf(10, 5, 4, 13, 8))) }


    if (isInputMode) {
        ArrayInputDialog(
            onDismiss = {
                //  onExitRequest
            },
            onConfirm = { list ->
                simulateController = UIController(list)
                isInputMode = false
            }
        )
    } else {
        VisualizationRoute(
            cellSize = cellSize,
            uiController = simulateController,
            onExitRequest = onExitRequest,
            onResetRequest = {},
            onAutoPlayRequest = {}
        )
    }

}

@Composable
internal fun <T : Comparable<T>> VisualizationRoute(
    cellSize: Dp,
    uiController: UIController<T>,
    onExitRequest: () -> Unit,
    onResetRequest: () -> Unit,
    onAutoPlayRequest: () -> Unit,
) {
    val arrayController = uiController.arrayController
    val algoController = uiController.algoController
    val i = uiController.i.collectAsState(null).value
    val minIndex = uiController.minIndex.collectAsState(null).value

    var state by remember { mutableStateOf(SimulationScreenState()) }


    SimulationSlot(
        modifier = Modifier,
        state = state,
        resultSummary = {
        },
        pseudoCode = { PseudoCodeSection(uiController.pseudocode.collectAsState().value) },
        visualization = {
            ArraySection(
                list = uiController.list,
                cellSize = cellSize,
                arrayController = arrayController,
                i = i,
                minIndex = minIndex,
            )
        },
        onEvent = { event ->
            when (event) {
                SimulationScreenEvent.AutoPlayRequest -> onAutoPlayRequest()
                SimulationScreenEvent.NextRequest -> {
                    algoController.next()
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
internal fun PseudoCodeSection(
    code: List<LineForPseudocode>
) {
    PseudoCodeExecutor(
        modifier = Modifier.padding(8.dp),
        code = code.map {
            CodeLine(
                line = it.line,
                highLighting = it.highLighting,
                debugText = it.debuggingText,
                lineNumber = 0
            )
        }
    )
}


@Composable
fun <T> ArraySection(
    list: List<T>,
    cellSize: Dp,
    arrayController: SwappableArrayController<T>,
    i: Int?,
    minIndex: Int?,
) {
    Box {
        SwappableVisualArray(
            cellSize = cellSize,
            controller = arrayController,
        )
        i?.let { index ->
            if (index.isWithinRange(list.size)) {
                arrayController.getCellPosition(index)?.let {
                    CellPointerComposable(
                        cellSize = cellSize,
                        position = it,
                        label = "i"
                    )
                }
            }

        }
        minIndex?.let { index ->
            if (index.isWithinRange(list.size)) {
                arrayController.getCellPosition(index)?.let {
                    CellPointerComposable(
                        cellSize = cellSize,
                        position = it,
                        label = "minIdx"
                    )
                }
            }

        }

    }
}

private fun Int?.isWithinRange(size: Int) = this != null && this in 0..<size
