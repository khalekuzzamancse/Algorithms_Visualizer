package graphtopologicalsort.ui

// Make sure to import FlowRow
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import graphtopologicalsort.presentationlogic.model.NodeModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NodeSelectionDialog(
    nodes: Set<NodeModel>,
    onDismiss: () -> Unit,
    onConfirm: (List<String>) -> Unit
) {


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select a Node") },
        text = {
            FlowRow(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement =Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                nodes.forEach { node ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
//                            .background(
//                                if (selectedNodeId == node.id)
//                                    MaterialTheme.colorScheme.primary
//                                else
//                                    MaterialTheme.colorScheme.surfaceVariant
//                            )
//                            .clickable { selectedNodeId = node.id }
                    ) {
                        Text(
                            text = node.label,
                            //color = if (selectedNodeId == node.id) Color.White else Color.Black,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(nodes.map { it.id }.reversed())
                   // selectedNodeId?.let { onConfirm(it) }
                },
              //  enabled = selectedNodeId != null
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
