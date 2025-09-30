package tree.binary.expression_tree

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import core.lang.ComposeView
import core.lang.Logger
import core.lang.VoidCallback
import core.ui.core.ControlIconButton
import core.ui.core.CustomTextField
import core.ui.core.SimulationScreenEvent
import core.ui.core.SimulationScreenState
import core.ui.core.SimulationSlot
import lineards._core.FeatureNavHost
import tree.binary.Items
import tree.binary.PostFixItem
import tree.binary.core.BaseNode
import tree.binary.core.SpacerVertical
import tree.binary.core.ThemeInfo
import tree.binary.core.contentColor


@Composable
fun ExpressionTreeScreenX(modifier: Modifier = Modifier, navigationIcon: ComposeView) {
    val controller = remember { ExpressionTreeViewController() }
    val state by remember { mutableStateOf(SimulationScreenState()) }
    val showControls = controller.showControls.collectAsState().value
    val showInputDialog = controller.inputMode.collectAsState().value
    val infix = controller.infix.collectAsState().value
    val postFix = controller.postFix.collectAsState().value
    if (showInputDialog) {
        _InputScreen(
            navigationIcon = navigationIcon,
            title = "",
            initial = "3 + 5 * ( ( 4 - 2 ) + ( 6 / 3 ) - ( 2 - 1 ) )",
            onAdded = {
                //  Logger.on("ExpressionTree","clicked, :$navigateToVisualization")
                controller.onInputComplete(it)
                navigateToVisualization?.invoke()
            },
        )
    } else {
        SimulationSlot(
            modifier = Modifier.fillMaxSize(),
            state = state,
            disableControls = false,
            enableNext = controller.enableNext.collectAsState().value,
            navigationIcon = {},
            extraActions = {
                ControlIconButton(
                    onClick = controller::toggleControlsVisibility,
                    icon = if (showControls) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    contentDescription = "Autoplay",
                    enabled = true
                )
            },
            visualization = {
                val showInput = controller.inputMode.collectAsState().value
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    AnimatedVisibility(showControls) {
                        Column(Modifier.fillMaxWidth()) {
                            if (infix.isNotEmpty()) {
                                Title(text = "Infix")
                                SpacerVertical(8)
                                Items(item = infix.map { item -> item })
                                SpacerVertical(8)
                            }
                            if (postFix.isNotEmpty()) {
                                HorizontalDivider()
                                SpacerVertical(8)
                                Title(text = "PostFix")
                                SpacerVertical(8)
                                PostFixItem(item = postFix)
                                SpacerVertical(8)
                                HorizontalDivider()
                            }
                        }
                    }
                    SpacerVertical(16)
                    _TreeView(
                        modifier = Modifier.padding(16.dp).height(350.dp).fillMaxWidth(), controller = controller
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
}

///TODO: It will stored as static fix it later, right now remember() can not keep it
//after navigate back as a result navigator is null
var navigateToVisualization: VoidCallback? = null

@Composable
fun ExpressionTreeScreenXX(modifier: Modifier = Modifier, navigationIcon: ComposeView) {
    val controller = remember { ExpressionTreeViewController() }
    val state by remember { mutableStateOf(SimulationScreenState()) }
    val showControls = controller.showControls.collectAsState().value

    val infix = controller.infix.collectAsState().value
    val postFix = controller.postFix.collectAsState().value

    FeatureNavHost(modifier = modifier, navigate = {
        navigateToVisualization = it
    }, onBacked = {
        //TODO: navigateToVisualization is null after back why??
        // Logger.on("ExpressionTree","backed, :$navigateToVisualization")
    }, inputScreen = {
        _InputScreen(
            navigationIcon = navigationIcon,
            title = "",
            initial = "3 + 5 * ( ( 4 - 2 ) + ( 6 / 3 ) - ( 2 - 1 ) )",
            onAdded = {
                //  Logger.on("ExpressionTree","clicked, :$navigateToVisualization")
                controller.onInputComplete(it)
                navigateToVisualization?.invoke()
            },
        )
    }, visualizationScreen = {
            SimulationSlot(
                modifier = Modifier.fillMaxSize().background(Color.Red),
                state = state,
                disableControls = false,
                enableNext = controller.enableNext.collectAsState().value,
                navigationIcon = it,
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
                            .verticalScroll(rememberScrollState())
                    ) {
                        AnimatedVisibility(showControls) {
                            Column(Modifier.fillMaxWidth()) {
                                if (infix.isNotEmpty()) {
                                    Title(text = "Infix")
                                    SpacerVertical(8)
                                    Items(item = infix.map { item -> item })
                                    SpacerVertical(8)
                                }
                                if (postFix.isNotEmpty()) {
                                    HorizontalDivider()
                                    SpacerVertical(8)
                                    Title(text = "PostFix")
                                    SpacerVertical(8)
                                    PostFixItem(item = postFix)
                                    SpacerVertical(8)
                                    HorizontalDivider()
                                }
                            }
                        }
                        SpacerVertical(16)
                        _TreeView(
                            modifier = Modifier.padding(16.dp).height(350.dp).fillMaxWidth()
                                .background(Color.Yellow), controller = controller
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

    })

}

@Composable
fun Title(modifier: Modifier = Modifier, text: String) {
    Text(modifier = modifier, text = text, fontSize = 18.sp)
}


@Composable
fun _TreeView(modifier: Modifier = Modifier, controller: ExpressionTreeViewController) {
    val nodes = controller.nodes.collectAsState().value
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val nodeRadius= with(density){20.dp.toPx()}
    BoxWithConstraints(modifier = modifier) {
        val width = maxWidth
        val height = maxHeight
        with(density) {
            controller.onCanvasSizeChanged(width.toPx(), height.toPx())
        }
        Logger.on("ExpressionTree", "${width},$height}")
        Canvas(
            Modifier.fillMaxSize()
        ) {
            nodes.forEach { parent ->
                val leftChild = parent.left
                val rightChild = parent.right

                if (leftChild != null && controller.isDrawn(leftChild)) drawEdgeOrSkip(
                    parent,
                    leftChild
                )

                if (rightChild != null && controller.isDrawn(rightChild)) drawEdgeOrSkip(
                    parent,
                    rightChild
                )
            }
            nodes.forEach { parent ->
                val drawnNode = drawNode(parent, textMeasurer,nodeRadius)
                if (drawnNode != null) controller.onDrawn(drawnNode.id)
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
            color = color, start = parentCenter, end = childCenter, strokeWidth = 2f
        )
    }

}

fun DrawScope.drawNode(node: BaseNode, textMeasurer: TextMeasurer,radius:Float): BaseNode? {
    val parentCenter = node.center
    val label = node.label
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun _InputScreen(
    title: String,
    initial: String = "",
    onAdded: (String) -> Unit,
    navigationIcon: ComposeView,
    leadingIcon: ImageVector = Icons.Outlined.Calculate
) {
    var text by rememberSaveable { mutableStateOf(initial) }
    Scaffold(topBar = {
        TopAppBar(navigationIcon = navigationIcon, title = {})
    }) { paddingValue ->
        Column(
            modifier = Modifier.padding(paddingValue).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                label = title,
                value = text,
                onValueChange = { text = it },
                keyboardType = KeyboardType.Text,
                leadingIcon = leadingIcon
            )
            SpacerVertical(32)
            Button(
                onClick = {
                    onAdded(text.trim())
                }, modifier = Modifier
            ) {
                Text(text = "Start Visualization")
            }


        }
    }
}



