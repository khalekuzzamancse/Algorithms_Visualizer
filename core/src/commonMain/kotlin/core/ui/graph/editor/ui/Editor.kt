package core.ui.graph.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.outlined.SwipeLeft
import androidx.compose.material.icons.outlined.SwipeRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import core.lang.Logger
import core.ui.SpacerHorizontal
import core.ui.SpacerVertical
import core.ui.core.CustomTextField
import core.ui.graph.common.drawEdge
import core.ui.graph.common.drawNode
import core.ui.graph.common.model.EditorEdgeModel
import core.ui.graph.common.model.EditorNodeModel
import core.ui.graph.editor.controller.GraphEditorController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        density: Density,
    )

    fun showDialog()
    fun dismiss()
}

class CanvasControllerImpl : CanvasController {
    private val scope = CoroutineScope(Dispatchers.Default)
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
        scope.launch {
            size.update { null }
            delay(500) //without delay state update not trigger new UI
            size.update {
                it?.copy(first = width, second = height) ?: Pair(width, height)
            }
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
        Logger.on("GraphEditor", "with nodes and edges")
    }

    override fun showDialog() {
        show.update { true }
    }

    override fun dismiss() {
        show.update { false }
    }

}


/**
 * # Caution
 * -  Manage it own scroller so consumer should handle the nested scrolling otherwise can causes crash
 */
@Composable
internal fun Editor(
    controller: GraphEditorController,
    contentPaddingTop: Dp=4.dp,
    contentPaddingLeft: Dp=4.dp
) {
    val tag = "GraphEditor:_Editor"
    val nodes: Set<EditorNodeModel> = controller.nodes.collectAsState().value
    val edges = controller.edges.collectAsState().value
    var awayFromViewportX by rememberSaveable { mutableStateOf(0f) }
    val scrollbarSize = 30.dp
    val canvasController = controller.canvasController
    val density = LocalDensity.current
    val canvasSize = canvasController.size.collectAsState().value
    val show = canvasController.show.collectAsState().value
    LaunchedEffect(Unit) {
        //don't depends on node and edges because these during new node add will cause problem
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
        val contentWidth = canvasSize.first+contentPaddingLeft////since left are shifted down, so to avoid right overlap
        val contentHeight = canvasSize.second+contentPaddingTop//since top are shifted down, so to avoid bottom overlap
        BoxWithConstraints(Modifier) {
            val viewportWidth = maxWidth
            val scrollableWidth = contentWidth - viewportWidth
            val scrollWidthPx = with(density) { scrollableWidth.toPx() }
            ScrollbarHorizontal(
                modifier = Modifier,
                scrollbarSize = scrollbarSize,
                onDrag = { amount ->
                    //x∈[wV −wC ,0]
                    awayFromViewportX = (awayFromViewportX + amount).coerceIn(-scrollWidthPx, 0f)
                },
            )
            //Viewport
            Box(
                modifier = Modifier.padding(top = scrollbarSize)
                    .height(contentHeight)
                    .width(contentWidth)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { positionInViewPort ->
                                Logger.on(tag, "onTap, positionInViewport:$positionInViewPort")
                                //enable only horizontal axis dragging
                                val positionInCanvas =
                                    positionInViewPort - Offset.Zero.copy(
                                        x = awayFromViewportX,
                                        y = 0f
                                    )
                                Logger.on(tag, "onTap, awayFromViewportX:$awayFromViewportX")
                                Logger.on(tag, "onTap position in canvas:$positionInCanvas")
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
                    modifier = Modifier
                        .width(contentWidth)
                        .height(contentHeight - scrollbarSize)
                        .shadow(
                            elevation = .5.dp,
                            ambientColor = Color.White,
                            clip = false
                        ),
                    contentPaddingTop = contentPaddingTop,
                    contentPaddingLeft = contentPaddingLeft,
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
    canvasController: CanvasController,
) {
    val canvasSize = canvasController.size.collectAsState().value
    canvasSize?.let { size ->
        var value1 by remember { mutableStateOf("${size.first.value.toInt()}") }
        var value2 by remember { mutableStateOf("${size.second.value.toInt()}") }
        Dialog(
            onDismissRequest = canvasController::dismiss
        ) {
            Surface(
                shadowElevation = 8.dp,
                modifier = Modifier.widthIn(max = 450.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Canvas Size(Width, Height)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400
                    )
                    SpacerVertical(16)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CustomTextField(
                            modifier = Modifier.width(80.dp),
                            label = "Width",
                            value = value1,
                            textAlign = TextAlign.Center,
                            keyboardType = KeyboardType.Number,
                            onValueChange = {
                                value1 = it
                            }
                        )
                        CustomTextField(
                            modifier = Modifier.width(80.dp),
                            textAlign = TextAlign.Center,
                            label = "Height",
                            value = value2,
                            keyboardType = KeyboardType.Number,
                            onValueChange = {
                                value2 = it
                            }
                        )
                        Icon(
                            imageVector = Icons.Filled.DoneAll,
                            contentDescription = "done",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable {
                                try {
                                    val width = value1.toInt()
                                    val height = value2.toInt()
                                    //TODO: Force to  min size
                                    canvasController.updateSize(width.dp, height.dp)
                                    canvasController.dismiss()
                                } catch (_: Throwable) {
                                }
                            }
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
    onDrag: (Float) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(scrollbarSize)
            .background(Color(0xFFE0E0E0), RoundedCornerShape(4.dp)) // Track color
            .shadow(
                elevation = 2.dp,
                ambientColor = Color.Black.copy(alpha = 0.1f),
                shape = RoundedCornerShape(4.dp)
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {},
                    onDrag = { _, dragAmount ->
                        onDrag(dragAmount.x)
                    },
                    onDragEnd = {}
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Row(modifier=Modifier.padding(2.dp).fillMaxHeight()) {
            Icon(
                imageVector = Icons.Outlined.SwipeLeft,
                contentDescription = "drag left"
            )
            SpacerHorizontal(16)
            Icon(
                imageVector = Icons.Outlined.SwipeRight,
                contentDescription = "drag left"
            )

        }
    }

}
/**
 * @param modifier , do not give padding instead use [contentPaddingTop] and [contentPaddingLeft] to avoid
 * cut-out effect during scrolling
 */
@Composable
private fun CanvasView(
    modifier: Modifier = Modifier,
    topLeft: Offset,
    nodes: Set<EditorNodeModel>,
    edges: List<EditorEdgeModel>,
    contentPaddingTop: Dp=4.dp,
    contentPaddingLeft: Dp=4.dp

) {
    val textMeasurer = rememberTextMeasurer()
    val edgeWidth = with(LocalDensity.current) { 1.dp.toPx() }
    val density = LocalDensity.current
    val topPaddingPx= with(density){contentPaddingTop.toPx()}
    val leftPaddingPx= with(density){contentPaddingLeft.toPx()}
    Box(modifier = modifier
        .offset { IntOffset(topLeft.x.toInt(), y = 0) }
        .drawBehind {
            translate(
                top = topPaddingPx,
                left = leftPaddingPx
            ) {
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

        }
    )
}

