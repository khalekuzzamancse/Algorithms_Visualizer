package binary_search.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.VisualArray
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.components.CellPointerComposable
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import binary_search.PackageLevelAccess

@PackageLevelAccess //avoid to access other layer such domain or data/infrastructure
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
                arrayController.getCellPosition(index)?.let {
                    CellPointerComposable(
                        cellSize = cellSize,
                        position = it,
                        label = "low"
                    )
                }
            }

        }
        high?.let { index ->
            if (index.isWithinRange(list.size)) {
                arrayController.getCellPosition(index)?.let {
                    CellPointerComposable(
                        cellSize = cellSize,
                        position = it,
                        label = "high"
                    )
                }
            }

        }
        mid?.let { index ->
            if (index.isWithinRange(list.size)) {
                arrayController.getCellPosition(index)?.let {
                    CellPointerComposable(
                        cellSize = cellSize,
                        position = it,
                        label = "mid"
                    )
                }
            }

        }

    }
}

private fun Int?.isWithinRange(size: Int) = this != null && this in 0..<size