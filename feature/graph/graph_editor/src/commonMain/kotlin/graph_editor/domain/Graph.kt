package graph_editor.domain

data class Graph(
    val isDirected: Boolean,
    val nodes: Set<Node>,
    val edges: Set<Edge>
) {
    fun toAdjacencyList() {
        TODO("Not Implemented yet..")
    }
}