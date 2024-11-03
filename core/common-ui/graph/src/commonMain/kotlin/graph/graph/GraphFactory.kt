@file:Suppress("functionName")

package graph.graph

import graph.graph.common.model.EditorEdgeMode
import graph.graph.common.model.EditorNodeModel
import graph.graph.editor.controller.GraphEditorController
import graph.graph.editor.factory.GraphEditorControllerImpl
import graph.graph.editor.factory.GraphProvider
import graph.graph.editor.factory.InputControllerImpl
import graph.graph.editor.factory.SavedGraphProvider
import graph.graph.viewer.controller.GraphViewerController
import graph.graph.viewer.controller.GraphViewerControllerImpl

object GraphFactory {

    internal fun createGraphViewerController(
        nodes: Set<EditorNodeModel>,
        edges: Set<EditorEdgeMode>
    ): GraphViewerController = GraphViewerControllerImpl(nodes, edges)

    internal fun createGraphEditorController(
        density: Float,
        initialGraph: Pair<List<EditorNodeModel>, List<EditorEdgeMode>> = Pair(
            emptyList(),
            emptyList()
        ),
    ): GraphEditorController =
        GraphEditorControllerImpl(
            density = density,
            initialGraph = initialGraph,
            inputController = InputControllerImpl(
                drawNodeObserver = { _, _ -> },
                addEdgeRequestObserver = {},
                graphTypeObserver = {}
            )
        )

    fun getDemoGraph(density: Float) = GraphProvider(density = density).getDemoGraph()
    fun getMSTDemoGraph() = SavedGraphProvider.getMSTGraph()
    fun getTopologicalSortDemoGraph() = SavedGraphProvider.getTopologicalSortDemoGraph()

}