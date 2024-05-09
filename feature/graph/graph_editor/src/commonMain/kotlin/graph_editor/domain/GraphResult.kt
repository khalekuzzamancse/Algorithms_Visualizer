package graph_editor.domain

/**
 * - This is just transfer the edited graph result to the client module
 * - Client module should not use it own purpose that is why constructor is internal
 */
data class GraphResult internal  constructor(
    val isDirected:Boolean,
    val visualGraph: VisualGraphModel,
    val graph: Graph
)