package graph_editor.ui.component.edge


import androidx.compose.ui.geometry.Offset
import graph_editor.ui.component.VisualEdge

/*
There are two reasons for dragging:
If an existing edge is being dragged
a new edge is adding
Or neither existing edge is being dragged nor new edge being added.
it will handle just the existing edge dragging,
 */

internal class ExistingEdgeDragController(private val selectedEdge: VisualEdge) {

    fun onDrag(dragAmount: Offset): VisualEdge {
        return selectedEdge.updatePoint(dragAmount)
    }


    private fun VisualEdge.updatePoint(amount: Offset): VisualEdge {
       // Log.i("SelectedPoint","${selectedEdge.selectedPoint}")
        return when (selectedPoint) {
            EdgePoint.Start -> {
                val newStart = getPositionWithinCanvas(start + amount)
                this.copy(start = newStart, control = (newStart + end) / 2f)
            }

            EdgePoint.End -> {
                val newEnd = getPositionWithinCanvas(end + amount)
                this.copy(end = newEnd, control = (start + newEnd) / 2f)
            }

            EdgePoint.Control -> {
                this.copy(control = getPositionWithinCanvas(control + amount))
            }

            else -> this

        }
    }

    private fun getPositionWithinCanvas(offset: Offset): Offset {
        var (x, y) = offset
        if (x < 0f) x = 0f
        if (y < 0f) y = 0f
        return Offset(x, y)
    }
}