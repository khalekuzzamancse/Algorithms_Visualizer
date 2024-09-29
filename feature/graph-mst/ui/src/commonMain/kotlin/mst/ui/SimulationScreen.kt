@file:Suppress("functionName")

package mst.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import graph.graph.editor.ui.GraphEditor
import graph.graph.viewer.GraphViewerController
import graph.graph.viewer.GraphViewer


@Composable
fun PrimsSimulationScreen() {
    val viewModel = remember { SimulationViewModel() }
    if (viewModel.isInputMode.collectAsState().value) {
        GraphEditor { result ->
            viewModel.onGraphCreated(result)
           // println(result)
        }
    } else {
        _GraphViewer(
            onNext = viewModel::onNext,
            graphController = viewModel.graphController
        )

    }


}

@Composable
private fun _GraphViewer(
    modifier: Modifier = Modifier,
    graphController: GraphViewerController,
    onNext:() -> Unit
) {
    Column(modifier) {
        Button(
            onClick =onNext
        ) {
            Text("Next")
        }

        GraphViewer(
            modifier = Modifier
                .background(Color.Gray)
                .padding(16.dp),
            controller = graphController
        )
    }
}



