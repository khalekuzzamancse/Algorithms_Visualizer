package tree.binary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.commonui.CustomTextField
import kotlinx.coroutines.flow.MutableStateFlow
import tree.binary.core.SpacerVertical
import tree.binary.expression_tree.ExpressionTreeBuilder
import tree.binary.tree_view.Node
import tree.binary.tree_view.TreeViewOld

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExpressionTree() {
    var inputMode by rememberSaveable { mutableStateOf(false) }
    var expression by rememberSaveable { mutableStateOf<String?>(null) }
    var controller :ExpressionTreeController? by  remember { mutableStateOf(null) }

    controller?.let { controllerSnapShot->
        val  root= controllerSnapShot.tree.collectAsState().value
        Column {
           FlowRow (
               horizontalArrangement = Arrangement.spacedBy(16.dp),
               verticalArrangement = Arrangement.spacedBy(16.dp),
           ){
               Button(label = "Next",onClick = controllerSnapShot::next)
               Button(label = "Reset",onClick = {
                  inputMode=true
               })
           }
            SpacerVertical(16)
            root?.let {
                TreeViewOld(it)
            }
        }
    }

    if (expression==null||inputMode){
        _InputDialog(
            title = "",
            initial = "3 + 5 * ( 2 - 1 )",
            onAdded = {exp->
                expression=exp
               controller= ExpressionTreeController(exp)
            }
        ){
            inputMode=false
        }
    }


}

class ExpressionTreeController(
     expression: String
) {
    val tree = MutableStateFlow<Node<String>?>(null)
    private val __root = MutableStateFlow( ExpressionTreeBuilder.create().buildTree(expression))
    private val iterator: Iterator<Node<String>> =buildTreeStepByStep(__root.value).iterator()

    fun  next(){
        if (iterator.hasNext()){
            tree.value=iterator.next()
        }
    }

    private fun <T> buildTreeStepByStep(node: Node<T>?): Sequence<Node<T>> = sequence {
        if (node == null) return@sequence // Base case

        // Step 1: Yield only the root without children
        var currentNode = Node(node.data, left = null, right = null, id = node.id)
        yield(currentNode)

        // Step 2: Incrementally add the left subtree
        var leftTree: Node<T>? = null
        node.left?.let {
            for (partialLeft in buildTreeStepByStep(it)) {
                leftTree = partialLeft
                currentNode = currentNode.copy(left = leftTree)
                yield(currentNode) // Yield updated tree state
            }
        }

        // Step 3: Incrementally add the right subtree
        var rightTree: Node<T>? = null
        node.right?.let {
            for (partialRight in buildTreeStepByStep(it)) {
                rightTree = partialRight
                currentNode = currentNode.copy(right = rightTree)
                yield(currentNode) // Yield updated tree state
            }
        }
    }
}


@Composable
private fun _InputDialog(
    title:String,
    initial: String = "",
    onAdded: (String) -> Unit,
    leadingIcon: ImageVector = Icons.Outlined.Search,
    onDismiss: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf(initial) }


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
                    onAdded(text.trim())
                    onDismiss()
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



