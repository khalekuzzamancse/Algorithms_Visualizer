package feature.search.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import feature.search.ui.visulizer.controller.UIController
import feature.search.ui.visulizer.VisualizationRoute


@Composable
fun LinearSearchDestination() {
    val list = listOf(10, 20, 30, 40, 50)
    val cellSize = 64.dp
    val sizePx = with(LocalDensity.current) { cellSize.toPx() }
    val controller = remember { UIController(list = list, cellSizePx = sizePx, target = 65) }
    VisualizationRoute(cellSize,controller)

}


