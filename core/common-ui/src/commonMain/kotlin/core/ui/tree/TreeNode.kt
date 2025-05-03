package graph.tree

import graph.graph.common.model.Node

data class TreeNode(
    val node: Node,
    val children: MutableList<TreeNode> = mutableListOf()
)