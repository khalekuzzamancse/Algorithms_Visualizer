package tree.binary.tree_view

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.max

interface BinarySearchTree<T : Comparable<T>> {
    val root: Node<T>?
    fun insert(value: T): Sequence<State>



    companion object{
        fun <T:Comparable<T>> create():BinarySearchTree<T>{
            return  BinarySearchTreeImpl(null)
        }
    }
}


data class Node<T>(val data: T, val left: Node<T>? = null, val right: Node<T>? = null, val id: String = "$data") {

    fun getDepth(): Int {
        val leftDepth = left?.getDepth() ?: 0
        val rightDepth = right?.getDepth() ?: 0
        return 1 + max(leftDepth, rightDepth)
    }

    val label = "$data"
}

data class NodeLayout(
    val center: Offset,
    val label: String,
    val id: String,
    val color: Color = Color.Blue
)




private class BinarySearchTreeImpl<T : Comparable<T>>(override val root: Node<T>?) : BinarySearchTree<T> {

    override fun insert(value: T) = sequence {
        val iterator = _insert(root, value).iterator()
        var current: Node<T>? = null
        while (iterator.hasNext()) {
            current = iterator.next()
            yield(State.ProcessingNode(current.id))
        }
        //on finish
        yield(State.NewTree(BinarySearchTreeImpl(current)))
    }

    private fun _insert(root: Node<T>?, value: T): Sequence<Node<T>> = sequence {
        if (root == null) {
            yield(Node(value))
            return@sequence
        }

        val stack = ArrayDeque<Node<T>>() // Stack for traversal
        val path = ArrayDeque<Node<T>>() // Stack to rebuild immutable nodes

        var current = root
        while (current != null) {
            yield(current) //emit each visited not means the node with we comparing, this the path
            stack.addFirst(current)

            current = when {
                value < current.data -> current.left
                value > current.data -> current.right
                else -> {
                    yield(root) // Value exists, return original tree
                    return@sequence
                }
            }
        }

        // Insert new node at the correct position
        var newNode = Node(value)

        // Rebuild the immutable tree from the stack
        while (stack.isNotEmpty()) {
            val parent = stack.removeFirst()
            newNode = if (value < parent.data) {
                Node(parent.data, newNode, parent.right) // Recreate with updated left child
            } else {
                Node(parent.data, parent.left, newNode) // Recreate with updated right child
            }
            path.addFirst(newNode)
        }

        yield(path.first()) // Return new immutable tree root
    }
}