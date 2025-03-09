package tree.binary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.commonui.CustomTextField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tree.binary.tree_view.TreeView
import tree.binary.tree_view.TreeViewController

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BinarySearchTree(navigationIcon: @Composable () -> Unit={}) {
    val tree = remember { TreeViewController.create<Int>() }
    var list by remember { mutableStateOf(emptyList<Int>()) }
    val scope = rememberCoroutineScope()

    Column {
        FlowRow (
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){

            _InputButton(
                label = "Add",
                title = "Enter a single or multiple number",
                initialValue = "50 40 30 45 80 70 60 65 90",
                leadingIcon = Icons.AutoMirrored.Outlined.List,
                onMultipleAdded = { values ->
                    list = list + values
                    scope.launch {
                        values.forEach { value ->
                            tree.insert(value)
                            delay(1000)
                        }

                    }
                },
                onSingleAdded = { value ->
                    list = list + value
                    scope.launch {
                        tree.insert(value)
                    }
                }
            )
            _InputButton(
                label = "Search",
                onSingleAdded = { value ->
                    scope.launch {
                        tree.search(value)
                    }
                }
            )
            _Button(
                label = "Min"
            ) {
                scope.launch {
                    tree.findMin()
                }
            }
            SpacerHorizontal(16)
            _Button(
                label = "Max"
            ) {
                scope.launch {
                    tree.findMax()
                }
            }
            _InputButton(
                label = "Successor",
                title = "Enter the sub tree root",
                onSingleAdded = { value ->
                    scope.launch {
                        tree.findSuccessor(value)
                    }
                }
            )

            _InputButton(
                label = "Predecessor",
                title = "Enter the sub tree root",
                onSingleAdded = { value ->
                    scope.launch {
                        tree.findPredecessor(value)
                    }
                }
            )
            _Button(
                label = "Reset"
            ) {
                scope.launch {
                    tree.resetColor()
                }
            }


        }
        SpacerVertical(16)
        if (list.isNotEmpty())
            Text(list.joinToString(" "))
        SpacerVertical(16)

        TreeView(controller = tree)

    }
}


@Composable
fun SpacerHorizontal(width: Int) = Spacer(Modifier.width(width.dp))

@Composable
fun SpacerVertical(height: Int) = Spacer(Modifier.height(height.dp))



@Composable
fun _Button(modifier: Modifier = Modifier, label: String, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(label)
    }
}

@Composable
fun _InputButton(
    modifier: Modifier = Modifier,
    label: String,
    title:String="Enter a number",
    initialValue: String="",
    onSingleAdded: (Int) -> Unit = {},
    leadingIcon:ImageVector= Icons.Outlined.Search,
    onMultipleAdded: ((List<Int>) -> Unit)? =null
) {
    var showDialog by remember { mutableStateOf(false) }
    Button(
        modifier = modifier,
        onClick = {
            showDialog = true
        }
    ) {
        Text(label)
    }
    if (showDialog) {
        _InputDialog(
            initialValue = initialValue,
            title = title,
            leadingIcon = leadingIcon,
            onAdded = onSingleAdded,
            onMultipleAdded = onMultipleAdded,
            onDismiss = {
                showDialog = false
            })
    }
}

@Composable
private fun _InputDialog(
    title:String,
    initialValue: String = "",
    onAdded: (Int) -> Unit,
    onMultipleAdded: ((List<Int>) -> Unit)? =null,
    leadingIcon:ImageVector= Icons.Outlined.Search,
    onDismiss: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf(initialValue) }


    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = {
            Column {
                CustomTextField(
                    label = title,
                    value = text,
                    onValueChange =  { text = it },
                    keyboardType = KeyboardType.Text,
                    leadingIcon = leadingIcon
                )

            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val input = text.trim()
                    //Catch for typecast
                    val interestedForSingle=onMultipleAdded==null
                    try {
                        if(interestedForSingle) onAdded(input.toInt())
                        else{
                            val numbers = input.split(Regex("[,\\s]+"))
                                .mapNotNull { it.toIntOrNull() }
                            if (numbers.size == 1) {
                                onAdded(numbers.first())

                            }
                            if (numbers.size > 1&&onMultipleAdded!=null) {
                                onMultipleAdded(numbers)
                            }
                        }
                        onDismiss()
                    }
                    catch (_:Exception){}
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


