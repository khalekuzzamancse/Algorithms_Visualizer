package tree.domain.model

data class TreeNode(
    val id: String,
    val children: MutableList<TreeNode> = mutableListOf()
)