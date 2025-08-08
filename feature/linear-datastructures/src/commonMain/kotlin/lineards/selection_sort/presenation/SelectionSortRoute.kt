package lineards.selection_sort.presenation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import lineards._core.Route
import lineards._core.SortRouteStrategy
import lineards.quick_sort.presentation.QuickSortController

@Composable
fun SelectionSortRoute(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit) {
    SortRouteStrategy(
        modifier=modifier,
        controller = SelectionSortController(),
        navigationIcon=navigationIcon,
        visualizationScreen = {viewModel,backView->
            Route(
                modifier = Modifier,
                controller = viewModel.controller,
                navigationIcon = backView
            )
        }
    )
}