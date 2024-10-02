package graph.graph.editor.controller


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import graph.graph.common.model.EditorEdgeMode
import graph.graph.editor.model.EdgePoint
import graph.graph.editor.model.Range

internal class EdgeSelectionControllerImpl(
    private val edges: List<EditorEdgeMode>,
    private val tappedPosition: Offset,
) {
    private var selectedEdge: EditorEdgeMode? = null

    init {
        selectedEdge = edges.find { edge -> isAnyControlTouched(edge) }
    }

    fun findSelectedEdge(): EditorEdgeMode? {
        return selectedEdge
    }

    fun getEdgesWithSelection(): List<EditorEdgeMode> {
        val point = findSelectedPoint()
        return highLightPoint(point)
    }

    private fun highLightPoint(point: EdgePoint): List<EditorEdgeMode> {
        selectedEdge?.let { activeEdge ->
            val highLightedEdge = when (point) {
                EdgePoint.Start -> activeEdge.copy(
                    selectedPoint = EdgePoint.Start,
                    pathColor = Color.Blue,
                    showSelectedPoint = true
                )

                EdgePoint.End -> activeEdge.copy(
                    selectedPoint = EdgePoint.End,
                    pathColor = Color.Blue,
                    showSelectedPoint = true
                )

                EdgePoint.Control -> activeEdge.copy(
                    selectedPoint = EdgePoint.Control,
                    pathColor = Color.Blue,
                    showSelectedPoint = true
                )

                else -> activeEdge.copy(
                    selectedPoint = EdgePoint.None,
                    pathColor = Color.Black
                )
            }
            var updatedEdges = edges - activeEdge
            updatedEdges = updatedEdges + highLightedEdge
            return updatedEdges

        }
        return deSelectEdges()
    }

    private fun deSelectEdges() =
        edges.map { it.copy(selectedPoint = EdgePoint.None, pathColor = Color.Black) }


    private fun findSelectedPoint(): EdgePoint {
        selectedEdge?.let { edge ->
            return if (isStartTouched(edge)) EdgePoint.Start
            else if (isEndTouched(edge)) EdgePoint.End
            else if (isControlTouched(edge)) EdgePoint.Control else EdgePoint.None
        }
        return EdgePoint.None
    }

    private fun isAnyControlTouched(edge: EditorEdgeMode): Boolean {
        return isStartTouched(edge) || isEndTouched(edge) || isControlTouched(edge)
    }


    private fun isControlTouched(edge: EditorEdgeMode) =
        isTargetTouched(edge, edge.pathCenter)

    private fun isStartTouched(edge: EditorEdgeMode) =
        isTargetTouched(edge, edge.start)

    private fun isEndTouched(edge: EditorEdgeMode) =
        isTargetTouched(edge, edge.end)

    private fun isTargetTouched(
        edge: EditorEdgeMode,
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
      }
      catch (e:Exception){
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