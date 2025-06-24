package lineards.binary_search.presentation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import lineards.DiContainer
import lineards._core.createSearchNavGraph

@Composable
fun BinarySearchRoute(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit) {
//    val viewModel= remember { BSRouteController() }
//    Route(modifier,viewModel,navigationIcon)
    val navController= rememberNavController()
    NavHost(
        modifier = modifier,
        navController =navController,
        graph =navController.createSearchNavGraph(
            controller= DiContainer.bsSearchController(),
            navigationIcon = navigationIcon,
        )
    )
}
