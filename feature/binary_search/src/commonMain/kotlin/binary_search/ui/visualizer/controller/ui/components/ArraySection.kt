package binary_search.ui.visualizer.controller.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.VisualArray
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.pointer.CellPointerComposable2

@Composable
fun <T> ArraySection(
    list: List<T>,
    cellSize: Dp,
    arrayController: ArrayController<T>,
    low: Int?,
    high: Int?,
    mid: Int?
) {
    Box {
        VisualArray(
            cellSize = cellSize,
            arrayController = arrayController,
            enableDrag = false
        )
        low?.let { index ->
            if (index.isWithinRange(list.size)) {
                CellPointerComposable2(
                    cellSize = cellSize,
                    position = arrayController.cells.value[index].position,
                    label = "L"
                )
            }

        }
        high?.let { index ->
            if (index.isWithinRange(list.size)) {
                CellPointerComposable2(
                    cellSize = cellSize,
                    position = arrayController.cells.value[index].position,
                    label = "R"
                )
            }

        }
        mid?.let { index ->
            if (index.isWithinRange(list.size)) {
                CellPointerComposable2(
                    cellSize = cellSize,
                    position = arrayController.cells.value[index].position,
                    label = "M"
                )
            }

        }

    }
}

private fun Int?.isWithinRange(size: Int) = this != null && this in 0..<size