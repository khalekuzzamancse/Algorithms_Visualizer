package mst.infrastructure.factory

import mst.domain.model.DijkstraGraphModel
import mst.domain.model.EdgeModel
import mst.domain.model.NodeModel
import mst.domain.service.Simulator

object InfrastructureFactory {
    fun createSimulator(
        graph: DijkstraGraphModel
    ): Simulator =
        SimulatorImpl(graph.nodes, graph.edges, startNode = graph.source)

}