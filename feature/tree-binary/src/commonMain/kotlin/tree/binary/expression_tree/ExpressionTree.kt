package tree.binary.expression_tree

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.commonui.ControlIconButton
import core.commonui.CustomTextField
import core.commonui.SimulationScreenEvent
import core.commonui.SimulationScreenState
import core.commonui.SimulationSlot
import core.commonui.controller.ControllerFactory.createAutoPlayer
import core.lang.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tree.binary.Items
import tree.binary.core.SpacerVertical
import tree.binary.tree_view.DonaldKnuthAlgorithm2
import tree.binary.tree_view.LayoutAlgorithm
import tree.binary.tree_view.Node
import tree.binary.tree_view.NodeLayout
import tree.binary.tree_view.VisualLine
import tree.binary.tree_view.VisualNode
import tree.binary.tree_view.VisualTree

class ExpressionTreeViewController{
    private val scope = CoroutineScope(Dispatchers.Default)
    private val tag=this.javaClass.simpleName
    //Visual tree is just set of nodes and edges that's it
    private lateinit var  iterator: Iterator<Node<String>?>
    private val _inputMode=MutableStateFlow(true)
    private val _nodes=MutableStateFlow<List<NodeLayout>>(emptyList())
    private val _lines=MutableStateFlow<List<VisualLine>>(emptyList())
    private var canvasWidth: Float = 0f
    private var canvasHeight: Float = 0f
    private var _root:Node<String>?=null
    private val algorithm= LayoutAlgorithm.create<String>()
    private val autoPlayer = createAutoPlayer(::next)
    private val _showControls = MutableStateFlow(true)
    private val _infix=MutableStateFlow<List<String>>(emptyList())
    private val _postFix=MutableStateFlow<List<String>>(emptyList())
    private  var _expression=""
    private val _enableNext=MutableStateFlow(true)
    val enableNext=_enableNext.asStateFlow()
    private var _isInserting=false
    private var preCalculation= VisualTree(emptyList(), emptyList())
    val infix=_infix.asStateFlow()
    val postFix=_postFix.asStateFlow()
    private val _nodes2= MutableStateFlow<List<Node<String>>>(emptyList())
    val nodes2=_nodes2.asStateFlow()

    val showControls = _showControls.asStateFlow()
    fun autoPlayRequest(delay: Int) = autoPlayer.autoPlayRequest(delay)
    fun toggleControlsVisibility() = _showControls.update { !it }
    fun reset() {
        Logger.on(this.javaClass.simpleName,"reset")

        _nodes.update {nodes->nodes.map {
            Logger.on(tag,"${it.label}:${it.color}")
            it.copy(color = Color.Blue)
        }

        }

        autoPlayer.dismiss()
        _root=null
        iterator=ExpressionTreeIterator().buildIterator(_expression)

    }
    val inputMode=_inputMode.asStateFlow()
    val nodes=_nodes.asStateFlow()
    val lines=_lines.asStateFlow()
    var count=0
    fun  next(){
//        try {
//            val node=preCalculation.nodes[count]
//            _nodes.update {nodes->
//                nodes.map {
//                   if ( it.isSame(node)) it.copy(center = node.center) else it
//                }
//
//            }
//            count++
//        }
//
//        catch (_:Exception){}

        if(_isInserting) return

        scope.launch {
            _isInserting=true
            _enableNext.update { false }
            try {
                _root=iterator.next()
                _root?.let {root->

                    val(measuredNodes,measuredLines)= algorithm.calculateTreeLayout(root, canvasWidth, canvasHeight)
                    _nodes.value=measuredNodes.map { node->
                        val finalPosition=preCalculation.nodes.find {it.id==node.id }!!.center
                        node.copy(center = finalPosition)
                    }
//                    val changes = measuredNodes.filter {new->
//                        val notExits= _nodes.value.find { old->new.id==old.id }==null
//                        notExits
//                    }
//                    _nodes.value = measuredNodes.map { node ->
//                        val newlyAdded=changes.find { it.isSame(node) }!=null
//                        if (newlyAdded) node.copy(color = Color.Red) else node
//                    }
//                    Logger.on(tag,"changes:$changes")
//                    _lines.value = measuredLines
//                    delay(1000)
//                    _nodes.value =_nodes.value.map { node ->
//                        node.copy(color = Color.Blue)
//                    }

                }

            }
            catch (_:Exception){}
            _isInserting=false
            _enableNext.update { true }
        }

    }
    fun onInputComplete(expression: String){
        _expression=expression
        iterator=ExpressionTreeIterator().buildIterator(expression)
        _inputMode.update { false }
        val  builder=ExpressionTreeBuilder.create()
        _infix.update { builder.buildInfix(expression) }
        _postFix.update { builder.buildPostfix(expression) }

        ExpressionTreeBuilder.create().buildTree(expression)?.let { root->
            val nodes=  DonaldKnuthAlgorithm2<String>().calculateTreeLayout(root,canvasWidth, canvasHeight)
           //preCalculation= algorithm.calculateTreeLayout(root, canvasWidth, canvasHeight)
            _nodes2.update {
                DonaldKnuthAlgorithm2<String>().calculateTreeLayout(root,canvasWidth, canvasHeight)
                // preCalculation.nodes.map { it.copy(center = Offset.Zero) }
            }
        }



    }
     fun onCanvasSizeChanged(canvasWidth: Float, canvasHeight: Float) {
        //TODO: Avoid unnecessary relayout to avoid side effect
        if(canvasWidth==this.canvasWidth&&canvasHeight==this.canvasHeight)
            return
        val root = _root
        this.canvasWidth = canvasWidth
        this.canvasHeight = canvasHeight
        if (root != null) {
            updateVisualTree(tree = algorithm.calculateTreeLayout(root, canvasWidth, canvasHeight))
        }

    }
    private fun updateVisualTree(tree: VisualTree){
        _nodes.update {tree.nodes }
        _lines.update {tree.edges }
    }
}



