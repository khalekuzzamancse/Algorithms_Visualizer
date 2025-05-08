package core.ui.graph.viewer.controller

import androidx.compose.ui.graphics.Color
import core.ui.graph.common.Constants
import core.ui.graph.common.model.EditorEdgeMode
import core.ui.graph.common.model.EditorNodeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * - Client should not create direct instance of it to avoid coupling
 * - Instead client should use DI container to get the instance with is abstraction
 */
class GraphViewerControllerImpl internal constructor(
    nodes: Set<EditorNodeModel>,
    edges: Set<EditorEdgeMode>
) : GraphViewerController() {


    private val canvasUtils = CanvasUtils(nodes, edges).trimExtraSpace().calculateCanvasSize()
    private val _nodes = MutableStateFlow(canvasUtils.nodes)
    private val _edges = MutableStateFlow(canvasUtils.edges)
    private val nodColor = Constants.nodeColor
    private val edgeColor = Constants.edgeColor
    override val canvasSize = canvasUtils.canvasSize
    override val nodes: StateFlow<Set<EditorNodeModel>> = _nodes.asStateFlow()
    override val edges: StateFlow<Set<EditorEdgeMode>> = _edges.asStateFlow()
    private val baseColors = mutableMapOf<String, Color>()
    private var blinkingJob: Job? = null
    private var blinkingNodeId: String? = null
    private var previousNodeColor: Color? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun updateDistance(id: String, distance: String) {
        _nodes.update { nodes ->
            nodes.map { node ->
                if (node.id == id) {
                    node.copy(distance = distance)
                } else {
                    node
                }
            }.toSet()
        }
    }

    override fun getNodesById(ids: List<String>) =
        nodes.value
            .filter { node -> node.id in ids }
            .map { Pair(it.id, it.label) }


    override fun changeNodeColor(id: String, color: Color) {
        if (blinkingNodeId == id) {
            baseColors[id] = color
        }
        _nodes.update { nodes ->
            nodes.map { node ->
                if (node.id == id)
                    node.copy(color = color)
                else node
            }.toSet()
        }
    }

    override fun changeEdgeColor(id: String, color: Color) {
        _edges.update { edges ->
            edges.map { edge ->
                if (edge.id == id) {
                    edge.copy(pathColor = color)
                } else edge
            }.toSet()
        }
    }

    override fun resetAllNodeColor() {
        _nodes.update { nodes ->
            nodes.map { node ->
                node.copy(color = nodColor)
            }.toSet()
        }
    }

    override fun resetAllEdgeColor() {
        _edges.update { edges ->
            edges.map { edge ->
                edge.copy(pathColor = edgeColor)
            }.toSet()
        }
    }


    override fun blinkNode(id: String) {
        // Stop any currently blinking node
        blinkingJob?.cancel()
        blinkingJob = null

        blinkingNodeId?.let { previousBlinkingId ->
            // Restore the color of the previously blinking node to its base color
            val baseColor = baseColors[previousBlinkingId] ?: nodColor
            changeNodeColor(previousBlinkingId, baseColor)
            baseColors.remove(previousBlinkingId)
        }

        blinkingNodeId = id

        // Store the current color of the node as its base color
        val currentNode = nodes.value.find { it.id == id }
        val currentColor = currentNode?.color ?: nodColor
        baseColors[id] = currentColor

        // Start blinking the new node
        blinkingJob = scope.launch {
            val colors = listOf(
                Color.Yellow,
                Color.Green,
                Color.Blue,
                Color.Red,
                Color.Magenta
            ) // Colors to blink between
            while (isActive) {
                // Randomly select a color different from the base color
                val randomColor = colors.filter { it != baseColors[id] }.random()
                // Change the node's color to the blinking color
                _nodes.update { nodes ->
                    nodes.map { node ->
                        if (node.id == id)
                            node.copy(color = randomColor)
                        else node
                    }.toSet()
                }
                // Wait for some time
                delay(500) // Adjust the delay duration as needed

                // Restore the node's color to the base color between blinks
                _nodes.update { nodes ->
                    nodes.map { node ->
                        if (node.id == id)
                            node.copy(color = baseColors[id] ?: nodColor)
                        else node
                    }.toSet()
                }
                delay(500)
            }
        }
    }

    override fun stopBlinkAll() {
        blinkNode("")//dummy id to just stop blink the existing
    }

    override fun reset() {
        resetAllNodeColor()
        resetAllEdgeColor()
        stopBlinkAll()
    }

    override fun filterEdgeByColor(color: Color) {
        _edges.update { edges ->
            edges.filter { it.pathColor == color }.toSet()
        }
    }


}