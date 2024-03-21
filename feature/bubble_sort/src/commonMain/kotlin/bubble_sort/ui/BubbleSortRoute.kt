package bubble_sort.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import layers.ui.common_ui.dialogue.ArrayInputDialog

@Composable
fun BubbleSortRoute() {
    val cellSize = 64.dp
    val viewModel = remember { BubbleSortViewModel<Int>() }
    if (viewModel.isInputMode.collectAsState().value) {
        ArrayInputDialog(
            showDialog = true,
            onDismiss = {},
            onConfirm = viewModel::onInputComplete
        )
    } else {
        VisualizationDestination(cellSize, viewModel)
    }

}