package tree.domain.model // Enum for traversal type selection
    enum class TraversalType {
      BFS,
        /**Also represent the inorder*/
        DFS,
        PRE_ORDER, POST_ORDER
    }