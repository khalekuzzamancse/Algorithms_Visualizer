package lineards.linear_search.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import lineards.DiContainer
import lineards._core.createSearchNavGraph

@Composable
fun LinearSearchRoute(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit,
) {
    val navController= rememberNavController()
    NavHost(
        modifier = modifier,
        navController =navController,
        graph =navController.createSearchNavGraph(
            controller= DiContainer.lsSearchController(),
            navigationIcon = navigationIcon,
        )
    )

}