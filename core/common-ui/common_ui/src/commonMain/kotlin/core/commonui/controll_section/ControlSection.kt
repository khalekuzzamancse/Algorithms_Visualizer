package core.commonui.controll_section

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
/**
 * This top bar can be used to utilize the space of the top section of the screen
 * so that
 */
@Composable
fun TopBarControlSection(
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack,
    showPseudocode: Boolean = false,
    onNavIconClick: () -> Unit = {},
    onNext: () -> Unit = {},
    onResetRequest: () -> Unit = {},
    onAutoPlayRequest: (Int) -> Unit = {},  // Takes an Int as input for autoplay time
    onCodeVisibilityToggleRequest: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    var autoPlayTimeInSeconds by rememberSaveable { mutableStateOf<Int?>(null) }
    var inputTime by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(autoPlayTimeInSeconds) {
        autoPlayTimeInSeconds?.let { time ->
            if (time > 0) {
                while (true) {
                    delay(time * 1000L)
                    onNext()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    if (navigationIcon != null) {
                        IconButton(onClick = onNavIconClick) {
                            Icon(
                                navigationIcon,
                                contentDescription = "navigation icon",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                actions = {
                    TopBarActions(
                        showPseudocode = showPseudocode,
                        onNext = onNext,
                        onCodeVisibilityToggleRequest = onCodeVisibilityToggleRequest,
                        onResetRequest = onResetRequest,
                        onAutoPlayRequest = { showDialog = true }
                    )
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }

    // AutoPlay dialog
    if (showDialog) {
        AutoPlayDialog(
            inputTime = inputTime,
            onInputChange = { inputTime = it },
            onConfirm = {
                val second = inputTime.toIntOrNull()
                if (second != null) {
                    onAutoPlayRequest(second)
                    autoPlayTimeInSeconds=second
                    showDialog = false
                }
            },
            onCancel = { showDialog = false }
        )
    }
}

// Function to handle the dialog for autoplay
@Composable
fun AutoPlayDialog(
    inputTime: String,
    onInputChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Set Autoplay Time") },
        text = {
            Column {
                Text("Enter time in second")
                Spacer(Modifier.height(8.dp))
                TextField(
                    value = inputTime,
                    onValueChange = onInputChange,
                    label = { Text("Second") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
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

// Function to handle the autoplay icon button and trigger dialog
@Composable
fun AutoPlayIconButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.Timer,
            contentDescription = "automatic play",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

// Function to group all the top bar action buttons
@Composable
fun TopBarActions(
    showPseudocode: Boolean,
    onNext: () -> Unit,
    onCodeVisibilityToggleRequest: () -> Unit,
    onResetRequest: () -> Unit,
    onAutoPlayRequest: () -> Unit
) {
    IconButton(onClick = onNext) {
        Icon(Icons.Filled.SkipNext, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
    }
    Spacer(Modifier.width(8.dp))
    IconButton(onClick = onCodeVisibilityToggleRequest) {
        Icon(
            imageVector = if (showPseudocode) Icons.Default.CodeOff else Icons.Default.Code,
            contentDescription = "code on off",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    Spacer(Modifier.width(8.dp))
    IconButton(onClick = onResetRequest) {
        Icon(Icons.Default.Replay, contentDescription = "reset", tint = MaterialTheme.colorScheme.primary)
    }
    Spacer(Modifier.width(8.dp))
    AutoPlayIconButton(onClick = onAutoPlayRequest)
}


