@file:Suppress("functionName")

package graph.dfs.presentation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import graph._core.presentation.Route
import graph._core.presentation._NodeStatusIndicator
import graph.bfs.presentation.BFSRouteController


@Composable
fun DFSSimulation(navigationIcon: @Composable () -> Unit){
val viewModel= viewModel { MyViewModel() }
    Route(viewModel=viewModel.controller, navigationIcon = navigationIcon, nodeStatusUI = { _NodeStatusIndicator() })
}
private class MyViewModel: ViewModel(){
    val controller= DFSRouteController()
}
