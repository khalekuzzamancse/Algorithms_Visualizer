package graph.dfs.presentation

import graph.DiContainer
import graph._core.domain.GraphModel
import graph._core.presentation.TraversalRouteController

internal class DFSRouteController: TraversalRouteController() {
    override fun createSimulator(graph: GraphModel)=DiContainer.createDFSSimulator(graph)
}
