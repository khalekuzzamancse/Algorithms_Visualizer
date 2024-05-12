package graph_editor.infrastructure

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import graph_editor.ui.component.VisualEdge
import graph_editor.ui.component.VisualNode

internal object SavedGraphProvider {
        val nodes = listOf(
            VisualNode(id = "c66097e1-0d1c-4c3d-b550-75c9787d054c", label = "1", topLeft = Offset(384.0f, 48.0f), exactSizePx = 48.0f, color = Color(1.0f, 0.0f, 0.0f, 1.0f)),
            VisualNode(id = "4eb11290-f8e5-4329-9b0f-94ee6fe6590c", label = "2", topLeft = Offset(312.0f, 121.0f), exactSizePx = 48.0f, color = Color(1.0f, 0.0f, 0.0f, 1.0f)),
            VisualNode(id = "3aeb3a6b-e8fc-4182-af07-c937f54c88aa", label = "3", topLeft = Offset(448.0f, 118.0f), exactSizePx = 48.0f, color = Color(1.0f, 0.0f, 0.0f, 1.0f)),
            VisualNode(id = "41bb5ef6-d258-4f90-b618-d7a48ce5b4be", label = "4", topLeft = Offset(243.0f, 192.9f), exactSizePx = 48.0f, color = Color(1.0f, 0.0f, 0.0f, 1.0f)),
            VisualNode(id = "1942ed99-cfa8-4e49-ac5b-d808b110e263", label = "5", topLeft = Offset(365.8f, 198.1f), exactSizePx = 48.0f, color = Color(1.0f, 0.0f, 0.0f, 1.0f))
        )
    val edges = listOf(
        VisualEdge(id = "c51a1854-2782-41c8-b81b-4b18d0b2020d", start = Offset(400.0f, 74.0f), end = Offset(345.0f, 130.9f), control = Offset(372.5f, 102.4f), cost = null, undirected = true, minTouchTargetPx = 30.0f),
        VisualEdge(id = "669a6c7a-1362-446c-a5da-4e0c2be80463", start = Offset(323.0f, 150.0f), end = Offset(276.1f, 203.0f), control = Offset(299.6f, 176.5f), cost = null, undirected = true, minTouchTargetPx = 30.0f),
        VisualEdge(id = "e14664da-507a-429c-a8c9-01357b08ddeb", start = Offset(332.0f, 147.0f), end = Offset(380.9f, 216.9f), control = Offset(356.5f, 182.0f), cost = null, undirected = true, minTouchTargetPx = 30.0f),
        VisualEdge(id = "5549a108-336c-4185-af07-8d52d9f422c6", start = Offset(413.0f, 68.0f), end = Offset(467.0f, 131.9f), control = Offset(440.0f, 99.9f), cost = null, undirected = true, minTouchTargetPx = 30.0f)
    )


}