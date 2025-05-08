@file:Suppress("functionName")

package graph.prims.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import core.ui.graph.GraphFactory
import core.ui.graph.editor.model.GraphType
import graph._core.presentation.Route

@Composable
fun PrimsSimulationScreen(navigationIcon: @Composable () -> Unit) {
    val viewModel = remember { PrimsSimulationViewModel() }
    Route(
        viewModel = viewModel,
        navigationIcon = navigationIcon,
        supportedType = listOf(
        GraphType.UnDirectedWeighted,
        GraphType.DirectedWeighted
    ),
        initialGraph = GraphFactory.getMSTDemoGraph()
    )

}


