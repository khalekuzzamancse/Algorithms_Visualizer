package graphtraversal.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import graph.graph.common.model.Node

@Composable
fun MainScreen() {
    val nodes = listOf(
        Node(id = "1", label = "A"),
        Node(id = "2", label = "B"),
        Node(id = "3", label = "C")
    )

    var showDialog by remember { mutableStateOf(false) }
    var selectedNodeId by remember { mutableStateOf<String?>(null) }

    Button(onClick = { showDialog = true }) {
        Text("Select Node")
    }

    if (showDialog) {
        NodeSelectionDialog(
            nodes = nodes,
            onDismiss = { showDialog = false },
            onConfirm = { id ->
                selectedNodeId = id
                showDialog = false
                // Handle the selected node ID as needed
            }
        )
    }

    selectedNodeId?.let {
        Text("Selected Node ID: $it")
    }
}
