package layers.ui.common_ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.CodeOff
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ControlSection(
    modifier: Modifier = Modifier,
    showPseudocode: Boolean = false,
    onNext: () -> Unit = {},
    onResetRequest: () -> Unit = {},
    onAutoPlayRequest: () -> Unit = {},
    onCodeVisibilityToggleRequest: () -> Unit = {},
) {

    FlowRow(modifier) {
        Button(
            onClick = onNext,
        ) {
            Icon(Icons.Filled.SkipNext, null)
            Spacer(Modifier.width(4.dp))
            Text("NEXT")
        }
        Spacer(Modifier.width(8.dp))
        Button(
            onClick = onCodeVisibilityToggleRequest
        ) {
            Icon(
                imageVector = if (showPseudocode) Icons.Default.CodeOff else Icons.Default.Code,
                contentDescription = "code on off"
            )
            Spacer(Modifier.width(4.dp))
            Text("CODE")
        }
        Spacer(Modifier.width(8.dp))
        Button(
            onClick = onResetRequest
        ) {
            Icon(
                imageVector = Icons.Default.Replay,
                contentDescription = "reset"
            )
            Spacer(Modifier.width(4.dp))
            Text("RESET")
        }
        Spacer(Modifier.width(8.dp))
        Button(
            onClick = onAutoPlayRequest
        ) {
            Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = "automatic play"
            )
            Spacer(Modifier.width(4.dp))
            Text("Auto")
        }


    }


}