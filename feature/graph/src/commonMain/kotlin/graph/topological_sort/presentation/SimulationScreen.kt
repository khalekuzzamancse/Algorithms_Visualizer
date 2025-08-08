@file:Suppress("functionName")
package graph.topological_sort.presentation
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import core.ui.graph.GraphFactory
import core.ui.graph.editor.model.GraphType
import graph._core.presentation.Route

@Composable
 fun TopologicalSort( navigationIcon: @Composable () -> Unit) {
     val  viewmodel= viewModel { MyViewModel() }
    Route(
        viewModel = viewmodel.controller,
        navigationIcon = navigationIcon,
        supportedType = listOf(GraphType.Directed, GraphType.DirectedWeighted),
        initialGraph = GraphFactory.getTopologicalSortDemoGraph()
    )

}
private class MyViewModel: ViewModel(){
    val controller= TopologicalRouteController()
}

