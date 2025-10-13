@file:Suppress("unused", "className", "functionName")

package tree.binary.tree_view

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import core.lang.VoidCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tree.binary.core.ThemeInfo

interface TreeViewController<T : Comparable<T>> {
    val nodes: StateFlow<List<NodeLayout>>
    val lines: StateFlow<List<VisualLine>>
    fun onCanvasSizeChanged(canvasWidth: Float, canvasHeight: Float)
    suspend fun insert(value: T, onRunning: VoidCallback = {}, onFinish: VoidCallback = {})
    suspend fun search(value: T, onRunning: VoidCallback = {}, onFinish: VoidCallback = {})
    suspend fun findMin()
    suspend fun findMax()
    suspend fun findSuccessor(value: T)
    suspend fun findPredecessor(value: T)
    suspend fun resetColor()

    companion object {
        fun <T : Comparable<T>> create( delayOnInsertionComplete:Long, processingTime:Long): TreeViewController<T> {
            return TreeViewControllerImpl(
                algorithm = LayoutAlgorithm.create(),
                delayOnInsertionComplete = delayOnInsertionComplete,
                processingTime = processingTime
            )
        }
    }
}

/**
 * @param delayOnInsertionComplete pass time in ms
 * @param processingTime pass time is ms
 */
class TreeViewControllerImpl<T : Comparable<T>> internal
constructor(
    private val algorithm: LayoutAlgorithm<T>,
    private val delayOnInsertionComplete:Long,
    private  val processingTime:Long
) : TreeViewController<T> {

    private var canvasWidth: Float = 0f
    private var canvasHeight: Float = 0f
    private var  bst: BstIterator<T> = BstIterator.create()
    private val _root = MutableStateFlow<Node<T>?>(null)
    private val _nodes = MutableStateFlow<List<NodeLayout>>(emptyList())
    private val _lines = MutableStateFlow<List<VisualLine>>(emptyList())
    override val nodes = _nodes.asStateFlow()
    override val lines = _lines.asStateFlow()


    override fun onCanvasSizeChanged(canvasWidth: Float, canvasHeight: Float) {
        //TODO: Avoid unnecessary relayout to avoid side effect
        if(canvasWidth==this.canvasWidth&&canvasHeight==this.canvasHeight)
            return
        val root = _root.value
        this.canvasWidth = canvasWidth
        this.canvasHeight = canvasHeight

        if (root != null) {
          val (nodes,lines) = algorithm.calculateTreeLayout(root, canvasWidth, canvasHeight)
            _nodes.update { nodes }
            _lines.update { lines }

        }

    }

    override suspend fun findMin() = _withStateMachine(bst.findMin().iterator())
    override suspend fun findMax() = _withStateMachine(bst.findMax().iterator())
    override suspend fun findSuccessor(value: T) = _withStateMachine(bst.findSuccessor(value).iterator())
    override suspend fun findPredecessor(value: T) = _withStateMachine(bst.findPredecessor(value).iterator())

    override suspend fun insert(value: T, onRunning: VoidCallback, onFinish: VoidCallback) =
        _withStateMachine(
            bst.insert(value).iterator(),
            insertionNode = value,
            onRunning = onRunning,
            onFinish = onFinish
        )

    override suspend fun search(value: T, onRunning: VoidCallback, onFinish: VoidCallback) =
        _withStateMachine(
            iterator = bst.search(value).iterator(),
            onRunning = onRunning,
            onFinish = onFinish
        )

    override suspend fun resetColor() = _nodes.update { all ->
        all.map { node -> node.copy(color = ThemeInfo.nodeColor) }
    }


    private suspend fun _updateTree(root: Node<T>, newlyAddedNodeId: String? = null) {
        val newTree = algorithm.calculateTreeLayout(root, canvasWidth, canvasHeight)
        var nodes = newTree.nodes
        val lines = newTree.edges

        if (newlyAddedNodeId != null) {
            _highLightTarget(newlyAddedNodeId)
            nodes = _getUpdatedNodes(newTree, newlyAddedNodeId, ThemeInfo.targetItemColor)
        }
        _nodes.update { nodes }
        _lines.update { lines }
        delay(delayOnInsertionComplete)
        resetColor()

    }

    private fun _getUpdatedNodes(
        newTree: VisualTree,
        newlyAddedNodeId: String,
        newlyAddedNodeColor: Color
    ) =
        newTree.nodes.map { node ->
            if (newlyAddedNodeId == node.id) node.copy(color = newlyAddedNodeColor)
            else node
        }


    /**
     * @param insertionNode , if used for adding a node pass the value of it, such as for insertion operation
     * pass the value that want to insert
     */
    private suspend fun _withStateMachine(
        iterator: Iterator<State>,
        insertionNode: T? = null,
        onRunning: VoidCallback = {},
        onFinish: VoidCallback = {}
    ) {
        while (iterator.hasNext()) {
            onRunning()
            val state = iterator.next()
            if (state is State.ProcessingNode)
                _onProcessing(state.id)
            if (state is State.TargetReached)
                _highLightTarget(state.id)
            //TODO:this state emit when the tree is modified such as added a new node
            //so consume it if needed
            if (state is State.NewTree<*>) {
                try {
                    bst = state.tree as BstIterator<T>
                    val newRoot = bst.root
                    _root.update { newRoot }
                    if (newRoot != null) {
                        _updateTree(newRoot, newlyAddedNodeId = "$insertionNode")
                    }
                } catch (_: Exception) {
                }

            }

        }
        onFinish()
    }


    private fun _highLightTarget(nodeId: String) = _changeColor(nodeId, ThemeInfo.targetItemColor)

    private suspend fun _onProcessing(nodeId: String) {
        _changeColor(nodeId, ThemeInfo.processingNodeColor)
        delay(processingTime)
    }

    private fun _changeColor(id: String, color: Color) = _nodes.update { all ->
        all.map { node ->
            if (node.id == id) node.copy(color = color)
            else node
        }
    }

}

data class VisualTree(val nodes: List<NodeLayout>, val edges: List<VisualLine>)
data class VisualLine(val first: Offset, val second: Offset)