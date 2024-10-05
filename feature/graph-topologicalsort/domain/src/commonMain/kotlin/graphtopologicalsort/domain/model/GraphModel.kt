package graphtopologicalsort.domain.model

data class GraphModel(
    val isDirected: Boolean,
    val nodes:Set<NodeModel>,
    val edges:Set<EdgeModel>,
)