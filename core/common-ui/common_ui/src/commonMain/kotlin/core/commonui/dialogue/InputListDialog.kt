package core.commonui.dialogue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * * Completely stateless component ,directly can be reused,
 * * date is returned as Milli second so that this component does not need to coupled with a dateConverter,
 *
 */
@Composable
fun ArrayInputDialog(
    initialList: String = "10, 5, 4, 13, 8",
    onDismiss: (List<Int>) -> Unit = {},
    onConfirm: (List<Int>) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(initialList) }
    val list = text.split("\\s*,\\s*|\\s+".toRegex()) // Split by comma or space
    AlertDialog(
        onDismissRequest = {
            val numberList = list.mapNotNull { it.toIntOrNull() }
            try {
                onDismiss(numberList)
            } catch (_: Exception) {
            }
        },
        title = { Text(text = "Enter List of Numbers") },
        text = {
            Column {
                TextField(
                    label = { Text("List") },
                    value = text,
                    onValueChange = { text = it.filter {char-> char.isDigit()||char==',' } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

            }


        },
        confirmButton = {
            Button(
                onClick = {
                    val numberList = list.mapNotNull { it.toIntOrNull() }
                    try {
                        onConfirm(numberList)
                    } catch (_: Exception) {
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                val numberList = list.mapNotNull { it.toIntOrNull() }
                try {
                    onConfirm(numberList)
                } catch (_: Exception) {
                }
            }) {
                Text("Cancel")
            }
        }
    )

}


/**
 * * Completely stateless component ,directly can be reused,
 * * date is returned as Milli second so that this component does not need to coupled with a dateConverter,
 *
 */
@Composable
fun SearchInputDialoge(
    showDialog: Boolean,
    onDismiss: () -> Unit = {},
    onConfirm: (List<Int>, target: Int) -> Unit
) {
    if (showDialog) {
        var text by rememberSaveable { mutableStateOf("10 20 30 40 50 60") }
        var target by rememberSaveable { mutableStateOf("50") }
        val list = text.split("\\s*,\\s*|\\s+".toRegex()) // Split by comma or space
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Enter List of Numbers") },
            text = {
                Column {
                    TextField(
                        label = { Text("List") },
                        value = text,
                        onValueChange = { text = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(Modifier.height(16.dp))
                    TextField(
                        label = { Text("Target") },
                        value = target,
                        onValueChange = { target = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }


            },
            confirmButton = {
                Button(
                    onClick = {
                        val numberList = list.mapNotNull { it.toIntOrNull() }
                        try {
                            onConfirm(numberList, target.toInt())
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


