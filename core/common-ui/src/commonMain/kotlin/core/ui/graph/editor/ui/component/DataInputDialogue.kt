package core.ui.graph.editor.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Input
import androidx.compose.material.icons.outlined.Input
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.commonui.CustomTextField

@Composable
internal fun InputDialog(
    label: String,
    type: KeyboardType = KeyboardType.Text,
    onDismissRequest: () -> Unit,
    onInputComplete: (String) -> Unit,

) {
    var text by remember { mutableStateOf("") }
    val keyboard= LocalSoftwareKeyboardController.current

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        text = {
            Column {
                CustomTextField(
                    label = label,
                    value = text,
                    leadingIcon = Icons.AutoMirrored.Outlined.Input,
                    onValueChange = { input ->
                        text = if (type == KeyboardType.Number)
                            input.filter { it.isDigit() }
                        else
                            input
                    },
                    keyboardType =type,
                    modifier = Modifier.widthIn(max=300.dp)
                )

            }
        },
        confirmButton = {
            Button(
                onClick = {
                    keyboard?.hide()
                    if (text.isNotBlank()) {
                        onInputComplete(text)
                        text = "" // Clear the text after submission
                    }
                }
            ) {
                Text(text = "Done")

            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    keyboard?.hide()
                    onDismissRequest() // Dismiss the dialog without completing the input
                }
            ) {
                Text(text = "Cancel")
            }
        }
    )
}
