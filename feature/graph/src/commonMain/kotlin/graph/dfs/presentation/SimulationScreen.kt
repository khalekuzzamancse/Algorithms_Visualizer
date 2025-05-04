@file:Suppress("functionName")

package graph.dfs.presentation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import graph._core.presentation.Route


@Composable
fun DFSSimulation(navigationIcon: @Composable () -> Unit){
val viewModel= remember { DFSRouteController() }
    Route(viewModel=viewModel, navigationIcon = navigationIcon)
}
