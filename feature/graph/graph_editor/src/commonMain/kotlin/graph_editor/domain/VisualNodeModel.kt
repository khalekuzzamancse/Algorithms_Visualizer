package graph_editor.domain

import androidx.compose.ui.geometry.Offset

/*
- This is just for data transfer,the client module should not use in own purpose
- Will be used to return the result to the client module
- Do not need to store the color and other UI info,the client will take care of that
 */
data class VisualNodeModel internal constructor(
    //prevent client  to create an instance of it,by making constructor  internal
    val id: String,
    val label: String,
    val topLeft: Offset,
    val sizePx: Float,
)
