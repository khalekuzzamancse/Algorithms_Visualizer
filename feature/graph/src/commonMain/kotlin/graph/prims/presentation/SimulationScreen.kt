@file:Suppress("functionName")

package graph.prims.presentation
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import core.ui.graph.GraphFactory
import core.ui.graph.editor.model.GraphType
import graph._core.presentation.Route

@Composable
fun PrimsSimulationScreen(navigationIcon: @Composable () -> Unit) {
    val viewModel = viewModel { MyViewModel() }
    Route(
        viewModel = viewModel.controller,
        navigationIcon = navigationIcon,
        supportedType = listOf(
        GraphType.UnDirectedWeighted,
        GraphType.DirectedWeighted
    ),
        initialGraph = GraphFactory.getMSTDemoGraph()
    )

}
private class MyViewModel: ViewModel(){
    val controller= PrimsSimulationController()
}


