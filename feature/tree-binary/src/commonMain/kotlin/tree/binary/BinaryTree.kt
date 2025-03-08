package tree.binary

import androidx.compose.runtime.Composable

@Composable
fun BinaryTree() {
    TreeView(createTree())
  //  LayoutTreeKnuthPreview()
}
fun createTree(): Node<Int> {
    val root = Node(0)
    root.left = Node(1).apply {
        left = Node(3).apply {
            left = Node(7)
            right = Node(8)
        }
        right = Node(4).apply {
            left = Node(7)
            right = Node(8)
        }
    }
    root.right = Node(2).apply {
        left = Node(5).apply {
            left = Node(9)
            right = Node(10)
        }
        right = Node(6).apply {
            left = Node(7)
            right = Node(8)
        }
    }
    return root
}