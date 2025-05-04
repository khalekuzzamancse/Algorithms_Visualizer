package lineards.linear_search.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import lineards._core.Route

@Composable
fun LinearSearchRoute(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit,
) {
    val viewModel = remember { LSSearchRouteController() }
    Route(modifier = modifier, viewModel = viewModel, navigationIcon = navigationIcon)

}