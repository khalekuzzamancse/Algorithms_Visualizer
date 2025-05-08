package tree.binary.expression_tree

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.ui.core.ControlIconButton
import core.ui.core.CustomTextField
import core.ui.core.SimulationScreenEvent
import core.ui.core.SimulationScreenState
import core.ui.core.SimulationSlot
import core.ui.core.controller.ControllerFactory.createAutoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import tree.binary.Items
import tree.binary.PostFixItem
import tree.binary.core.BaseNode
import tree.binary.core.SpacerVertical
import tree.binary.core.ThemeInfo
import tree.binary.core.contentColor
import tree.binary.tree_view.DonaldKnuthAlgorithm3
import tree.binary.tree_view.Node

data class PostFixItem(
    val label: String,
    /** Id is unique, because there can be repeated number or operator]*/
    val id: String,
    val isDrawnInTree: Boolean = false
)

class ExpressionTreeViewController {
    private val tag = this.javaClass.simpleName

    //Visual tree is just set of nodes and edges that's it
    private lateinit var iterator: Iterator<Node<String>?>
    private val _inputMode = MutableStateFlow(true)
    private var canvasWidth: Float = 100f
    private var canvasHeight: Float = 100f
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
    private fun  initialize(){
        autoPlayer.dismiss()
        _nodes.update { emptyList() }
        _drawn.clear()
        _postFix.update { items->items.map { it.copy(isDrawnInTree = false) } }
        count=0
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

    fun onCanvasSizeChanged(width:Float,height:Float){
        canvasHeight=height
        canvasWidth=width
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

/***
 * The material component is complex layout and extra property such as click etc,
 * which has complex layout and drawing phase, that is why for app performance direct use the
 * Skia command in the draws cope.
 * that is why directly using the drawCircle,draw line...etc since we do not listen
 * click and other event, we just want  to draw
 *
 */

@Composable
fun ExpressionTree(modifier: Modifier = Modifier,  onNavBack: () -> Unit) {
    val controller = remember { ExpressionTreeViewController() }
    val state by remember { mutableStateOf(SimulationScreenState()) }
    val showControls = controller.showControls.collectAsState().value
    val showInputDialog = controller.inputMode.collectAsState().value
    val infix = controller.infix.collectAsState().value
    val postFix = controller.postFix.collectAsState().value

    if (showInputDialog) {
        _InputDialog(
            title = "",
            initial = "3 + 5 * ( ( 4 - 2 ) + ( 6 / 3 ) - ( 2 - 1 ) )",
            onAdded = controller::onInputComplete,
            onDismiss =   onNavBack,
        )
    }

    SimulationSlot(
        modifier = Modifier,
        state = state,
        disableControls = false,
        enableNext = controller.enableNext.collectAsState().value,
        navigationIcon = { },
        extraActions = {
            ControlIconButton(
                onClick = controller::toggleControlsVisibility,
                icon = if (showControls) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                contentDescription = "Autoplay",
                enabled = true
            )
        },
        visualization = {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().verticalScroll(
                    rememberScrollState()
                )
            ) {
                AnimatedVisibility(showControls) {
                    Column(Modifier.fillMaxWidth()) {
                        if (infix.isNotEmpty()) {
                            Title(text = "Infix")
                            SpacerVertical(8)
                            Items(item = infix.map { it })
                            SpacerVertical(8)
                        }
                        if (postFix.isNotEmpty()) {
                            HorizontalDivider()
                            SpacerVertical(8)
                            Title(text = "PostFix")
                            SpacerVertical(8)
                            PostFixItem( item = postFix)
                            SpacerVertical(8)
                            HorizontalDivider()
                        }
                    }
                }
                SpacerVertical(16)

                _TreeView(
                    modifier = Modifier.padding(16.dp).height(350.dp).fillMaxWidth(),
                    controller = controller
                )
            }
        },
        onEvent = { event ->
            when (event) {
                is SimulationScreenEvent.AutoPlayRequest -> {
                    controller.autoPlayRequest(event.time)
                }

                SimulationScreenEvent.NextRequest -> {
                    controller.next()
                }

                SimulationScreenEvent.NavigationRequest -> {}
                SimulationScreenEvent.ResetRequest -> {
                    controller.reset()
                }

                else -> {}
            }

        },
    )


}

@Composable
fun Title(modifier: Modifier = Modifier, text: String) {
    Text(modifier = modifier, text = text, fontSize = 18.sp)
}


@Composable
fun _TreeView(modifier: Modifier = Modifier, controller: ExpressionTreeViewController) {
    val nodes = controller.nodes.collectAsState().value
    val textMeasurer = rememberTextMeasurer()
    val  density= LocalDensity.current
    BoxWithConstraints(modifier=modifier) {
        val width=maxWidth
        val height=maxHeight
        with(density){
            controller.onCanvasSizeChanged(width.toPx(),height.toPx())
        }

        Canvas(Modifier.fillMaxSize()
            .drawBehind {
                val tag = "drawBehind"
                nodes.forEach { parent ->
                    val leftChild = parent.left
                    val rightChild = parent.right

                    if (leftChild != null && controller.isDrawn(leftChild))
                        drawEdgeOrSkip(parent, leftChild)

                    if (rightChild != null && controller.isDrawn(rightChild))
                        drawEdgeOrSkip(parent, rightChild)
                }
            }
        ) {
            nodes.forEach { parent ->
                val drawnNode = drawNode(parent, textMeasurer)
                if (drawnNode != null)
                    controller.onDrawn(drawnNode.id)
            }

        }

    }



}

fun DrawScope.drawEdgeOrSkip(parent: BaseNode, child: BaseNode) {
    var parentCenter = parent.center
    val childCenter = child.center
    val color = ThemeInfo.processingNodeColor
    if (parentCenter != null && childCenter != null) {
        val x = if (parentCenter.x.isNaN()) 0f else parentCenter.x
        val y = if (parentCenter.y.isNaN()) 0f else parentCenter.y
        parentCenter = Offset(x, y)
        drawLine(
            color = color,
            start = parentCenter,
            end = childCenter,
            strokeWidth = 2f
        )
    }

}

fun DrawScope.drawNode(node: BaseNode, textMeasurer: TextMeasurer): BaseNode? {
    val parentCenter = node.center
    val label = node.label
    val radius = 20f
    val textWidth = textMeasurer.measure(label).size.width
    val textHeight = textMeasurer.measure(label).size.height
    val move = Offset(textWidth / 2f, textHeight / 2f)
    val color = ThemeInfo.nodeColor
    //Catch edge cases such as Nan or other
    try {
        if (parentCenter != null) {
            val x = if (parentCenter.x.isNaN()) 0f else parentCenter.x
            val y = if (parentCenter.y.isNaN()) 0f else parentCenter.y
            drawCircle(color = color, center = Offset(x, y), radius = radius)
            val textOffset = parentCenter - move

            drawText(
                textMeasurer = textMeasurer,
                style = TextStyle(color = color.contentColor()),
                text = label,
                topLeft = textOffset
            )
            //Execution is here means drawn successfully
            return node
        }

    } catch (_: Exception) {

    }
    return null

}


@Composable
private fun _InputDialog(
    title: String,
    initial: String = "",
    onAdded: (String) -> Unit,
    leadingIcon: ImageVector = Icons.Outlined.Search,
    onDismiss: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf(initial) }


    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = {
            Column {
                CustomTextField(
                    label = title,
                    value = text,
                    onValueChange = { text = it },
                    keyboardType = KeyboardType.Text,
                    leadingIcon = leadingIcon
                )

            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAdded(text.trim())
                  //  onDismiss()
                }
            ) {
                Text("Add")

            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}



