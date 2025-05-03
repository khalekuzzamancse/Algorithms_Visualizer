@file:Suppress("functionName")

package core_ui.graph.editor.factory

import androidx.compose.ui.geometry.Offset
import core_ui.graph.common.Constants
import core_ui.graph.common.model.EditorNodeModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


internal data class GraphEditorNodeController(private val deviceDensity: Float) {
    private val _nodes = MutableStateFlow<Set<EditorNodeModel>>(emptySet())
    private val _selected = MutableStateFlow<EditorNodeModel?>(null)

    val nodes = _nodes.asStateFlow()
    val selectedNode = _selected.asStateFlow()
    fun setInitialNode(nodes: Set<EditorNodeModel>) = _nodes.update { nodes }

    /*
    Observe the canvas tapping so that:
    If node is tapped we have to change it color to indicate that it is tapped.
    User may want to remove the tapped node.
    User may want to drag the tapped node.
     */

    fun observeCanvasTap(offset: Offset) {
        val tapped= offset.getTappedNodeOrNull() ?: return
        if (tapped.isAlreadySelected())
        {
            deselect(tapped)
            return
        }
        //Clear if any other node is already selected
        resetSelection()
        //Update the newly tapped node
        _selected.update {tapped}

        onTappedNodeChanged()
    }


    private fun EditorNodeModel.isAlreadySelected()=
        (this.color== Constants.selectedNodeColor||this.color==Constants.activeNodeColor)
    private fun onTappedNodeChanged() {
        //if any node tapped or selected
        _selected.value?.let {tapped->
            val updatedNode =tapped.copy(color = Constants.activeNodeColor)
            //replace the tapped node with active node
            _nodes.value -= tapped
            _nodes.value += updatedNode
        }
    }

     fun resetSelection() {
        _selected.value = null
        _nodes.update { nodeSet ->
            nodeSet.map {
                //Restoring  all node color either from selected color or active color
                it.copy(color = EditorNodeModel.defaultColor)
            }.toSet()
        }
    }




    fun removeNode() {
        _selected.value?.let { activeNode ->
            _nodes.update { nodeSet ->
                nodeSet.filter { it.id != activeNode.id }.toSet()
            }
        }

    }

    fun add(editorNodeModel: EditorNodeModel) {
        _nodes.value += editorNodeModel
    }


    fun onDragging(dragAmount: Offset) {
        _selected.value?.let { activeNode ->
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
    private fun Offset.getTappedNodeOrNull() = _nodes.value.find { it.isInsideCircle(this) }

    private  fun deselect(node:EditorNodeModel){
        _selected.update { null }
        _nodes.update { nodeSet ->
            nodeSet.map {
                if (it.id==node.id)
                    it.copy(color = EditorNodeModel.defaultColor)
                else it
            }.toSet()
        }

    }
}