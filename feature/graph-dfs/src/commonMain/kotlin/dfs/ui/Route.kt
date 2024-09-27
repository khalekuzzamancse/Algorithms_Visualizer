package dfs.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dfs.di_containter.BFSFactory
import dfs.domain.AlgorithmicGraph
import dfs.domain.AlgorithmicNode
import dfs.domain.LineForPseudocode
import dfs.domain.SimulationState
import dfs.infrastructure.AlgoSimulatorImpl
import graphviewer.domain.GraphViewerController
import graphviewer.ui.viewer.GraphViewer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import layers.ui.common_ui.pseudocode.CodeLine
import layers.ui.common_ui.pseudocode.PseudoCodeExecutor
import kotlin.random.Random


@Composable
fun DFSSimulation() {
    var canvasSizePx by remember { mutableStateOf(Size(0f, 0f)) }
    var controller by remember { mutableStateOf<SimulatorController?>(null) }
    if (controller == null)//if input mode
    {
        GraphInput(
            onGraphInput = { graphViewerController ->
                val graph = BFSFactory.getGraph()
                val source = graph.nodes.first()
                controller = SimulatorController(
                    graphViewerController = graphViewerController,
                    graph = graph,
                    source = source
                )
            },
            canvasSize = {
                canvasSizePx = it
            }
        )
    } else {
        controller?.let {
            _Simulator(canvasSizePx, it)
        }
    }
}

@Composable
private fun _Simulator(
    canvasSizePx: Size,
    controller: SimulatorController,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            controller.onNext()
        }) {
            Text("Next")
        }
        _GraphSimulationSection(controller.graphViewerController, canvasSizePx)
        _PseudoCodeSection(controller.pseudocode)

    }

}


@Composable
private fun _GraphSimulationSection(
    controller: GraphViewerController,
    canvasSizePx: Size,
) {
    val density = LocalDensity.current
    Box(
        Modifier
            .width(with(density) { canvasSizePx.width.toDp() })
            .height(with(density) { canvasSizePx.height.toDp() })
            .background(Color.Green.copy(alpha = 0.3f))
    ) {
        GraphViewer(controller)
    }
}

@Composable
private fun _PseudoCodeSection(
    code: List<LineForPseudocode>
) {
    PseudoCodeExecutor(
        modifier = Modifier.padding(8.dp),
        code = code.map {
            CodeLine(
                line = it.line,
                highLighting = it.highLighting,
                debugText = it.debuggingText,
                lineNumber = 0
            )
        }
    )
}

internal class SimulatorController(
    val graphViewerController: GraphViewerController,
    graph: AlgorithmicGraph,
    source: AlgorithmicNode
) {
    val iterator = AlgoSimulatorImpl(graph, source)
    var pseudocode by mutableStateOf<List<LineForPseudocode>>(emptyList())
        private set

    /** keep track which is highlighting or blinking so that ,can stop blink later */
    private var blinkingNode: AlgorithmicNode? = null

    private val visitedColor = Color.Blue

    fun onNext() {
        when (val state = iterator.next()) {
            is SimulationState.Running -> {
                val processingNode = state.processingNode
                if (processingNode != null) {
                    val id = processingNode.id
                    graphViewerController.changeNodeColor(id = id, color = visitedColor)

                    state.peekNode?.let {peekOfStack->
                        blinkNode(peekOfStack)
                    }

                }
                pseudocode = state.pseudocode

            }

            else -> {
                //   viewerController.resetAllNodeColor()
            }
        }
    }

    private fun blinkNode(node: AlgorithmicNode){
        CoroutineScope(Dispatchers.Default).launch {
            clearBlinking()
            blinkingNode=node
            while (blinkingNode!=null){
                blinkingNode?.let {
                    graphViewerController.changeNodeColor(it.id,getRandomColor())
                }
                delay(500) //0.5 sec
            }
        }
    }

    private fun getRandomColor(): Color {
        val random = Random.Default
        val r = random.nextInt(256)
        val g = random.nextInt(256)
        val b = random.nextInt(256)
        return Color(r,g,b)
    }

    /** Reset it color as visited color */
    private fun clearBlinking() {
        blinkingNode?.let {
            graphViewerController.changeNodeColor(id = it.id, color = visitedColor)
        }
        blinkingNode=null
    }
}