@file:Suppress("functionName")

package graph.bfs.presentation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import graph._core.presentation.Route


@Composable
fun BFSSimulation(navigationIcon: @Composable () -> Unit){
val viewModel= remember { BFSRouteController() }
    Route(viewModel=viewModel, navigationIcon = navigationIcon)
}

