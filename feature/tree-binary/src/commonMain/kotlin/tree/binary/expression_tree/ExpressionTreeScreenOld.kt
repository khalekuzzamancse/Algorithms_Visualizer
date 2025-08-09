package tree.binary.expression_tree

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.lang.ComposeView
import core.lang.VoidCallback
import core.ui.core.ControlIconButton
import core.ui.core.CustomTextField
import core.ui.core.SimulationScreenEvent
import core.ui.core.SimulationScreenState
import core.ui.core.SimulationSlot
import tree.binary.Items
import tree.binary.PostFixItem
import tree.binary.core.SpacerVertical

//TODO: Has a bug, taking Input and SimulationSlot as separate screen or taking
// SimulationSlot in the else close show unwanted size of tree viewer, fix it later
@Composable
fun ExpressionTreeScreen(modifier: Modifier = Modifier,  onNavBack: VoidCallback={},navigationIcon: ComposeView) {
    val controller = remember { ExpressionTreeViewController() }
    val state by remember { mutableStateOf(SimulationScreenState()) }
    val showControls = controller.showControls.collectAsState().value
    val showInputDialog = controller.inputMode.collectAsState().value
    val infix = controller.infix.collectAsState().value
    val postFix = controller.postFix.collectAsState().value

    if (showInputDialog) {
        _InputDialog(
            title = "",
            initial = "3 + 5 * ( ( 4 - 2 ) + ( 6 / 3 ) - ( 2 - 1 ) )",
            onAdded = controller::onInputComplete,
            onDismiss =   onNavBack,
        )
    }
    SimulationSlot(
        modifier = Modifier,
        state = state,
        disableControls = false,
        enableNext = controller.enableNext.collectAsState().value,
        navigationIcon = { },
        extraActions = {
            ControlIconButton(
                onClick = controller::toggleControlsVisibility,
                icon = if (showControls) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                contentDescription = "Autoplay",
                enabled = true
            )
        },
        visualization = {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().verticalScroll(
                    rememberScrollState()
                )
            ) {
                AnimatedVisibility(showControls) {
                    Column(Modifier.fillMaxWidth()) {
                        if (infix.isNotEmpty()) {
                            Title(text = "Infix")
                            SpacerVertical(8)
                            Items(item = infix.map { it })
                            SpacerVertical(8)
                        }
                        if (postFix.isNotEmpty()) {
                            HorizontalDivider()
                            SpacerVertical(8)
                            Title(text = "PostFix")
                            SpacerVertical(8)
                            PostFixItem( item = postFix)
                            SpacerVertical(8)
                            HorizontalDivider()
                        }
                    }
                }
                SpacerVertical(16)
                _TreeView(
                    modifier = Modifier.padding(16.dp).height(350.dp).fillMaxWidth(),
                    controller = controller
                )
            }
        },
        onEvent = { event ->
            when (event) {
                is SimulationScreenEvent.AutoPlayRequest -> {
                    controller.autoPlayRequest(event.time)
                }
                SimulationScreenEvent.NextRequest -> {
                    controller.next()
                }
                SimulationScreenEvent.NavigationRequest -> {}
                SimulationScreenEvent.ResetRequest -> {
                    controller.reset()
                }

                else -> {}
            }

        },
    )


}



@Composable
private fun _InputDialog(
    title: String,
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
                    onValueChange = { text = it },
                    keyboardType = KeyboardType.Text,
                    leadingIcon = leadingIcon
                )

            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAdded(text.trim())
                  //  onDismiss()
                }
            ) {
                Text("Visualize")

            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}


