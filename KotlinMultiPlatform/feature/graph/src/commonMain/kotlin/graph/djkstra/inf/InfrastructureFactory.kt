package graph.djkstra.inf

import graph.djkstra.domain.model.DijkstraGraphModel
import graph.djkstra.domain.model.EdgeModel
import graph.djkstra.domain.model.NodeModel
import graph.djkstra.domain.service.Graph
import graph.djkstra.domain.service.Simulator

object InfrastructureFactory {
    fun createSimulator(
        graph: DijkstraGraphModel
    ): Simulator =
        SimulatorImpl(graph = _createGraph(graph.nodes, graph.edges), startNode = graph.source)

    private fun _createGraph(nodes: Set<NodeModel>, edges: Set<EdgeModel>): Graph =
        GraphImpl(nodes = nodes, edges = edges)
}