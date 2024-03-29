package feature.search.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.VisualArray
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.components.CellPointerComposable
import feature.search.PackageLevelAccess

@PackageLevelAccess //avoid to access other layer such domain or data/infrastructure
@Composable
 fun <T> ArraySection(
    elements: List<T>,
    cellSize: Dp,
    arrayController: ArrayController<T>,
    currentIndex: Int?
) {
    Box {
       VisualArray(
            cellSize = cellSize,
            arrayController = arrayController,
            enableDrag = false
        )
        currentIndex?.let { index ->
            if (index.isWithinRange(elements.size)){
                arrayController.getCellPosition(index)?.let {
                    CellPointerComposable(
                        cellSize = cellSize,
                        position = it,
                        label = "↑"
                    )
                }
            }
            

        }

    }

}
private fun Int.isWithinRange(size:Int)= this in 0..<size