@file:Suppress("functionName")

package core_ui.graph

import core_ui.graph.common.model.EditorEdgeMode
import core_ui.graph.common.model.EditorNodeModel
import core_ui.graph.editor.controller.GraphEditorController
import core_ui.graph.editor.factory.GraphEditorControllerImpl
import core_ui.graph.editor.factory.GraphProvider
import core_ui.graph.editor.factory.InputControllerImpl
import core_ui.graph.editor.factory.SavedGraphProvider
import core_ui.graph.viewer.controller.GraphViewerController
import core_ui.graph.viewer.controller.GraphViewerControllerImpl


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