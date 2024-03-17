package feature.search.ui.route

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import feature.search.ui.visulizer.controller.UIController
import feature.search.ui.visulizer.VisualizationRoute
import layers.ui.common_ui.InputListDialog


@Composable
fun LinearSearchDestination() {
    val cellSize = 64.dp
    val sizePx = with(LocalDensity.current) { cellSize.toPx() }
    val visitedCellColor = MaterialTheme.colorScheme.secondaryContainer
    var inputMode by remember { mutableStateOf(true) }
    var list by remember { mutableStateOf(listOf(10, 20, 30, 40, 50)) }
    var target by remember { mutableStateOf(50) }
    if (inputMode) {
        InputListDialog(
            showDialog = true,
            onDismiss = { inputMode = false }) {array,tar->
            list = array
            target=tar
            inputMode = false

        }
    } else {
        val controller = remember {
            UIController(
                list = list,
                cellSizePx = sizePx,
                target = target,
                visitedCellColor = visitedCellColor
            )
        }
        VisualizationRoute(modifier = Modifier.padding(top = 16.dp), cellSize, controller)
    }


}


