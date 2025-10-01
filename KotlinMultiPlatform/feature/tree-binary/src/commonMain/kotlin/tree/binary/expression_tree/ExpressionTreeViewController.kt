package tree.binary.expression_tree

import core.lang.Logger
import core.ui.core.controller.ControllerFactory.createAutoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tree.binary.core.BaseNode
import tree.binary.tree_view.DonaldKnuthAlgorithm3
import tree.binary.tree_view.Node

class ExpressionTreeViewController {
    private val tag = this.javaClass.simpleName

    //Visual tree is just set of nodes and edges that's it
    private lateinit var iterator: Iterator<Node<String>?>
    private val _inputMode = MutableStateFlow(true)
    private var canvasWidth: Float = 300f
    private var canvasHeight: Float = 500f
    private val autoPlayer = createAutoPlayer(::next)
    private val _showControls = MutableStateFlow(true)
    private val _infix = MutableStateFlow<List<String>>(emptyList())
    private val _postFix = MutableStateFlow<List<PostFixItem>>(emptyList())
    private var _expression = ""
    private val _enableNext = MutableStateFlow(true)
    val enableNext = _enableNext.asStateFlow()
    private var preCalculation = emptyList<BaseNode>()
    val infix = _infix.asStateFlow()
    val postFix = _postFix.asStateFlow()
    private val _nodes = MutableStateFlow<List<BaseNode>>(emptyList())
    val nodes = _nodes.asStateFlow()
    private var count = 0

    /** Used data class for node as result there can be copy of same node,as result
     * updating the one instance may not updated the tree parent-child properly that is why
     * need to maintain single source of truth to determine which node are drawn
     * */
    private var _drawn = mutableSetOf<String>()

    val showControls = _showControls.asStateFlow()
    fun autoPlayRequest(delay: Int) = autoPlayer.autoPlayRequest(delay)
    fun toggleControlsVisibility() = _showControls.update { !it }
    fun reset() {

        initialize()

    }

    val inputMode = _inputMode.asStateFlow()
    fun dismissInput(){
        _inputMode.update { false }
    }

    fun next() {
        try {
            val node = preCalculation[count]
            _drawn.add(node.id)
            _nodes.update { it + node }
            count++
        } catch (_: Exception) {
        }


    }

    fun onInputComplete(expression: String) {
        _inputMode.update { false }
        _expression = expression
        initialize()
    }

    private fun initialize() {
        autoPlayer.dismiss()
        _nodes.update { emptyList() }
        _drawn.clear()
        _postFix.update { items -> items.map { it.copy(isDrawnInTree = false) } }
        count = 0
        ///
        iterator = ExpressionTreeIterator().buildIterator(_expression)

        val builder = ExpressionTreeBuilder.create()
        _infix.update { builder.buildInfix(_expression) }
        _postFix.update {
            builder.buildPostfix(_expression).mapIndexed { index, item ->
                PostFixItem(
                    label = item, id = "$index"
                )
            }
        }

        ExpressionTreeBuilder.create().buildTree2(_expression)?.let { root ->
            val nodes = DonaldKnuthAlgorithm3().calculateTreeLayout(root, canvasWidth, canvasHeight)
            preCalculation = nodes
        }
    }

    fun onCanvasSizeChanged(width: Float, height: Float) {
        canvasHeight = height
        canvasWidth = width
        Logger.on("ExpressionTree,","onCanvasSizeChanged,$width,$height")
    }

    fun onDrawn(id: String) {
        _drawn.add(id)
        //The ExpressionTreeBuilder::buildTree2 made the postfix index as the node id
        _postFix.update { items ->
            items.map { item ->
                if (item.id == id) item.copy(isDrawnInTree = true)
                else item
            }

        }
    }

    fun isDrawn(node: BaseNode) = _drawn.contains(node.id)

}
data class PostFixItem(
    val label: String,
    /** Id is unique, because there can be repeated number or operator]*/
    val id: String,
    val isDrawnInTree: Boolean = false
)
