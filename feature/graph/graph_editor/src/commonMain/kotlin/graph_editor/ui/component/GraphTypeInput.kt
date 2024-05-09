package graph_editor.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GraphTypeInput(
    onInputComplete: (GraphType) -> Unit,
) {
    val options = listOf(GraphType.Undirected,GraphType.Directed,GraphType.UnDirectedWeighted,GraphType.DirectedWeighted)
    var selectedOptionText by remember { mutableStateOf(options[0]) }

        Dialog(
            onDismissRequest = { }
        ) {
            Surface(
                modifier = Modifier
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
            ) {
                Column {
                    options.forEach { selected ->
                        DropdownMenuItem(
                            text = { Text(selected.label) },
                            onClick = {
                                selectedOptionText = selected
                                onInputComplete(selected)
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }

            }
        }

}

