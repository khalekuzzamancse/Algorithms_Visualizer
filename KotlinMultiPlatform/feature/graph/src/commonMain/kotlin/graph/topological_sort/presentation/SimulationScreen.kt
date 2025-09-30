@file:Suppress("functionName")
package graph.topological_sort.presentation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import core.ui.graph.GraphFactory
import core.ui.graph.editor.model.GraphType
import core.ui.graph.editor.ui.GraphConstant
import core.ui.graph.scaleGraph
import graph._core.presentation.Route

@Composable
 fun TopologicalSort( navigationIcon: @Composable () -> Unit) {
     val  viewmodel= viewModel { MyViewModel() }
    val density= LocalDensity.current
    val nodeSizePx= remember { with(density){ GraphConstant.nodeMinSize().toPx()} }
    Route(
        viewModel = viewmodel.controller,
        navigationIcon = navigationIcon,
        supportedType = listOf(GraphType.Directed, GraphType.DirectedWeighted),
        initialGraph = GraphFactory.getTopologicalSortDemoGraph().scaleGraph(nodeSizePx)
    )

}
private class MyViewModel: ViewModel(){
    val controller= TopologicalRouteController()
}

