package core.ui.graph.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.lang.Logger
import core.ui.SpacerHorizontal
import core.ui.SpacerVertical
import core.ui.graph.common.drawEdge
import core.ui.graph.common.drawNode
import core.ui.graph.common.model.EditorEdgeModel
import core.ui.graph.common.model.EditorNodeModel
import core.ui.graph.editor.controller.GraphEditorController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface CanvasController {
    /** (Width,height)Null means not decided yet*/
    val size: StateFlow<Pair<Dp, Dp>?>
    val show: StateFlow<Boolean>
    fun updateWidth(value: Dp)
    fun updateHeight(value: Dp)
    fun updateSize(width: Dp, height: Dp)
    fun sizeAccordingToContent(
        nodes: List<EditorNodeModel>,
        edges: List<EditorEdgeModel>,
        density: Density
    )

    fun showDialog()
    fun dismiss()
}

class CanvasControllerImpl : CanvasController {
    override val size = MutableStateFlow<Pair<Dp, Dp>?>(null)
    override val show = MutableStateFlow(false)
    override fun updateWidth(value: Dp) {
        size.update {
            it?.copy(first = value) ?: Pair(value, 0.dp)
        }
    }

    override fun updateHeight(value: Dp) {
        size.update {
            it?.copy(second = value) ?: Pair(0.dp, value)
        }
    }

    override fun updateSize(width: Dp, height: Dp) {
        size.update {
            it?.copy(first = width, second = height) ?: Pair(width, height)
        }
    }

    //Calculating exact size that need to render the node and parent
    //This is helpful when there is initial graph set
    //Is there is already no edge or node in the graph the canvasUtils should return a default min size
    //so that graph can draw here..
    override fun sizeAccordingToContent(
        nodes: List<EditorNodeModel>,
        edges: List<EditorEdgeModel>,
        density: Density,
    ) {
        if (nodes.isEmpty() && edges.isEmpty())
            return
        val points = getMaxXY(nodes = nodes.map { it.topLeft },
            starts = edges.map { it.start }, ends = edges.map { it.end },
            controls = edges.map { it.control })
        //Dealing with topLeft so need to add the node size to get the canvas exact size
        val nodeMaxSize = with(density) { nodes.maxBy { it.exactSizePx }.exactSizePx.toDp() }
        val canvasHeight = with(density) { points.second.toDp() + nodeMaxSize }
        val canvasWidth = with(density) { points.first.toDp() + nodeMaxSize }
        updateSize(canvasWidth, canvasHeight)
    }

    override fun showDialog() {
        show.update { true }
    }

    override fun dismiss() {
        show.update{false}
    }

}


/**
 * # Caution
 * -  Manage it own scroller so consumer should handle the nested scrolling otherwise can causes crash
 */
@Composable
internal fun Editor(controller: GraphEditorController) {
    val tag = "GraphEditor:_Editor"
    val nodes: Set<EditorNodeModel> = controller.nodes.collectAsState().value
    val edges = controller.edges.collectAsState().value
    var awayFromViewportX by rememberSaveable { mutableStateOf(0f) }
    val scrollbarSize = 20.dp
    val canvasController = remember(nodes, edges) { CanvasControllerImpl() }
    val density = LocalDensity.current
    val canvasSize = canvasController.size.collectAsState().value
    var show = canvasController.show.collectAsState().value
    LaunchedEffect(nodes, edges) {
        canvasController.sizeAccordingToContent(nodes.toList(), edges, density)
    }

    if (show && canvasSize != null) {
        DialogUI(
            canvasController = canvasController
        )
    }

    if (canvasSize == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(Modifier.size(64.dp))
        }
    } else {
        val contentWidth = canvasSize.first
        val contentHeight = canvasSize.second
        BoxWithConstraints(Modifier) {
            val viewportWidth = maxWidth
            val scrollableWidth = contentWidth - viewportWidth
            val scrollWidthPx = with(density) { scrollableWidth.toPx() }
            ScrollbarHorizontal(
                modifier = Modifier,
                scrollbarSize = scrollbarSize,
                viewportWidth = viewportWidth,
                awayFromViewportX = awayFromViewportX,
                onDrag = { amount ->
                    //x∈[wV −wC ,0]
                    awayFromViewportX = (awayFromViewportX + amount).coerceIn(-scrollWidthPx, 0f)
                },
                canvasSize = canvasSize,
                onSizeChanged = {
                    canvasController.showDialog()
                },
            )
            Logger.on(tag, "viewportWidth:$viewportWidth")
            Logger.on(tag, "canvasWidth:$contentWidth")
            Logger.on(tag, "scrollableWidth:$scrollableWidth")
            Logger.on(tag, "scrollWidthPx:$scrollWidthPx px")
            Logger.on(tag, "awayFromViewportX:$awayFromViewportX px")
            //Viewport
            Box(
                modifier = Modifier.padding(top = scrollbarSize)
                    .height(contentHeight)
                    .width(contentWidth)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { positionInViewPort ->
                                Logger.on(tag, "onTap:$positionInViewPort")
                                //enable only horizontal axis dragging
                                val positionInCanvas =
                                    positionInViewPort - Offset.Zero.copy(awayFromViewportX)
                                controller.onTap(positionInCanvas)
                            },
                            onDoubleTap = {
                                Logger.on(tag, "onDoubleTap")
                                controller.onDoubleTap()
                            }

                        )
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { positionInViewport ->
                                val positionInCanvas =
                                    positionInViewport - Offset.Zero.copy(x = awayFromViewportX)
                                controller.onDragStart(positionInCanvas)
                            },
                            onDrag = { _, dragAmount -> controller.onDrag(dragAmount) },
                            onDragEnd = { controller.dragEnd() }
                        )
                    }

            ) {
                CanvasView(
                    modifier = Modifier.width(contentWidth)
                        .height(contentHeight - scrollbarSize)
                        .background(Color.Gray),
                    topLeft = Offset.Zero.copy(x = awayFromViewportX),
                    nodes = nodes,
                    edges = edges,
                )

            }
        }

    }


}

