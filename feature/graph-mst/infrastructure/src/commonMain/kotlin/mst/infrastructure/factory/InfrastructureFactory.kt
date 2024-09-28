package mst.infrastructure.factory

import mst.domain.model.DijkstraGraphModel
import mst.domain.model.EdgeModel
import mst.domain.model.NodeModel
import mst.domain.service.Graph
import mst.domain.service.Simulator

object InfrastructureFactory {
    fun createSimulator(
        graph: DijkstraGraphModel
    ): Simulator =
        SimulatorImpl(graph.nodes, graph.edges, startNode = graph.source)

    private fun _createGraph(nodes: Set<NodeModel>, edges: Set<EdgeModel>): Graph =
        GraphImpl(nodes = nodes, edges = edges)
}