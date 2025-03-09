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
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun BinaryTree() {
    var tree by  remember {  mutableStateOf(BST<Int>(null)) }
    var showDialog by remember { mutableStateOf(false) }

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
              tree=tree.insert(value)

            }){
                showDialog=false
            }
        }

        tree.root?.let {
            TreeView(it)
        }

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



//
//class Tree<T : Comparable<T>>(val root: Node<T>?) {
//
//    constructor() : this(null)
//
//    fun add(value: T): Tree<T> {
//        return Tree(insert(root, value))
//    }
//
//    private fun insert(node: Node<T>?, value: T): Node<T> {
//        if (node == null) return Node(value)
//
//        return if (value < node.data) {
//            Node(node.data).apply {
//                left = insert(node.left, value)
//                right = node.right
//            }
//        } else if (value > node.data) {
//            Node(node.data).apply {
//                right = insert(node.right, value)
//                left = node.left
//            }
//        } else {
//            node // No duplicate insertion
//        }
//    }
//
//    override fun toString(): String = inorderTraversal(root).joinToString(", ")
//
//    private fun inorderTraversal(node: Node<T>?): List<T> {
//        return node?.let { inorderTraversal(it.left) + it.data + inorderTraversal(it.right) } ?: emptyList()
//    }
//}
