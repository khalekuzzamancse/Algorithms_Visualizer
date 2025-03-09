package tree.binary

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.launch

@Composable
fun BinaryTree() {
    val tree =  remember { TreeViewController<Int>() }
    var showDialog by remember { mutableStateOf(false) }
    val  scope= rememberCoroutineScope()

    Column {
        Button(onClick = {
          try {
            showDialog=true
           }
           catch (_:Exception){}
        }) {
            Text("Add Node")
        }
        if (showDialog){
            InputDialog(onAdded = {value->
                scope.launch {
                    tree.insert(value)
                }
            }){
                showDialog=false
            }
        }
            TreeView(controller = tree)

    }
}


@Composable
fun InputDialog(
    onAdded: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }


    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Enter a Number") },
        text = {
            Column {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Number") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val number = text.text.toIntOrNull()
                    if (number != null) {
                        onAdded(number)
                        onDismiss()
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

