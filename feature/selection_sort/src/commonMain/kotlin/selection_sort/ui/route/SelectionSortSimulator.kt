package selection_sort.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import selection_sort.ui.visulizer.controller.ui.UIController

@Composable
fun SelectionSortSimulator() {
    val list = listOf(10, 5, 4, 13, 8)
    val cellSize = 64.dp
    val uiController = remember { UIController(list) }
    VisualizationRoute(cellSize, uiController)
}