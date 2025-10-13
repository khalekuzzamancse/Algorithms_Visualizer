@file:Suppress("unused", "className", "functionName")

package tree.binary.tree_view

import androidx.compose.ui.geometry.Offset
import kotlin.math.max

interface State {
    data class ProcessingNode(val id: String) : State
    data class TargetReached(val id: String) : State
    data object Finish : State
    data class NewTree<T : Comparable<T>>(val tree: BstIterator<T>) : State
}


interface BstIterator<T : Comparable<T>> {
    val root: Node<T>?
    fun insert(value: T): Sequence<State>
    fun search(value: T): Sequence<State>
    fun findMin(): Sequence<State>
    fun findMax(): Sequence<State>
    fun findSuccessor(value: T): Sequence<State>
    fun findPredecessor(value: T): Sequence<State>


    companion object {
        fun <T : Comparable<T>> create(): BstIterator<T> {
            // return BstIteratorImpl(null)
            return BstIteratorImpl2(null)
        }
    }
}


private class BstIteratorImpl<T : Comparable<T>>(override val root: Node<T>?) : BstIterator<T> {

    override fun insert(value: T): Sequence<State> = sequence {
        val iterator = _insert(root, value).iterator()
        var current: Node<T>? = null
        while (iterator.hasNext()) {
            current = iterator.next()
            yield(State.ProcessingNode(current.id))
        }
        //on finish
        yield(State.NewTree(BstIteratorImpl(current)))
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

    override fun search(value: T): Sequence<State> = sequence {
        var current = root
        while (current != null) {
            yield(State.ProcessingNode(current.id)) // Emit each visited node
            current = when {
                value < current.data -> current.left
                value > current.data -> current.right
                else -> {
                    yield(State.TargetReached(current.id)) // Value found
                    return@sequence
                }
            }
        }
        yield(State.Finish)
    }

    override fun findMin(): Sequence<State> = sequence {
        var current = root
        if (current == null) {
            yield(State.Finish) // If tree is empty
            return@sequence
        }

        while (current!!.left != null) {
            yield(State.ProcessingNode(current.id)) // Emit each visited node
            current = current.left
        }

        yield(State.TargetReached(current.id)) // Found minimum value
    }

    override fun findMax(): Sequence<State> = sequence {
        var current = root
        if (current == null) {
            yield(State.Finish) // If tree is empty
            return@sequence
        }

        while (current!!.right != null) {
            yield(State.ProcessingNode(current.id)) // Emit each visited node
            current = current.right
        }

        yield(State.TargetReached(current.id)) // Found maximum value
    }

    override fun findSuccessor(value: T): Sequence<State> = sequence {
        var current = root
        var successor: Node<T>? = null

        // Step 1: Locate the node in the BST
        while (current != null) {
            //TODO:Not need to emit these, directly emit from the subtree
            current = when {
                value < current.data -> {
                    successor = current  // Store potential successor (ancestor case)
                    current.left
                }

                value > current.data -> current.right
                else -> {
                    //TODO: Target node reached
                    yield(State.ProcessingNode(current.id))
                    break
                }
            }
        }

        // If the node is not found, return Finish
        if (current == null) {
            yield(State.Finish)
            return@sequence
        }

        // Step 2: If the node has a right subtree, find the leftmost node in that subtree
        if (current.right != null) {
            successor = current.right
            while (successor?.left != null) {
                yield(State.ProcessingNode(successor.id))
                successor = successor.left
            }
        }

        // Step 3: Return the successor (either from subtree or ancestor)
        if (successor != null) {
            yield(State.TargetReached(successor.id))
        } else {
            yield(State.Finish) // No successor exists (highest value node)
        }
    }

    override fun findPredecessor(value: T): Sequence<State> = sequence {
        var current = root
        var predecessor: Node<T>? = null

        // Step 1: Locate the node in the BST
        while (current != null) {
            //TODO:Not need to emit these, directly emit from the subtree
            current = when {
                value > current.data -> {
                    predecessor = current  // Store potential predecessor (ancestor case)
                    current.right
                }

                value < current.data -> current.left
                else -> {
                    //TODO: Target node reached
                    yield(State.ProcessingNode(current.id))
                    break
                }
            }
        }

        // If the node is not found, return Finish
        if (current == null) {
            yield(State.Finish)
            return@sequence
        }

        // Step 2: If the node has a left subtree, find the rightmost node in that subtree
        if (current.left != null) {
            predecessor = current.left
            while (predecessor?.right != null) {
                yield(State.ProcessingNode(predecessor.id))
                predecessor = predecessor.right
            }
        }

        // Step 3: Return the predecessor (either from subtree or ancestor)
        if (predecessor != null) {
            yield(State.TargetReached(predecessor.id))
        } else {
            yield(State.Finish) // No predecessor exists (lowest value node)
        }
    }

}

private class BstIteratorImpl2<T : Comparable<T>>(override val root: Node<T>?) : BstIterator<T> {
    private val states = mutableListOf<Node<T>>()
    var current: Node<T>? = null
   companion object{
       var cnt = 0
       var cnt2=0
   }
    override fun insert(value: T): Sequence<State> = sequence {
        __insert(root, value)
        for (node in states) {
            yield(State.ProcessingNode(node.id))
        }
        yield(State.NewTree(BstIteratorImpl2(states.last())))
    }

    fun __insert(value: T):State {
        __insert(root, value)
        while (cnt < states.lastIndex) {
            current = states[cnt]
            if (current == null) break
            cnt++
          return  State.ProcessingNode(current!!.id)
        }
        current=states[cnt]
        return  State.NewTree(BstIteratorImpl2(current))
    }


    private fun _insert(value: T): State {
        if (root == null) {
            return State.NewTree(BstIteratorImpl2(Node(value)))
        }
        __insert(root, value)
        while (cnt < states.lastIndex) {
            current = states[cnt]
            if (current == null) break
            cnt++
            return State.ProcessingNode(current!!.id)
        }
        return State.NewTree(BstIteratorImpl2(current))
    }

    private fun __insert(root: Node<T>?, value: T) {
        if (root == null) {
            states.add(Node(value))
            return
        }

        val stack = ArrayDeque<Node<T>>() // Stack for traversal
        val path = ArrayDeque<Node<T>>() // Stack to rebuild immutable nodes

        var current = root
        while (current != null) {
            //emit each visited not means the node with we comparing, this the path
            states.add(current)
            stack.addFirst(current)
            current = when {
                value < current.data -> current.left
                value > current.data -> current.right
                else -> {
                    states.add(root) // Value exists, return original tree
                    return
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
        states.add(path.first()) // Return new immutable tree root
    }

    override fun search(value: T) = TODO()
    override fun findMin(): Sequence<State> = TODO()
    override fun findMax(): Sequence<State> = TODO()
    override fun findSuccessor(value: T) = TODO()
    override fun findPredecessor(value: T) = TODO()

}

