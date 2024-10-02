package graph.graph.editor.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
internal fun InputDialog(
    label: String,
    type: KeyboardType = KeyboardType.Text,
    onDismissRequest: () -> Unit,
    onInputComplete: (String) -> Unit,

) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Text(text = label)
        },
        text = {
            Column {
                TextField(
                    value = text,
                    onValueChange = { input ->
                        text = if (type == KeyboardType.Number)
                            input.filter { it.isDigit() }.take(2) // Max 2 digits for numbers
                        else
                            input.take(2) // Max 2 characters for text
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = type),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        onInputComplete(text)
                        text = "" // Clear the text after submission
                    }
                }
            ) {
                Text("Done")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest() // Dismiss the dialog without completing the input
                }
            ) {
                Text("Cancel")
            }
        }
    )
}
