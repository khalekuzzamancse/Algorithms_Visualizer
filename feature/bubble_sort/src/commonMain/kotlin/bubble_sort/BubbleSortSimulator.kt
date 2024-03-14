package bubble_sort

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import bubble_sort.ui.visulizer.controller.AlgoControllerImpl
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableArrayController
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.swappable.SwappableVisualArray

@Composable
fun BubbleSortSimulator() {
    val list = listOf(10, 5, 4, 13, 8)
    val cellSize = 64.dp
    val arrayController = remember { SwappableArrayController(list) }
    val controller = remember { AlgoControllerImpl(list) }
    LaunchedEffect(Unit) {
        controller.algoState.collect { state ->
            val swapPair = state.swapAblePair
            if (swapPair != null) {
                arrayController.swapElement(swapPair.j, swapPair.jPlus1)
            }

        }
    }
    Column {
        SwappableVisualArray(cellSize, arrayController)
        Button(onClick = {
            controller.next()
        }) {
            Text("Next")
        }

    }

}