@file:Suppress("functionName")

package core.ui.graph

import core.ui.graph.common.model.EditorEdgeModel
import core.ui.graph.common.model.EditorNodeModel
import core.ui.graph.editor.controller.GraphEditorController
import core.ui.graph.editor.factory.GraphEditorControllerImpl
import core.ui.graph.editor.factory.GraphProvider
import core.ui.graph.editor.factory.InputControllerImpl
import core.ui.graph.editor.factory.SavedGraphProvider
import core.ui.graph.viewer.controller.GraphViewerController
import core.ui.graph.viewer.controller.GraphViewerControllerImpl


object GraphFactory {

    internal fun createGraphViewerController(
        nodes: Set<EditorNodeModel>,
        edges: Set<EditorEdgeModel>
    ): GraphViewerController = GraphViewerControllerImpl(nodes, edges)

    internal fun createGraphEditorController(
        density: Float,
        initialGraph: Pair<List<EditorNodeModel>, List<EditorEdgeModel>> = Pair(
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