package layers.ui.common_ui.common.ui.custom_navigation_item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Composable
internal fun  NavigationItemLayout(
    modifier: Modifier = Modifier,
    visibilityDelay:Long=0,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    onFocusing: () -> Unit = {},
    props: NavigationItemProps = NavigationItemProps(
        focusedColor = MaterialTheme.colorScheme.errorContainer,
        unFocusedColor = MaterialTheme.colorScheme.primaryContainer)
) {
    var show by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(visibilityDelay)
            show = true
            break
        }
    }
    AnimatedVisibility(
        visible = show
    ) {
        NavigationItemLayoutCore(
            modifier = modifier,
            label = label,
            selected = selected,
            onClick = onClick,
            onFocusing = onFocusing,
            props = props,
            icon = icon
        )
    }
}



@Composable
private fun NavigationItemLayoutCore(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    onFocusing: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    props: NavigationItemProps = NavigationItemProps(
        focusedColor = MaterialTheme.colorScheme.errorContainer,
        unFocusedColor = MaterialTheme.colorScheme.primaryContainer,

        )
) {

    var focusing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    scope.launch {
        interactionSource.interactions.collect {
            //hover for non touch desktop and web ,press for touch devices
            focusing = it is HoverInteraction.Enter

        }

    }
    if (focusing) {
        onFocusing()
    }
    val backgroundColor by animateColorAsState(
        targetValue = if (focusing) props.focusedColor else props.unFocusedColor
    )

    val selectionColor = MaterialTheme.colorScheme.primary
    Surface(
        shadowElevation = 2.dp,
        selected = selected,
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Tab },
        shape = props.shape,
        color = if(selected) selectionColor else backgroundColor,
        interactionSource = interactionSource,
    ) {
        Row(
            Modifier.drawBehind {
                if (focusing) {
                    drawRect(
                        color = selectionColor,
                        style = Stroke(width = 2f)
                    )
                }
            }.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Spacer(Modifier.width(12.dp))
            Text(text = label)
        }
    }
}
