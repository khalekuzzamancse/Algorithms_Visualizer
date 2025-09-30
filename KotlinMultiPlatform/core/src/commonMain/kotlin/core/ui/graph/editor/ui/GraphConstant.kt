package core.ui.graph.editor.ui

import androidx.compose.ui.unit.dp

object GraphConstant {
    fun nodeMinSize(hasDistance:Boolean=false)=
        if (hasDistance) 50.dp else 40.dp
}