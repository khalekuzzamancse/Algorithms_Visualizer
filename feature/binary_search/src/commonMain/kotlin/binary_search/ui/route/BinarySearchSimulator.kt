package binary_search.ui.route
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import binary_search.ui.visualizer.controller.AlgoControllerImpl
import binary_search.ui.visualizer.controller.ui.UIController
import binary_search.ui.visualizer.controller.ui.VisualizationRoute

@Composable
fun BinarySearchSimulator() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,13,14,15,16,17,18,19,20)
    val target=6
    AlgoControllerImpl(list, target = target)
    val cellSize = 64.dp
    val sizePx = with(LocalDensity.current) { cellSize.toPx() }
    val uiController = remember { UIController(list = list, cellSizePx = sizePx, target = target) }
    VisualizationRoute(cellSize = 64.dp,uiController)


}