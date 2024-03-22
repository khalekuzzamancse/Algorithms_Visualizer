package bubble_sort.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import layers.ui.common_ui.decorators.tab_layout.TabDecoratorControllerImpl
import layers.ui.common_ui.dialogue.ArrayInputDialog

@Composable
fun BubbleSortRoute(
    onExitRequest:()->Unit,
) {
    val visitedCellColor = MaterialTheme.colorScheme.secondaryContainer
    val cellSize = 64.dp
    val createViewModel: () -> BubbleSortViewModel<Int> = {
        BubbleSortViewModel<Int>(visitedCellColor)
    }
    var viewModel by  remember { mutableStateOf(createViewModel()) }
    if (viewModel.isInputMode.collectAsState().value) {
        ArrayInputDialog(
            showDialog = true,
            onDismiss = {},
            onConfirm = viewModel::onInputComplete
        )
    } else {
        VisualizationDestination(
            modifier = Modifier,
            cellSize = cellSize,
            viewModel = viewModel,
            onExitRequest=onExitRequest,
            onResetRequest = {
                viewModel=createViewModel()
            }
        )
    }

}