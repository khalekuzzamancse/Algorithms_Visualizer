package lineards.quick_sort.presentation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import lineards._core.Route
import lineards._core.SortRouteStrategy

@Composable
fun QuickSortScreen(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit) {
    SortRouteStrategy(
        modifier=modifier,
        controller = QuickSortController(),
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