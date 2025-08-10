@file:Suppress("functionName")

package graph.bfs.presentation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import graph._core.presentation.Route
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun BFSSimulation(navigationIcon: @Composable () -> Unit){
val viewModel= viewModel { BFSViewModel() }
    Route(viewModel=viewModel.controller, navigationIcon = navigationIcon)
}
private class BFSViewModel:ViewModel(){
    val controller=BFSRouteController()
}

