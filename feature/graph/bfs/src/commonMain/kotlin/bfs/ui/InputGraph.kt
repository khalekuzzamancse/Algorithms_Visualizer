package bfs.ui

import androidx.compose.runtime.Composable
import graph_editor.ui.GraphEditor

@Composable
 fun InputGraph() {
    GraphEditor { result->
        val graph=result.graph
        println("InputGraphLog:: $graph")
    }

}