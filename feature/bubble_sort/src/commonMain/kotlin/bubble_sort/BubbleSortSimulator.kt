package bubble_sort

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import bubble_sort.ui.visulizer.controller.AlgoControllerImpl
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.VisualArray
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BubbleSortSimulator() {
    val list = listOf(10, 5, 4, 13, 8)
    val controller = remember { AlgoControllerImpl(list) }
    val cellSize = 64.dp
    val sizePx = with(LocalDensity.current) { cellSize.toPx() }
    val arrayController = remember { ArrayController(list, sizePx) }

    LaunchedEffect(Unit) {
        controller.algoState.collect { state ->
            println(state)
            //if should swap
            val swapPair = state.swapAblePair
            if (swapPair != null) {
                val swapPair = state.swapAblePair
                println(swapPair)
                arrayController.swapCellElement(swapPair.j, swapPair.jPlus1)
            }

        }
    }
    val scope = rememberCoroutineScope()
    Column {
        VisualArray(
            cellSize = cellSize,
            arrayController = arrayController,
        )
        Button(onClick = {
            scope.launch {
                var pos = arrayController.cells.value[1].position
                arrayController.changeElementPosition(0, pos)
                pos = arrayController.cells.value[0].position
                println(arrayController.cellsCurrentElements)
             //   delay(5_000)
             //   arrayController.changeElementPosition(1, pos)
                // arrayController.swapCellElement(0,1)
                //   delay(10_000)
                // arrayController.swapCellElement(1,2)
//                delay(10_000)
//                arrayController.swapCellElement(3,4)
//                delay(10_000)
//                arrayController.swapCellElement(0,1)
//                delay(10_000)
//                arrayController.swapCellElement(2,3)
//                delay(10_000)
            }
            // controller.next()
        }) {
            Text("Swap")
        }

    }

}