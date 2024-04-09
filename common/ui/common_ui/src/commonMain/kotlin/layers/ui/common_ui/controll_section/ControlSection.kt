package layers.ui.common_ui.controll_section

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.CodeOff
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
/**
 * This top bar can be used to utilize the space of the top section of the screen
 * so that
 */
@Composable
fun TopBarControlSection(
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack,
    showPseudocode: Boolean = false,
    showNavigationSection:Boolean=true,
    onNavIconClick: () -> Unit = {},
    onToggleNavigationSection:()->Unit={},
    onNext: () -> Unit = {},
    onResetRequest: () -> Unit = {},
    onAutoPlayRequest: () -> Unit = {},
    onCodeVisibilityToggleRequest: () -> Unit = {},
    content: @Composable () -> Unit,
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    if (navigationIcon != null) {
                        IconButton(
                            onClick = onNavIconClick,
                        ) {
                            Icon(
                                navigationIcon,
                                "navigation icon",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },

                actions = {
                    IconButton(
                        onClick = onNext,
                    ) {
                        Icon(Icons.Filled.SkipNext, null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(Modifier.width(8.dp))
                    IconButton(
                        onClick = onCodeVisibilityToggleRequest
                    ) {
                        Icon(
                            imageVector = if (showPseudocode) Icons.Default.CodeOff else Icons.Default.Code,
                            contentDescription = "code on off",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    IconButton(
                        onClick = onResetRequest
                    ) {
                        Icon(
                            imageVector = Icons.Default.Replay,
                            contentDescription = "reset", tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(Modifier.width(8.dp))
//                    IconButton(
//                        onClick = onAutoPlayRequest
//                    ) {
//                        Icon(
//                            imageVector = Icons.Outlined.Timer,
//                            contentDescription = "automatic play",
//                            tint = MaterialTheme.colorScheme.primary
//                        )
//                    }
                    IconButton(
                        onClick = onToggleNavigationSection
                    ) {
                        Icon(
                            imageVector = if (!showNavigationSection)Icons.Outlined.ArrowDropDown else Icons.Outlined.ArrowDropUp,
                            contentDescription = "automatic play",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                }

            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }

    }


}

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