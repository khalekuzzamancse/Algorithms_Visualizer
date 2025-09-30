package graph.bfs.presentation

import graph.DiContainer
import graph._core.domain.GraphModel
import graph._core.presentation.TraversalRouteController

internal class BFSRouteController: TraversalRouteController() {
    override fun createSimulator(graph: GraphModel)=DiContainer.createBFSSimulator(graph)
}
