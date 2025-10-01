package lineards.linear_search.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import lineards.DiContainer
import lineards._core.Route
import lineards._core.SearchRouteStrategy

@Composable
fun LinearSearchRoute(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit,
) {
    SearchRouteStrategy(
        modifier=modifier,
        controller = DiContainer.lsSearchController(),
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