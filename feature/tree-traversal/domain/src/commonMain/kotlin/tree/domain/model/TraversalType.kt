package tree.domain.model // Enum for traversal type selection
    enum class TraversalType(val label:String) {
      BFS("BFS"),
        /**Also represent the inorder*/
        DFS("DFS"),
        PRE_ORDER("PreOrder"), POST_ORDER("PostOrder")
    }