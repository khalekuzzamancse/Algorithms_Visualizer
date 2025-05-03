package graphtraversal.ui

// Make sure to import FlowRow
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import graphtraversal.presentationlogic.model.NodeModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NodeSelectionDialog(
    nodes: Set<NodeModel>,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var selectedNodeId by remember { mutableStateOf<String?>(null) }

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
                            .background(
                                if (selectedNodeId == node.id)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.surfaceVariant
                            )
                            .clickable { selectedNodeId = node.id }
                    ) {
                        Text(
                            text = node.label,
                            color = if (selectedNodeId == node.id) Color.White else Color.Black,
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
                    selectedNodeId?.let { onConfirm(it) }
                },
                enabled = selectedNodeId != null
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
