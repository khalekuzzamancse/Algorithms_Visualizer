package core.ui.graph.editor.factory

import androidx.compose.ui.geometry.Offset
import core.ui.graph.common.Constants
import core.ui.graph.common.model.*
import core.ui.graph.editor.model.*

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class GraphEditorEdgeController {
    private val _edges= MutableStateFlow<List<EditorEdgeModel>>(emptyList())
    private val _selectedEdge = MutableStateFlow<EditorEdgeModel?>(null)
    private var newlyAdding = false

    val selectedEdge = _selectedEdge.asStateFlow()
    val edges = _edges.asStateFlow()

    fun setInitialEdge(edge: Set<EditorEdgeModel>) {
        _edges.update { edge.toList() }
    }

    fun addEdge(edge: EditorEdgeModel) {
        _edges.value = edges.value + edge.copy(selectedPoint = EdgePoint.End)
        _selectedEdge.value = edge.copy(selectedPoint = EdgePoint.End)
        newlyAdding = true
    }
    /*
    Observing when the canvas is tapped so that:
    Select a point of the edge to edit it.
    Select the edge to remove it.
     */
    fun onTap(tappedPosition: Offset) {
        val tapListener = EdgeSelectionControllerImpl(_edges.value, tappedPosition)
        val tapped=tapListener.findSelectedEdgeOrNull()?:return

        //remove selection that are already selected,if tapped on empty space selection will be removed
         clearSelection()
        _selectedEdge.update { tapped }
        _edges.update { tapListener.getEdgesWithSelection() }
    }


    fun removeEdge() {
        _selectedEdge.value?.let { activeEdge ->
            _edges.update { edgeSet ->
                edgeSet.filter { it.id != activeEdge.id }
            }
        }
    }

    fun onDragStart(offset: Offset) {
        if (newlyAdding) {
            _selectedEdge.value?.let { activeEdge ->
                _edges.update { edges ->
                    edges.map { edge ->
                        if (edge.id == activeEdge.id)
                            edge.copy(
                                start = offset,
                                end = offset,
                                control = offset,
                                selectedPoint = EdgePoint.End
                            )
                        else edge
                    }
                }
            }
        }

    }

    fun dragOngoing(dragAmount: Offset) {
        _selectedEdge.value?.let { activeEdge ->
            _edges.update { edges ->
                edges.map { edge ->
                    if (edge.id == activeEdge.id) ExistingEdgeDragController(edge).onDrag(
                        dragAmount
                    ) else edge
                }
            }
        }
    }

    fun dragEnded() {
        _selectedEdge.value = null
        newlyAdding = false
    }

    fun setEdge(edges: List<EditorEdgeModel>) {
        _edges.update { edges }
    }

     fun clearSelection() =_edges.update { edges->
        _selectedEdge.update { null }
        edges.map { it.copy(
            selectedPoint = EdgePoint.None,
            pathColor = Constants.edgeColor,
            selectedPointColor = Constants.edgeColor
        )
        }
    }

}

internal class EdgeSelectionControllerImpl(
    private val edges: List<EditorEdgeModel>,
    private val tappedPosition: Offset) {

    private var selectedEdge: EditorEdgeModel? = null

    init {
        deSelectEdges()//remove if any node is already selected
        selectedEdge = edges.find { edge -> isAnyControlTouched(edge) }
    }

    fun findSelectedEdgeOrNull()=selectedEdge

    fun getEdgesWithSelection(): List<EditorEdgeModel> {
        val point = findSelectedPoint()
        return highLightPoint(point)
    }

    private fun highLightPoint(point: EdgePoint): List<EditorEdgeModel> {
        selectedEdge?.let { activeEdge ->
            val highLightedEdge = when (point) {
                EdgePoint.Start -> activeEdge.copy(
                    selectedPoint = EdgePoint.Start,
                    pathColor = Constants.selectedEdgePointColor,
                    showSelectedPoint = true
                )

                EdgePoint.End -> activeEdge.copy(
                    selectedPoint = EdgePoint.End,
                    showSelectedPoint = true
                )

                EdgePoint.Control -> activeEdge.copy(
                    selectedPoint = EdgePoint.Control,
                    pathColor =Constants.selectedEdgePointColor,
                    showSelectedPoint = true
                )

                else -> activeEdge.copy(
                    selectedPoint = EdgePoint.None,
                    pathColor = Constants.edgeColor
                )
            }
            var updatedEdges = edges - activeEdge
            updatedEdges = updatedEdges + highLightedEdge
            return updatedEdges

        }
        return deSelectEdges()
    }

    private fun deSelectEdges() =
        edges.map { it.copy(selectedPoint = EdgePoint.None, pathColor = EditorEdgeModel.pathDefaultColor) }



    private fun findSelectedPoint(): EdgePoint {
        selectedEdge?.let { edge ->
            return if (isStartTouched(edge)) EdgePoint.Start
            else if (isEndTouched(edge)) EdgePoint.End
            else if (isControlTouched(edge)) EdgePoint.Control else EdgePoint.None
        }
        return EdgePoint.None
    }

    private fun isAnyControlTouched(edge: EditorEdgeModel): Boolean {
        return isStartTouched(edge) || isEndTouched(edge) || isControlTouched(edge)
    }


    private fun isControlTouched(edge: EditorEdgeModel) =
        isTargetTouched(edge, edge.pathCenter)

    private fun isStartTouched(edge: EditorEdgeModel) =
        isTargetTouched(edge, edge.start)

    private fun isEndTouched(edge: EditorEdgeModel) =
        isTargetTouched(edge, edge.end)

    private fun isTargetTouched(
        edge: EditorEdgeModel,
        target: Offset
    ): Boolean {
        //Can causes crash
        return try {
            val minTouchTargetPx = edge.minTouchTargetPx
            return Range(
                target.x - minTouchTargetPx / 2,
                target.x + minTouchTargetPx / 2
            ).contains(tappedPosition.x) &&
                    Range(
                        target.y - minTouchTargetPx / 2,
                        target.y + minTouchTargetPx / 2
                    ).contains(tappedPosition.y)
        } catch (e: Exception) {
            false
        }
    }

//        return tappedPosition.x in Range(
//            target.x - minTouchTargetPx / 2,
//            target.x + minTouchTargetPx / 2
//        ) &&
//                tappedPosition.y in Range(
//            target.y - minTouchTargetPx / 2,
//            target.y + minTouchTargetPx / 2
//        )

}