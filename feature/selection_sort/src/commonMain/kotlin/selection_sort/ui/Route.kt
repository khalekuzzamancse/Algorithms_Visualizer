package selection_sort.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.components.CellPointerComposable
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableArrayController
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableVisualArray
import layers.ui.common_ui.controll_section.ControlSection
import layers.ui.common_ui.pseudocode.CodeLine
import layers.ui.common_ui.pseudocode.PseudoCodeExecutor
import selection_sort.domain.LineForPseudocode

@Composable
fun SelectionSortSimulator() {
    val list = listOf(10, 5, 4, 13, 8)
    val cellSize = 64.dp
    val uiController = remember { UIController(list) }
    VisualizationRoute(cellSize, uiController)
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun <T:Comparable<T>>VisualizationRoute(
    cellSize: Dp,
    uiController: UIController<T>
) {
    val arrayController = uiController.arrayController
    val algoController = uiController.algoController
    val i = uiController.i.collectAsState(null).value
    val minIndex=uiController.minIndex.collectAsState(null).value

    FlowRow(
        Modifier.verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ControlSection(
            onNext = algoController::next,
            showPseudocode = uiController.showPseudocode.collectAsState().value,
            onCodeVisibilityToggleRequest = uiController::togglePseudocodeVisibility
        )
        Spacer(Modifier.height(64.dp))
        ArraySection(
            list = uiController.list,
            cellSize = cellSize,
            arrayController = arrayController,
            i = i,
            minIndex = minIndex,
        )
        Column {

            AnimatedVisibility(uiController.showPseudocode.collectAsState().value) {
                PseudoCodeSection(uiController.pseudocode.collectAsState().value)
            }

        }

    }


}





@Composable
internal fun PseudoCodeSection(
    code: List<LineForPseudocode>
) {
    PseudoCodeExecutor(
        modifier = Modifier.padding(8.dp),
        code = code.map {
            CodeLine(line = it.line, highLighting = it.highLighting, debugText = it.debuggingText, lineNumber =0)
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
