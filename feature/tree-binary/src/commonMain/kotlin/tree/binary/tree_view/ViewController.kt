@file:Suppress("unused", "className", "functionName")

package tree.binary.tree_view

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface TreeViewController<T : Comparable<T>> {
    val nodes: StateFlow<List<NodeLayout>>
    val lines: StateFlow<List<VisualLine>>
    fun onCanvasSizeChanged(canvasWidth: Float, canvasHeight: Float)
    suspend fun insert(value: T,onInserting:()->Unit={},onFinish:()->Unit={})
    suspend fun search(value: T)
    suspend fun findMin()
    suspend fun findMax()
    suspend fun findSuccessor(value: T)
    suspend fun findPredecessor(value: T)
    suspend fun resetColor()

    companion object {
        fun <T : Comparable<T>> create(): TreeViewController<T> {
            return TreeViewControllerImpl(LayoutAlgorithm.create())
        }
    }
}


class TreeViewControllerImpl<T : Comparable<T>> internal
constructor(private val layoutAlgorithm: LayoutAlgorithm<T>) : TreeViewController<T> {

    private var canvasWidth: Float = 0f
    private var canvasHeight: Float = 0f
    private val _root = MutableStateFlow<Node<T>?>(null)
    private val _nodes = MutableStateFlow<List<NodeLayout>>(emptyList())
    private val _lines = MutableStateFlow<List<VisualLine>>(emptyList())
    override val nodes = _nodes.asStateFlow()
    override val lines = _lines.asStateFlow()

    override fun onCanvasSizeChanged(canvasWidth: Float, canvasHeight: Float) {
        val root = _root.value
        this.canvasWidth = canvasWidth
        this.canvasHeight = canvasHeight
        if (root != null) {
//            layoutAlgorithm = LayoutAlgorithm.create()
            layoutAlgorithm.calculateTreeLayout(root, canvasWidth, canvasHeight)
        }

    }

    override suspend fun findMin() = _handleState(bst.findMin().iterator())
    override suspend fun findMax() = _handleState(bst.findMax().iterator())


    override suspend fun findSuccessor(value: T) =
        _handleState(bst.findSuccessor(value).iterator())

    override suspend fun findPredecessor(value: T) =
        _handleState(bst.findPredecessor(value).iterator())


    private var bst: BstIterator<T> = BstIterator.create()

    override suspend fun insert(value: T,onInserting:()->Unit,onFinish:()->Unit) {
        val iterator = bst.insert(value).iterator()
        while (iterator.hasNext()) {
            onInserting()
            val state = iterator.next()
            if (state is State.ProcessingNode) {
                _highLightNode(state.id)
                delay(1000)
            }
            if (state is State.NewTree<*>) {
                try {
                    bst = state.tree as BstIterator<T>
                    val newRoot = bst.root
                    _root.update { newRoot }
                    if (newRoot != null) {
                        updateTree(newRoot, newNodeId = "$value")
                    }
                } catch (_: Exception) {
                }

            }

        }
        onFinish()


    }

    override suspend fun search(value: T) {
        val iterator = bst.search(value).iterator()
        _handleState(iterator)
    }

    private suspend fun _handleState(iterator: Iterator<State>) {
        while (iterator.hasNext()) {
            val state = iterator.next()
            if (state is State.ProcessingNode) {
                _highLightNode(state.id)
                delay(1000)
            }
            if (state is State.TargetReached) {
                _changeColor(state.id, Color.Red)
            }

        }
    }


    private fun _highLightNode(id: String) {
        _nodes.update { all ->
            all.map { node ->
                if (node.id == id) node.copy(color = Color.Cyan)
                else node
            }
        }
    }

    private fun _changeColor(id: String, color: Color) {
        _nodes.update { all ->
            all.map { node ->
                if (node.id == id) node.copy(color = color)
                else node
            }
        }
    }

    override suspend fun resetColor() {
        _nodes.update { all ->
            all.map { node ->
                node.copy(color = Color.Blue)
            }
        }
    }

    private suspend fun updateTree(root: Node<T>, newNodeId: String? = null) {
        val newTree = layoutAlgorithm.calculateTreeLayout(root, canvasWidth, canvasHeight)
        var nodes = newTree.nodes
        val lines = newTree.edges
        if (newNodeId != null) {
            nodes = nodes.map {
                if (newNodeId == it.id) it.copy(color = Color.Red)
                else it.copy(color = Color.Blue)
            }
        }
        _nodes.update { nodes }
        _lines.update { lines }
        delay(2000)
        _nodes.update { existingNode ->
            existingNode.map { it.copy(color = Color.Blue) }
        }

    }

}


data class VisualTree(val nodes: List<NodeLayout>, val edges: List<VisualLine>)
data class VisualLine(val first: Offset, val second: Offset)
