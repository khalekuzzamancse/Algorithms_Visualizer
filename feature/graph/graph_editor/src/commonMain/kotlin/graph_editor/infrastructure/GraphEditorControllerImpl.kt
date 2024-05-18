package graph_editor.infrastructure

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import graph_editor.ui.component.VisualNode
import graph_editor.ui.component.node.GraphEditorNodeController
import graph_editor.domain.Edge
import graph_editor.domain.Graph
import graph_editor.ui.component.GraphEditorMode
import graph_editor.domain.GraphEditorController
import graph_editor.domain.GraphResult
import graph_editor.domain.Node
import graph_editor.domain.VisualEdgeModel
import graph_editor.domain.VisualGraphModel
import graph_editor.domain.VisualNodeModel
import graph_editor.ui.component.edge.GraphEditorEdgeController
import graph_editor.ui.component.VisualEdge
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID


/**
onTap:
Tapping support two operations:
1:Add a node to a to tapped location.
2:Select a control point of an edge
to distinguish which operation is running ,we have to keep track of the operation.
if the user clicked to add node button then change mode=AddNode
when onTap() executed if mode==AddNode then add the node,otherwise do something else.
after tap if mode==AddNode is on then change the mode.
 */


@Suppress("LocalVariableName", "FunctionName")
internal data class GraphEditorControllerImpl(
    private val density: Float,
) : GraphEditorController {
    private val nodeManger = GraphEditorNodeController(density)
    private val edgeManger = GraphEditorEdgeController()

    //Direction
    private  var undirected: Boolean =true

    override val edges: StateFlow<List<VisualEdge>>
        get() = edgeManger.edges
    override val nodes: StateFlow<Set<VisualNode>>
        get() = nodeManger.nodes

    private var operationMode = GraphEditorMode.None
    private var nextAddedVisualNode: VisualNode? = null


    //Edge and Node Deletion
    override val selectedNode = nodeManger.selectedNode

    override var selectedEdge = edgeManger.selectedEdge

    override fun onRemovalRequest() {
        nodeManger.removeNode()
        edgeManger.removeEdge()
    }

    override fun onDone(): GraphResult {
        return GraphResult(
            undirected = undirected,
            visualGraph = getVisualGraph(),
            graph = getGraph()
        )
    }


    override fun onDirectionChanged(undirected: Boolean) {
        this.undirected = undirected
        //set a initial demo graph
        setDemoGraph()
    }
    private fun setDemoGraph(){
        if (undirected){
            nodeManger.setInitialNode(SavedGraphProvider.nodes.toSet())
            edgeManger.setEdge(SavedGraphProvider.edges)
        }
        else{
            //for directed
            nodeManger.setInitialNode(SavedGraphProvider.getTopologicalSortDemoGraph().first.toSet())
            edgeManger.setEdge(SavedGraphProvider.getTopologicalSortDemoGraph().second)
        }
    }


    override fun onAddNodeRequest(label: String, nodeSizePx: Float) {
        nextAddedVisualNode = VisualNode(
            id = UUID.randomUUID().toString(),//So that guaranteed to be unique
            label = label,
            exactSizePx = nodeSizePx
        )
        operationMode = GraphEditorMode.NodeAdd
    }


    override fun onEdgeConstInput(cost: String?) {
        operationMode = GraphEditorMode.EdgeAdd
        edgeManger.addEdge(
            VisualEdge(
                id = UUID.randomUUID().toString(),
                start = Offset.Zero,
                end = Offset.Zero,
                control = Offset.Zero,
                cost = cost,
                minTouchTargetPx = 30.dp.value * density,
                undirected = undirected,
            )
        )
    }

    override fun onTap(tappedPosition: Offset) {
        nodeManger.observeCanvasTap(tappedPosition)
        when (operationMode) {
            GraphEditorMode.NodeAdd -> {
                addNode(tappedPosition)
                operationMode = GraphEditorMode.None
            }

            else -> {
                edgeManger.onTap(tappedPosition)
            }
        }
    }


    override fun onDragStart(startPosition: Offset) = edgeManger.onDragStart(startPosition)


    override fun onDrag(dragAmount: Offset) {
        nodeManger.onDragging(dragAmount)
        edgeManger.dragOngoing(dragAmount, dragAmount)
    }

    override fun dragEnd() = edgeManger.dragEnded()


    private fun addNode(position: Offset) {
        nextAddedVisualNode?.let {
            val radius = it.exactSizePx / 2
            nodeManger.add(it.copy(topLeft = position - Offset(radius, radius)))
        }
    }

    private fun getVisualGraph(): VisualGraphModel {
        val _nodes = nodes.value.map { it._toVisualModel() }.toSet()
        val _edges = edges.value.map { it._toVisualModel() }.toSet()
        return VisualGraphModel(_nodes, _edges)
    }

    override fun getGraph(): Graph {
//        nodes.value.forEach {
//            log(it.toString())
//        }
//        edges.value.forEach {
//            log(it.toString())
//        }
        val _edges = makeEdges()
        val _nodes = makeNodes()
        return Graph(undirected = undirected,nodes = _nodes, edges = _edges)
    }

    //TODO:Helper method --- Helper method --- Helper method --- Helper method --- Helper method --- Helper method --- Helper method
//TODO:Helper method --- Helper method --- Helper method --- Helper method --- Helper method --- Helper method --- Helper method
//TODO:Helper method --- Helper method --- Helper method --- Helper method --- Helper method --- Helper method --- Helper method

    private fun makeNodes(): Set<Node> {
        val _nodes = mutableSetOf<Node>()
        nodes.value.forEach { node ->
            _nodes.add(Node(node.id, node.label))
        }
        return _nodes.toSet() //returning immutable copy,to avoid side effect
    }

    /**
     * - From the visual edges making the set of edges(u,v),so that using it , make  a graph adjacency list
     */

    private fun makeEdges(): Set<Edge> {
        val _edges = mutableSetOf<Edge>()
        edges.value.forEach { edge ->
            val u =
                nodes.value.find { node -> node.isInsideNode(edge.start) }//u=start node where the node start point is
            val v =
                nodes.value.find { node -> node.isInsideNode(edge.end) } //v=end node where edge endpoint is

            val isValidEdge = (u != null && v != null)
            if (isValidEdge) {
                _edges.add(
                    Edge(
                        Node(u!!.id, u.label),
                        Node(v!!.id, v.label)
                    )
                )
            }
        }
        return _edges.toSet() //returning immutable copy,to avoid side effect
    }

    private fun VisualNode._toVisualModel() = VisualNodeModel(
        id = id,
        label = label,
        topLeft = topLeft,
        sizePx = exactSizePx
    )

    private fun VisualEdge._toVisualModel() = VisualEdgeModel(
        id = id,
        start = start,
        end = end,
        control = control,
        cost = cost
    )

    @Suppress("Unused")
    private fun log(message: String, methodName: String? = null) {
        val tag = "${this@GraphEditorControllerImpl::class.simpleName}Log:"
        val method = if (methodName == null) "" else "$methodName()'s "
        val msg = "$method:-> $message"
        println("$tag:: $msg")
    }

}