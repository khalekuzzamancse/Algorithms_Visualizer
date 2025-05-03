package core_ui.tree

import core_ui.graph.common.model.Node


data class TreeNode(
    val node: Node,
    val children: MutableList<TreeNode> = mutableListOf()
)