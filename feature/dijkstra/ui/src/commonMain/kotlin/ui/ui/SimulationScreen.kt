package ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
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
import di.DiContainer
import domain.model.NodeModel.Companion.INFINITY
import domain.model.SimulationState
import domain.service.Simulator
import graphviewer.domain.GraphViewerController
import graphviewer.ui.viewer.GraphViewer

@Composable
fun DijkstraSimulationScreen(modifier: Modifier = Modifier) {
    var canvasSizePx by remember { mutableStateOf(Size(0f, 0f)) }
    var graphController by remember { mutableStateOf<GraphViewerController?>(null) }
    var simulator by remember { mutableStateOf<Simulator?>(null) }

    if (graphController == null)//if input mode
    {
        GraphInput(
            onGraphInput = {
                graphController = it
            },
            canvasSize = {
                canvasSizePx = it
            },
            onGraphReady = {
                simulator = DiContainer.createSimulator(it)
            }
        )
    }
    graphController?.let { viewerController ->
        Column {
            Button(
                onClick = {
                    simulator?.let { simulator ->
                        when (val state = simulator.next()) {
                            is SimulationState.ProcessingNode -> {
                                val node = state.node
                                println("Processing Node: ${state.node.label}")
                                viewerController.changeNodeColor(id = node.id, color = Color.Blue)

                            }

                            is SimulationState.ProcessingEdge -> {
                                val edge = state.edge
                                viewerController.changeEdgeColor(id = edge.id, color = Color.Green)
                                println("Processing Edge: ${state.edge}: ${edge.id}")
                            }

                            is SimulationState.DistanceUpdated -> {
                                state.nodes.forEach {
                                    val distance=if(it.distance == INFINITY)"∞" else it.distance.toString()
                                    viewerController.updateDistance(
                                        id = it.id,
                                        distance = distance
                                    )
                                }

                                println("Distance Updated: ${state.nodes.joinToString { it.toString() }}")
                            }

                            SimulationState.Finished -> {
                                println("Simulation Finished")
                            }

                            else -> {}
                        }

                    }
                }
            ) {
                Text("Next")
            }
            _GraphViewer(viewerController, canvasSizePx)
        }

    }


}

@Composable
private fun _GraphViewer(
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
