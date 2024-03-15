package feature.search.ui.visulizer.controller.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.VisualArray
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.components.CellPointerComposable

@Composable
 fun <T> ArraySection(
    list: List<T>,
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
            if (index.isWithinRange(list.size)){
                arrayController.getCellPosition(index)?.let {
                    CellPointerComposable(
                        cellSize = cellSize,
                        position = it,
                        label = "i"
                    )
                }
            }
            

        }

    }

}
private fun Int.isWithinRange(size:Int)= this in 0..<size