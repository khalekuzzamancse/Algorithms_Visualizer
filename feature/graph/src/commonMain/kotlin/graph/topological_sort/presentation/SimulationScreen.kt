@file:Suppress("functionName")
package graph.topological_sort.presentation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import core_ui.graph.GraphFactory
import core_ui.graph.editor.model.GraphType
import graph._core.presentation.Route


@Composable
 fun TopologicalSort( navigationIcon: @Composable () -> Unit,) {
     val  viewmodel= remember { TopologicalRouteController() }
    Route(
        viewModel = viewmodel,
        navigationIcon = navigationIcon,
        supportedType = listOf(GraphType.Directed, GraphType.DirectedWeighted),
        initialGraph = GraphFactory.getTopologicalSortDemoGraph()
    )

}
