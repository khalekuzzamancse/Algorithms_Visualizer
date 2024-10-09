@file:Suppress("functionName")

package graph.graph.editor.factory

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import graph.graph.editor.model.GraphEditorMode
import graph.graph.GraphFactory
import graph.graph.common.model.Edge
import graph.graph.common.model.EditorEdgeMode
import graph.graph.common.model.EditorNodeModel
import graph.graph.common.model.GraphResult
import graph.graph.common.model.Node
import graph.graph.editor.controller.GraphEditorController
import kotlinx.coroutines.flow.StateFlow


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


@Suppress("LocalVariableName")
internal data class GraphEditorControllerImpl(
    override val inputController: GraphEditorController.InputController,
    private val density: Float,
    private val initialGraph:Pair<List<EditorNodeModel>, List<EditorEdgeMode>> =Pair(emptyList(), emptyList()),
) : GraphEditorController {
    private val nodeManger = GraphEditorNodeController(density)
    private val edgeManger = GraphEditorEdgeController()
    private var edgeId: Int = 1

    init {
        inputController.drawNodeObserver = ::_onAddNodeRequest
        inputController.addEdgeRequestObserver = this::_onDrawEdgeRequest
        inputController.graphTypeObserver = {
            setDemoGraph()
        }
    }

    private fun _onAddNodeRequest(label: String, nodeSizePx: Float) {
        nextAddedEditorNodeModel = EditorNodeModel(
            id = label,//So that guaranteed to be unique
            label = label,
            exactSizePx = nodeSizePx
        )
        operationMode = GraphEditorMode.NodeAdd

    }


    private fun _onDrawEdgeRequest(cost: String?) {
        operationMode = GraphEditorMode.EdgeAdd
        edgeManger.addEdge(
            EditorEdgeMode(
                id = edgeId++.toString(),
                start = Offset.Zero,
                end = Offset.Zero,
                control = Offset.Zero,
                cost = cost,
                minTouchTargetPx = 30.dp.value * density,
                directed = inputController.isDirected(),
            )
        )
    }


    override var selectedEdge = edgeManger.selectedEdge


    override val edges: StateFlow<List<EditorEdgeMode>>
        get() = edgeManger.edges
    override val nodes: StateFlow<Set<EditorNodeModel>>
        get() = nodeManger.nodes

    private var operationMode = GraphEditorMode.None
    private var nextAddedEditorNodeModel: EditorNodeModel? = null


    //Edge and Node Deletion
    override val selectedNode = nodeManger.selectedNode


    override fun onRemovalRequest() {
        nodeManger.removeNode()
        edgeManger.removeEdge()
    }

    override fun onGraphInputCompleted(): GraphResult {

//        println(nodes.value)
//        println()
//        println(edges.value)

        return GraphResult(
            directed = inputController.isDirected(),
            controller = GraphFactory
                .createGraphViewerController(
                    nodes.value.toSet(),
                    edges.value.map { it.copy(directed = inputController.isDirected()) }.toSet()
                ),
            nodes = makeNodes(),
            edges = makeEdges()
        )
    }


    private fun setDemoGraph() {
        nodeManger.setInitialNode(initialGraph.first.toSet())
        edgeManger.setEdge(initialGraph.second)
//        if (undirected){
//            nodeManger.setInitialNode(SavedGraphProvider.nodes.toSet())
//            edgeManger.setEdge(SavedGraphProvider.edges)
//        }
//        else{
//            //for directed
//            nodeManger.setInitialNode(SavedGraphProvider.getDijkstraGraph().first.toSet())
//            edgeManger.setEdge(SavedGraphProvider.getDijkstraGraph().second)
//        }
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
        nextAddedEditorNodeModel?.let {
            val radius = it.exactSizePx / 2
            nodeManger.add(it.copy(topLeft = position - Offset(radius, radius)))
        }
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
                        id = edge.id,
                        from = Node(u!!.id, u.label),
                        to = Node(v!!.id, v.label),
                        cost = edge.cost
                    )
                )
            }
        }
        return _edges.toSet() //returning immutable copy,to avoid side effect
    }

//    private fun EditorNodeModel._toDrawableNode() = ViewerNodeModel(
//        id = id,
//        label = label,
//        topLeft = topLeft,
//        sizePx = exactSizePx
//    )
//
//    private fun EditorEdgeModel._toDrawableEdge() = ViewerEdgeModel(
//        id = id,
//        start = start,
//        end = end,
//        control = control,
//        cost = cost
//    )

    @Suppress("Unused")
    private fun log(message: String, methodName: String? = null) {
        val tag = "${this@GraphEditorControllerImpl::class.simpleName}Log:"
        val method = if (methodName == null) "" else "$methodName()'s "
        val msg = "$method:-> $message"
        println("$tag:: $msg")
    }

}