package core.ui.graph.editor.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import core.lang.Logger
import core.ui.graph.common.drawEdge
import core.ui.graph.common.drawNode
import core.ui.graph.editor.controller.GraphEditorController


/**
 * # Caution
 * -  Manage it own scroller so consumer should handle the nested scrolling otherwise can causes crash
 */
@Composable
internal fun Editor(
    controller: GraphEditorController,
) {
    val tag="GraphEditor:_Editor"
    val textMeasurer = rememberTextMeasurer()
    val nodes = controller.nodes.collectAsState().value
    val edges = controller.edges.collectAsState().value
    val edgeWidth = with(LocalDensity.current) { 1.dp.toPx() }


    //In case of touch device at a time either scroll will detect or dragging
    //That is why need to manage scroll and drag separately
    //In order to use scrolling need to give a fix size to the canvas otherwise causes render issue
    //Because these drawing are not affecting the layout phase , they are affecting only drawing phase

    //Calculating exact size that need to render the node and parent
    //This is helpful when there is initial graph set
    //Is there is already no edge or node in the graph the canvasUtils should return a default min size
    //so that graph can draw here..
    val canvasUtils = remember(nodes, edges) {
        getMaxXY(nodes = nodes.map { it.topLeft },
            starts = edges.map { it.start }, ends = edges.map { it.end },
            controls = edges.map { it.control })
//   CanvasUtils(nodes, edges.toSet()).trimExtraSpace().calculateCanvasSize()
    }
    val density = LocalDensity.current
    val nodeMaxSize = remember { 64.dp }
    val extra = remember { 20.dp }//possible that edge cost at end and after the node or control point
    //Dealing with topLeft so need to add the node size to get the canvas exact size
    val canvasHeight = remember(nodes, edges) { with(density) { canvasUtils.second.toDp() + nodeMaxSize + extra } }
    val canvasWidth = remember(nodes, edges) { with(density) { canvasUtils.first.toDp() + nodeMaxSize + extra } }
    val selectionMode = controller.selectedNode.collectAsState().value != null || controller.selectedEdge.collectAsState().value != null

    //At a  time either tap or drag detector will work, unable to detect drag and tap at a time
    //that is why need to use the select mode
    //if a node or edge is selected in that case tap(single or double) will not work so to clear the selection use a difference approach

    val modifier=  Modifier
        .fillMaxSize()
        .pointerInput(selectionMode){
        if(!selectionMode){
            Logger.on(tag,"Non Selection mode")
            detectTapGestures(
                onTap = { touchedPosition ->
                    Logger.on(tag,"onTap")
                    controller.onTap(touchedPosition)

                },
                onDoubleTap = {
                    Logger.on(tag,"onDoubleTap")
                    controller.onDoubleTap()
                }
            )
        }
        else{
            Logger.on(tag," Selection mode")
            detectDragGestures(
                onDragStart = {
                    Logger.off(tag,"onDragStart")
                    controller.onDragStart(it)
                },
                onDrag = { _, dragAmount ->
                    Logger.off(tag,"onDrag")
                    controller.onDrag(dragAmount)
                },
                onDragEnd = {
                    Logger.off(tag,"onDragEnd")
                    controller.dragEnd()
                }
            )
        }



    }


    Canvas(modifier =
    Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = { touchedPosition ->
                    Logger.on(tag,"onTap")
                    controller.onTap(touchedPosition) //adding the node on tap
                },
                onDoubleTap = {
                    Logger.on(tag,"onDoubleTap")
                    controller.onDoubleTap()
                }

                )
        }
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = {
                    controller.onDragStart(it)
                },
                onDrag = { _, dragAmount ->

                    controller.onDrag(dragAmount)
                },
                onDragEnd = {

                    controller.dragEnd()
                }
            )
        }
//        .width(canvasWidth) //TODO:Careful can may crashes,directly use padding can cause crashes
//        .height(canvasHeight) //TODO:Careful may causes crashes
        //TODO:Find reason why graph not render if use size(height,weight) modifier before scroll modifier
//                    Modifier.horizontalScroll(rememberScrollState())
//                        .verticalScroll(rememberScrollState())
        //TODO:Can cases crash if current window size is less than this size
      //  .background(Color.Gray)
    ) {

        try {
            //TODO:Since drawing, and can pass a saved graph but the device may have not space window to fit the drawing
            //in that case it will crash so avoid the app crashing,fix it later
            edges.forEach {
                drawEdge(hideControllerPoints = false,it, textMeasurer, width = edgeWidth)
            }
            nodes.forEach {
                drawNode(it, textMeasurer)
            }
        } catch (_: Exception) {

        }
    }
}
