package graph_editor.ui.component.node

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import graph_editor.ui.component.VisualNode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/*

We used here some extension method on nodes because these are only
used by the node manager so other going  to use it,so we do not keep these
method inside the Node class itself so that the node class become lightweight
 */
internal data class GraphEditorNodeController(
    private val deviceDensity: Float,
) {

    private val _nodes = MutableStateFlow(emptySet<VisualNode>())
    val nodes = _nodes.asStateFlow()

    /*
    Observe the canvas tapping so that:
    If node is tapped we have to change it color to indicate that it is tapped.
    User may want to remove the tapped node.
    User may want to drag the tapped node.
     */
    private var _selectedVisualNode = MutableStateFlow<VisualNode?>(null)
    val selectedNode=_selectedVisualNode.asStateFlow()
    fun observeCanvasTap(offset: Offset) {
        _selectedVisualNode.value = _nodes.value.find { it.isInsideCircle(offset) }
        val aNodeIsTapped = _selectedVisualNode.value != null
        if (aNodeIsTapped){
//            deactivateAllNodes()//remove if already some node highlighted
            highlightTappedNode()
        }
        else
            deactivateAllNodes()
    }

    private fun highlightTappedNode() {
        //if a node is tapped
        _selectedVisualNode.value?.let {
            val selectedNode = it.makeActive()
            //replace the tapped node with active node.
            _nodes.value = _nodes.value - it
            _nodes.value = _nodes.value + selectedNode
        }
    }

    private fun deactivateAllNodes() {
        _selectedVisualNode.value=null
        val selectedNodeColor = Color.Blue
        _nodes.update { nodeSet ->
            nodeSet.map {
                if (it.color == selectedNodeColor) it.copy(color = Color.Red)
                else it
            }.toSet()
        }
    }

    private fun VisualNode.makeActive(): VisualNode {
        val activeNodeColor = Color.Blue
        return copy(color = activeNodeColor)
    }


    //

    fun removeNode() {
        _selectedVisualNode.value?.let { activeNode ->
            _nodes.update { nodeSet ->
                nodeSet.filter { it.id != activeNode.id }.toSet()
            }
        }

    }

    fun add(visualNode: VisualNode) {
        _nodes.value = _nodes.value + visualNode
    }

    fun setNode(set: Set<VisualNode>) {
        _nodes.update { set }
    }


    fun onDragging(dragAmount: Offset) {
        _selectedVisualNode.value?.let { activeNode ->
            _nodes.update { set ->
                set.map {
                    if(it.id==activeNode.id)
                    it.updateTopLeft(dragAmount)
                    else it

                }.toSet()
            }
        }
    }

    private fun VisualNode.isInsideCircle(touchPosition: Offset): Boolean {
        val minX = topLeft.x
        val minY = topLeft.y
        val shapeSize = exactSizePx
        val maxX = minX + shapeSize
        val maxY = minY + shapeSize
        return touchPosition.x in minX..maxX && touchPosition.y in minY..maxY
    }

    private fun VisualNode.updateTopLeft(amount: Offset): VisualNode {
        var (x, y) = topLeft + amount
        if (x.isBeyondCanvas()) x = 0f
        if (y.isBeyondCanvas()) y = 0f
        return this.copy(topLeft = Offset(x, y))
    }
    private fun Float.isBeyondCanvas() = this < 0f
}