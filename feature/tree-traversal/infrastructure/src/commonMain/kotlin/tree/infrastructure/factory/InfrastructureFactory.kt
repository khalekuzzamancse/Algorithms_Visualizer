package tree.infrastructure.factory

import tree.domain.model.DijkstraGraphModel
import tree.domain.service.Simulator

object InfrastructureFactory {
    fun createSimulator(
        graph: DijkstraGraphModel
    ): Simulator =
        SimulatorImpl(graph.nodes, graph.edges, startNode = graph.source)

}