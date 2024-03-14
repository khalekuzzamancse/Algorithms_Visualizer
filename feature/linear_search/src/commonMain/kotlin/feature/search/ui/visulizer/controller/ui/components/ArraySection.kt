package feature.search.ui.visulizer.controller.ui.components

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
                CellPointerComposable2(
                    cellSize = cellSize,
                    position = arrayController.cells.value[index].position,
                    label = "i"
                )
            }
            

        }

    }

}
private fun Int.isWithinRange(size:Int)= this in 0..<size