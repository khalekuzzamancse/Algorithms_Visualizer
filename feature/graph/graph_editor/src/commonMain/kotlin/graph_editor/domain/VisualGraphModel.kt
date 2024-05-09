package graph_editor.domain

data class VisualGraphModel(
    val nodes: Set<VisualNodeModel>,
    val edges: Set<VisualEdgeModel>
)
