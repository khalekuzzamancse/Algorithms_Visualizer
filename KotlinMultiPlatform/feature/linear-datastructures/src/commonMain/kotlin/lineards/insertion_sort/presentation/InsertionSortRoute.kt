package lineards.insertion_sort.presentation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import lineards._core.Route
import lineards._core.SortRouteStrategy
import lineards.bubble_sort.presentation.BubbleSortController


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InsertionSortRoute(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit) {
    SortRouteStrategy(
        modifier=modifier,
        controller = InsertionSortController(),
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
