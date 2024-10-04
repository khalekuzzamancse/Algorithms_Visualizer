package core.commonui.dialogue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType


/**
 * * Completely stateless component ,directly can be reused,
 * * date is returned as Milli second so that this component does not need to coupled with a dateConverter,
 *
 */
@Composable
fun TimeInputDialogue(
    showDialog: Boolean,
    onDismiss: () -> Unit = {},
    onConfirm: (Long) -> Unit
) {
    if (showDialog) {

        var times by rememberSaveable { mutableStateOf("500") }
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Enter Time in ms") },
            text = {
                Column {
                    TextField(
                        value = times,
                        onValueChange = { times = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }


            },
            confirmButton = {
                Button(
                    onClick = {
                        try {
                            onConfirm(times.toLong())
                        } catch (_: Exception) {
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}