@Composable
fun DialogUI(
    modifier: Modifier = Modifier,
    canvasController: CanvasController,
) {
    val canvasSize=canvasController.size.collectAsState().value
    canvasSize?.let {canvasSize->
        var value1 by remember { mutableStateOf("${canvasSize.first.value.toInt()}") }
        var value2 by remember { mutableStateOf("${canvasSize.second.value.toInt()}") }
        Dialog(
            onDismissRequest = canvasController::dismiss
        ) {
            Surface(
                shadowElevation = 8.dp
            ) {
                Column {
                    Row(
                        modifier = Modifier.height(300.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            modifier = Modifier.width(100.dp),
                            value = value1,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            onValueChange = {
                                value1=it
                            }
                        )
                        SpacerHorizontal(8)
                        TextField(
                            modifier = Modifier.width(100.dp),
                            value = value2,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            onValueChange = {
                                value2=it
                            }
                        )

                    }
                    SpacerVertical(16)
                    IconButton(
                        onClick = {
                            try {
                                val width = value1.toInt().dp
                                val height = value2.toInt().dp
                                if (width >= canvasSize.first && height >= canvasSize.second) {
                                    canvasController.updateSize(width, height)
                                    canvasController.dismiss()
                                }
                            } catch (_: Throwable) {
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DoneAll,
                            contentDescription = "done"
                        )
                    }
                }
            }
        }

    }

}

@Composable
private fun ScrollbarHorizontal(
    modifier: Modifier = Modifier,
    scrollbarSize: Dp,
    awayFromViewportX: Float,
    viewportWidth: Dp,
    onDrag: (Float) -> Unit,
    canvasSize: Pair<Dp, Dp>,
    onSizeChanged: () -> Unit
) {
    val density = LocalDensity.current
    val paddingLeftPx = with(density) { 32.dp.toPx() }
    val viewportWidthPx = with(density) { viewportWidth.toPx() }
    var showDialog by mutableStateOf(false)
    Box(
        modifier
            .fillMaxWidth()
            .height(scrollbarSize)
            .background(Color.White)
            .shadow(elevation = 8.dp)
            .pointerInput(Unit) {
                detectDragGestures(onDragStart = {},
                    onDrag = { _, dragAmount ->
                        onDrag(dragAmount.x)
                    },
                    onDragEnd = {

                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.width(20.dp).fillMaxHeight().background(Color.Red).clickable {
            onSizeChanged()
        })

    }

}

@Composable
private fun CanvasView(
    modifier: Modifier = Modifier,
    topLeft: Offset,
    nodes: Set<EditorNodeModel>,
    edges: List<EditorEdgeModel>
) {
    val textMeasurer = rememberTextMeasurer()
    val edgeWidth = with(LocalDensity.current) { 1.dp.toPx() }
    Box(modifier = modifier
        .offset { IntOffset(topLeft.x.toInt(), y = 0) }
        .drawBehind {
            try {
                //TODO:Since drawing, and can pass a saved graph but the device may have not space window to fit the drawing
                //in that case it will crash so avoid the app crashing,fix it later
                edges.forEach {
                    drawEdge(
                        hideControllerPoints = false,
                        it,
                        textMeasurer,
                        width = edgeWidth
                    )
                }
                nodes.forEach {
                    drawNode(it, textMeasurer)
                }
            } catch (_: Exception) {

            }
        }
    )
}

