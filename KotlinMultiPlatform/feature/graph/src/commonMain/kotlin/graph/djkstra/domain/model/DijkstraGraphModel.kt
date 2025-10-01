package graph.djkstra.domain.model

data class DijkstraGraphModel(
    val nodes:Set<NodeModel>,
    val edges:Set<EdgeModel>,
    val source: NodeModel
)