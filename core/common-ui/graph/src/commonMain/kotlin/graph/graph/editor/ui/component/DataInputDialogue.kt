package graph.graph.editor.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun DataInputDialogue(
    description: String = "",
    onInputComplete: (String) -> Unit,
) {

    var text by remember {
        mutableStateOf("")
    }
    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val updateErrorMessage: (String) -> Unit = { msg ->
        scope.launch {
            errorMessage = msg
            delay(2000)
            errorMessage =
                null//clear old message so that same error message can shown multiple time
        }
    }
    Dialog(
        onDismissRequest = { }
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .semantics { contentDescription = "Data input dialogue" },
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = description)
                Spacer(modifier = Modifier.height(8.dp))
                _TextField(text, onValueChanged = { text = it },errorMessage)

                TextButton(onClick = {
                    if (text.isNotEmpty()) {
                        onInputComplete(text)
                        text = ""
                    } else {
                        updateErrorMessage("Can not be empty")
                    }

                }) {
                    Text(text = "Done")
                }
            }
        }
    }

}

@Composable
private fun _TextField(
    text: String,
    onValueChanged: (String) -> Unit,
    errorMessage:String?
) {
    TextField(
        value = text,
        onValueChange = onValueChanged,
        singleLine = true,
        isError = errorMessage!=null,
        supportingText = {
            if (errorMessage!=null){
                Text(errorMessage)
            }
        }
    )
}
