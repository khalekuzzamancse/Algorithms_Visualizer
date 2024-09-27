package bfs.ui


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
import bfs.di_containter.BFSFactory
import bfs.domain.AlgorithmicGraph
import bfs.domain.AlgorithmicNode
import bfs.domain.LineForPseudocode
import bfs.domain.SimulationState
import bfs.infrastructure.AlgoSimulatorImpl
import graphviewer.domain.GraphViewerController
import graphviewer.ui.viewer.GraphViewer
import layers.ui.common_ui.pseudocode.CodeLine
import layers.ui.common_ui.pseudocode.PseudoCodeExecutor

internal class SimulatorController(
    val viewerController: GraphViewerController,
    graph: AlgorithmicGraph,
    source: AlgorithmicNode
) {
    val iterator = AlgoSimulatorImpl(graph, source)
    var pseudocode by mutableStateOf<List<LineForPseudocode>>(emptyList())
        private set

    fun onNext() {
        when (val state = iterator.next()) {
            is SimulationState.Running -> {
                val processingNode = state.processingNode
                if (processingNode != null) {
                    val id = processingNode.id
                    viewerController.changeNodeColor(id = id, color = Color.Blue)
                }
                pseudocode = state.pseudocode
                println("Log:${pseudocode.mapNotNull { it.debuggingText }}")
            }

            else -> {
                //   viewerController.resetAllNodeColor()
            }
        }
    }
}

@Composable
fun BFSSimulation() {

    var canvasSizePx by remember { mutableStateOf(Size(0f, 0f)) }
    var controller by remember { mutableStateOf<SimulatorController?>(null) }
    if (controller == null)//if input mode
    {
        GraphInput(
            onGraphInput = { graphViewerController ->
                val graph = BFSFactory.getGraph()
                val source = graph.nodes.first()
                controller = SimulatorController(
                    viewerController = graphViewerController,
                    graph = graph,
                    source = source
                )
            },
            canvasSize = {
                canvasSizePx = it
            }
        )
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Button(onClick = {
                controller?.onNext()
            }) {
                Text("Next")
            }
            controller?.let {
                _GraphSimulationSection(it.viewerController, canvasSizePx)
                _PseudoCodeSection(it.pseudocode)
            }

        }


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

