package infrastructure.factory

import domain.model.DijkstraGraphModel
import domain.model.EdgeModel
import domain.model.NodeModel
import domain.service.Graph
import domain.service.Simulator

object InfrastructureFactory {
    fun createSimulator(
        graph: DijkstraGraphModel
    ): Simulator =
        SimulatorImpl(graph = _createGraph(graph.nodes, graph.edges), startNode = graph.source)

    private fun _createGraph(nodes: Set<NodeModel>, edges: Set<EdgeModel>): Graph =
        GraphImpl(nodes = nodes, edges = edges)
}