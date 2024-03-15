package quick_sort.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import quick_sort.ui.visulizer.controller.UIController

/**
 * Todo(Have to store the key at a temporary variable)
 * Todo(Show the shifting effect of the element position as animated state)
 *
 */
@Composable
fun InsertionSortSimulator() {
    val list = listOf(10, 5, 4, 13, 8)
    val uiController= remember { UIController(list) }
    VisualizationRoute(cellSize = 64.dp,uiController)
}