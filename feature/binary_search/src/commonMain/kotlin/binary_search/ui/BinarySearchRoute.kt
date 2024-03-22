package binary_search.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import layers.ui.common_ui.decorators.tab_layout.TabDecoratorControllerImpl
import layers.ui.common_ui.dialogue.SearchInputDialoge
import binary_search.PackageLevelAccess
import binary_search.ui.destinations.VisualizationDestination

/**
 * This is only public api
 */
@OptIn(PackageLevelAccess::class)//okay to use within UI Layer
@Composable
fun BinarySearchRoute(
    modifier: Modifier = Modifier,
    onExitRequest: () -> Unit
) {

    val cellSize = 64.dp
    val sizePx = with(LocalDensity.current) { cellSize.toPx() }
    val visitedCellColor = MaterialTheme.colorScheme.secondaryContainer
    val createViewModel: () -> BinarySearchViewModel<Int> = {
        BinarySearchViewModel(
            cellSizePx = sizePx,
            visitedCellColor = visitedCellColor,
            tabController = TabDecoratorControllerImpl()
        )
    }

    var viewModel by remember {
        mutableStateOf(createViewModel())
    }

    if (viewModel.isInputMode.collectAsState().value) {
        SearchInputDialoge(
            showDialog = true,
            onDismiss = onExitRequest,

        )
    } else {
        VisualizationDestination(
            modifier = modifier,
            onExitRequest = onExitRequest,
            viewModel = viewModel,
            arrayCellSize = cellSize,
            onResetRequest = { viewModel = createViewModel() },
            onAutoPlayRequest = {}
        )

    }


}

