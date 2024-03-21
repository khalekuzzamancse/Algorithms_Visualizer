package bubble_sort.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.components.CellPointerComposable
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableArrayController
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableVisualArray


@Composable
fun <T> ArraySection(
    list: List<T>,
    cellSize: Dp,
    arrayController: SwappableArrayController<T>,
    i: Int?,
    j: Int?,
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
        j?.let { index ->
            if (index.isWithinRange(list.size)) {
                arrayController.getCellPosition(index)?.let {
                    CellPointerComposable(
                        cellSize = cellSize,
                        position = it,
                        label = "j"
                    )
                }
            }

        }
        j?.let { index ->
            val `j+1`=index+1
            if (`j+1`.isWithinRange(list.size)) {
                arrayController.getCellPosition(`j+1`)?.let {
                    CellPointerComposable(
                        cellSize = cellSize,
                        position = it,
                        label = "j+1"
                    )
                }
            }


        }

    }
}

private fun Int?.isWithinRange(size: Int) = this != null && this in 0..<size