@Composable
fun ExpressionTree(modifier: Modifier = Modifier) {
    val controller= remember { ExpressionTreeViewController() }
    val state by remember { mutableStateOf(SimulationScreenState()) }
    val showControls = controller.showControls.collectAsState().value
    val showInputDialog=controller.inputMode.collectAsState().value
    val infix=controller.infix.collectAsState().value
    val postFix=controller.postFix.collectAsState().value

    if (showInputDialog) {
        _InputDialog(
            title = "",
            initial = "3 + 5 * ( 2 - 1 )",
            onAdded = controller::onInputComplete
        ) {

        }
    }

    SimulationSlot(
        modifier = Modifier,
        state = state,
        disableControls = false,
        enableNext = controller.enableNext.collectAsState().value,
        navigationIcon = {  },
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
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            ) {
                AnimatedVisibility(showControls){
                    Column (Modifier.fillMaxWidth()){
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
                            Items(item = postFix.map { it })
                            SpacerVertical(8)
                            HorizontalDivider()
                        }
                    }
                }
                SpacerVertical(16)
                _TreeView(modifier=Modifier.fillMaxWidth().height(400.dp),controller=controller)
            }
        },
        onEvent = { event ->
            when (event) {
                is SimulationScreenEvent.AutoPlayRequest -> { controller.autoPlayRequest(event.time) }
                SimulationScreenEvent.NextRequest -> { controller.next() }
                SimulationScreenEvent.NavigationRequest -> {}
                SimulationScreenEvent.ResetRequest -> { controller.reset() }
                else -> {}
            }

        },
    )



}

@Composable
fun Title(modifier: Modifier = Modifier,text:String) {
    Text(modifier = modifier, text = text, fontSize = 18.sp)
}

@Composable
fun _TreeView(modifier: Modifier = Modifier,controller: ExpressionTreeViewController) {
    //val nodes=controller.nodes.collectAsState().value
    val nodes2=controller.nodes2.collectAsState().value
  //  val  edges=controller.lines.collectAsState().value
    BoxWithConstraints(
        modifier = modifier.drawBehind {
            nodes2.forEach {node ->
                node.left?.let { child->
                    drawLine(
                        color = Color.Black,
                        start = node.center,
                        end = child.center,
                        strokeWidth = 2f
                    )
                }
                node.right?.let { child->
                    drawLine(
                        color = Color.Black,
                        start = node.center,
                        end = child.center,
                        strokeWidth = 2f
                    )
                }

            }
        }
    ) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()
        controller.onCanvasSizeChanged(canvasWidth, canvasHeight)
        nodes2.forEach { node ->
            VisualNode(
                label = node.label,
                offset = node.center - Offset(25f, 25f),
//                color = node.color
            )

        }
    }
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
                    onDismiss()
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



