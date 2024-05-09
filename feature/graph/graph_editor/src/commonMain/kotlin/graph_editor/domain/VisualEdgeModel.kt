package graph_editor.domain

import androidx.compose.ui.geometry.Offset

/*
- This is just for data transfer,the client module should not use in own purpose
- Will be used to return the result to the client module
- Do not need to store the color and other UI info,the client will take care of that
- Need not to store the edge is directed or not in every edge,the graph direction info should be kept in graph level
 */
 data class VisualEdgeModel internal  constructor(
    //prevent client  to create an instance of it,by making constructor  internal
    val id: String,
    val start: Offset,
    val end: Offset,
    val control: Offset,
    val cost: String?,
)