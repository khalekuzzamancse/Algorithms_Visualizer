package core.commonui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.CodeOff
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun SimulationSlot(
    modifier: Modifier = Modifier,
    disableControls: Boolean=false,
    enableNext:Boolean=true,
    state: SimulationScreenState,
    onEvent: (SimulationScreenEvent) -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    extraActions: (@Composable (Modifier) -> Unit)?=null,
    resultSummary:( @Composable ColumnScope.() -> Unit)?=null,
    pseudoCode: (@Composable ColumnScope.(Modifier) -> Unit)?=null,
    visualization: @Composable ColumnScope.() -> Unit,
) {

    TopBarControlSection(
        enableNext = enableNext,
        modifier = Modifier,
        disableControls = disableControls,
        extraActions = extraActions,
        showPseudocode = state.showPseudocode,
        disablePseudocode = true,
        navigationIcon = navigationIcon,
        onNext = { onEvent(SimulationScreenEvent.NextRequest) },
        onResetRequest = { onEvent(SimulationScreenEvent.ResetRequest) },
        onAutoPlayRequest = { onEvent(SimulationScreenEvent.AutoPlayRequest(it)) },
        onCodeVisibilityToggleRequest = { onEvent(SimulationScreenEvent.CodeVisibilityToggleRequest) }
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(state.showVisualization) {
                visualization()
            }
            AnimatedVisibility(state.showResultSummary) {
                if (resultSummary != null) {
                    Spacer(Modifier.height(16.dp))
                    resultSummary()
                }
            }
            if (resultSummary!=null)
                Spacer(Modifier.height(16.dp))
            AnimatedVisibility(state.showPseudocode) {
                if (pseudoCode != null) {
                    pseudoCode(Modifier.align(Alignment.Start))
                }
            }
        }

    }


}

data class SimulationScreenState(
    val showPseudocode: Boolean = true,
    val showResultSummary: Boolean = true,
    val showNavTabs: Boolean = false,
    val showVisualization: Boolean = true,
)

/**
 * Using sealed so that by mistake outer file or module can not create new event
 * so to make read only the event to the client
 */
sealed interface SimulationScreenEvent {
    data object NavigationRequest : SimulationScreenEvent
    data object ToggleNavigationSection : SimulationScreenEvent
    data object NextRequest : SimulationScreenEvent
    data object ResetRequest : SimulationScreenEvent
    data object CodeVisibilityToggleRequest : SimulationScreenEvent
    data class AutoPlayRequest(val time: Int) : SimulationScreenEvent
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarControlSection(
    modifier: Modifier = Modifier,
    disableControls: Boolean,
    showPseudocode: Boolean = false,
    enableNext:Boolean,
    disablePseudocode: Boolean = true,
    extraActions: (@Composable (Modifier) -> Unit)?=null,
    onNext: () -> Unit = {},
    onResetRequest: () -> Unit = {},
    onAutoPlayRequest: (Int) -> Unit = {},
    onCodeVisibilityToggleRequest: () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    var autoPlayTimeInSeconds by rememberSaveable { mutableStateOf<Int?>(null) }
    var inputTime by remember { mutableStateOf("1000") }
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = navigationIcon,
                actions = {
                    TopBarActions(
                        disablePseudocode = true,
                        enableNext = enableNext,
                        disableControls = disableControls,
                        showPseudocode = showPseudocode,
                        onNext = onNext,
                        onCodeVisibilityToggleRequest = onCodeVisibilityToggleRequest,
                        onResetRequest = onResetRequest,
                        onAutoPlayRequest = { showDialog = true }
                    )
                    if (extraActions!=null)
                        extraActions(Modifier)
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }

    if (showDialog) {
        AutoPlayDialog(
            inputTime = inputTime,
            onInputChange = { inputTime = it },
            onConfirm = {
                val seconds = inputTime.toIntOrNull()
                if (seconds != null) {
                    onAutoPlayRequest(seconds)
                    autoPlayTimeInSeconds = seconds
                    showDialog = false
                }
            },
            onCancel = { showDialog = false }
        )
    }
}

@Composable
private fun AutoPlayDialog(
    inputTime: String,
    onInputChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Set Autoplay Time") },
        text = {
            CustomTextField(
                leadingIcon = Icons.Outlined.Timer,
                value = inputTime,
                onValueChange = onInputChange,
                label = "Delay in ms",
                keyboardType = KeyboardType.Number
            )
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun TopBarActions(
    disableControls: Boolean,
    enableNext:Boolean,
    showPseudocode: Boolean,
    disablePseudocode: Boolean,
    onNext: () -> Unit,
    onCodeVisibilityToggleRequest: () -> Unit,
    onResetRequest: () -> Unit,
    onAutoPlayRequest: () -> Unit
) {
    ControlIconButton(
        onClick = onNext,
        icon = Icons.Filled.SkipNext,
        contentDescription = "Next",
        enabled =enableNext
    )

    if (!disablePseudocode) {
        ControlIconButton(
            onClick = onCodeVisibilityToggleRequest,
            icon = if (showPseudocode) Icons.Default.CodeOff else Icons.Default.Code,
            contentDescription = "Toggle Code Visibility",
            enabled = !disableControls
        )
    }


    ControlIconButton(
        onClick = onResetRequest,
        icon = Icons.Default.Replay,
        contentDescription = "Reset",
        enabled = !disableControls
    )

    ControlIconButton(
        onClick = onAutoPlayRequest,
        icon = Icons.Outlined.Timer,
        contentDescription = "Autoplay",
        enabled = !disableControls
    )

}

@Composable
 fun ControlIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String?,
    enabled: Boolean
) {
    IconButton(onClick = onClick, enabled = enabled) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (enabled) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            }
        )
    }
}
