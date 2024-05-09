package graph_editor.ui.component.edge

import androidx.compose.ui.geometry.Offset
import graph_editor.ui.component.VisualEdge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class GraphEditorEdgeController  {
    private val _edges: MutableStateFlow<List<VisualEdge>> =
        MutableStateFlow(emptyList())
     val edges = _edges.asStateFlow()

    fun addEdge(edge: VisualEdge) {
        _edges.value = edges.value + edge.copy(selectedPoint= EdgePoint.End)
        _selectedEdge.value = edge.copy(selectedPoint= EdgePoint.End)
        newlyAdding=true
    }
    /*
    Observing when the canvas is tapped so that:
    Select a point of the edge to edit it.
    Select the edge to remove it.
     */

    private val _selectedEdge = MutableStateFlow<VisualEdge?>(null)
    val selectedEdge = _selectedEdge.asStateFlow()
    private var newlyAdding=false


     fun onTap(tappedPosition: Offset) {
        val tapListener = EdgeSelectionControllerImpl(_edges.value, tappedPosition)
        _selectedEdge.value = tapListener.findSelectedEdge()
        _edges.update { tapListener.getEdgesWithSelection() }
    }

    // Tapping handling done
    //-----------Removing selected edge
    fun removeEdge() {
        _selectedEdge.value?.let { activeEdge ->
            _edges.update { edgeSet ->
                edgeSet.filter { it.id != activeEdge.id }
            }
        }
    }

    fun onDragStart(offset: Offset) {
        if(newlyAdding){
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

     fun dragOngoing(dragAmount: Offset, position: Offset) {
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
        _selectedEdge.value=null
        newlyAdding=false
    }

     fun setEdge(edges: List<VisualEdge>) {
        _edges.update { edges }
    }

}

