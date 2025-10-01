@file:Suppress("functionName")
package graph.djkstra.presentation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import core.lang.ComposeView
import core.ui.graph.GraphFactory
import core.ui.graph.editor.model.GraphType
import graph._core.presentation.Route

@Composable
fun DijkstraSimulationScreen(navigationIcon: ComposeView) {

    val density = LocalDensity.current.density
    val color = StatusColor(
        processingEdge = MaterialTheme.colorScheme.primary,
        processedNode = MaterialTheme.colorScheme.tertiary
    )
    val viewModel = viewModel { MyViewModel(color) }
    Route(
        viewModel = viewModel.controller,
        navigationIcon = navigationIcon,
        //Dijkstra should not allow unweighted graph
        supportedType = listOf(GraphType.UnDirectedWeighted, GraphType.DirectedWeighted),
        initialGraph =  GraphFactory.getDemoGraph(density),
    )

}
private class MyViewModel(color:StatusColor): ViewModel(){
    val controller=DiSimulationContoller(color)
}
