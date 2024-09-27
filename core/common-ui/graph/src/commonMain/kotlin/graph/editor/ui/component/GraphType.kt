package graph.editor.ui.component

/**
 * - Using for type safety
 */
internal sealed interface GraphType{
    val label:String

    data object Directed: GraphType {
        override val label="Directed"
    }
    data object Undirected: GraphType {
        override val label="Undirected"
    }
    data object DirectedWeighted: GraphType {
        override val label="Directed Weighted"
    }
    data object UnDirectedWeighted: GraphType {
        override val label="Undirected Weighted"
    }
    data object Tree: GraphType {
        override val label="Tree"
    }

}
