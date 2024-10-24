package graph.graph.editor.factory

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import graph.graph.common.model.EditorNodeModel
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

    //These color works for both dark and white and need to scope of composable function that is why
    //Defining here
    private val turquoiseBlue = Color(0xFF00B8D4)
    private val sunsetOrange = Color(0xFFFF7043)

    private val selectedNodeColor = turquoiseBlue
    //    private val _nodes = MutableStateFlow(emptySet<VisualNode>())
    private val _nodes= MutableStateFlow<Set<EditorNodeModel>>(emptySet())
    val nodes = _nodes.asStateFlow()

    fun setInitialNode(nodes:Set<EditorNodeModel>){
        _nodes.update {  nodes}
    }

    /*
    Observe the canvas tapping so that:
    If node is tapped we have to change it color to indicate that it is tapped.
    User may want to remove the tapped node.
    User may want to drag the tapped node.
     */
    private var _selectedEditorNodeModel = MutableStateFlow<EditorNodeModel?>(null)
    val selectedNode = _selectedEditorNodeModel.asStateFlow()
    fun observeCanvasTap(offset: Offset) {
        _selectedEditorNodeModel.value = _nodes.value.find { it.isInsideCircle(offset) }
        val aNodeIsTapped = _selectedEditorNodeModel.value != null
        if (aNodeIsTapped) {
//            deactivateAllNodes()//remove if already some node highlighted
            highlightTappedNode()
        } else
            deactivateAllNodes()
    }

    private fun highlightTappedNode() {
        //if a node is tapped
        _selectedEditorNodeModel.value?.let {
            val selectedNode = it.makeActive()
            //replace the tapped node with active node.
            _nodes.value -= it
            _nodes.value += selectedNode
        }
    }

    private fun deactivateAllNodes() {
        _selectedEditorNodeModel.value = null
        _nodes.update { nodeSet ->
            nodeSet.map {
                if (it.color == selectedNodeColor) it.copy(color = EditorNodeModel.defaultColor)
                else it
            }.toSet()
        }
    }

    private fun EditorNodeModel.makeActive(): EditorNodeModel {
        val activeNodeColor =sunsetOrange
        return copy(color = activeNodeColor)
    }


    //

    fun removeNode() {
        _selectedEditorNodeModel.value?.let { activeNode ->
            _nodes.update { nodeSet ->
                nodeSet.filter { it.id != activeNode.id }.toSet()
            }
        }

    }

    fun add(editorNodeModel: EditorNodeModel) {
        _nodes.value += editorNodeModel
    }




    fun onDragging(dragAmount: Offset) {
        _selectedEditorNodeModel.value?.let { activeNode ->
            _nodes.update { set ->
                set.map {
                    if (it.id == activeNode.id)
                        it.updateTopLeft(dragAmount)
                    else it

                }.toSet()
            }
        }
    }

    private fun EditorNodeModel.isInsideCircle(touchPosition: Offset): Boolean {
        val minX = topLeft.x
        val minY = topLeft.y
        val shapeSize = exactSizePx
        val maxX = minX + shapeSize
        val maxY = minY + shapeSize
        return touchPosition.x in minX..maxX && touchPosition.y in minY..maxY
    }

    private fun EditorNodeModel.updateTopLeft(amount: Offset): EditorNodeModel {
        var (x, y) = topLeft + amount
        if (x.isBeyondCanvas()) x = 0f
        if (y.isBeyondCanvas()) y = 0f
        return this.copy(topLeft = Offset(x, y))
    }

    private fun Float.isBeyondCanvas() = this < 0f
}