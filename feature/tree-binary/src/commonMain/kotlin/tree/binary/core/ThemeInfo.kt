package tree.binary.core

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance


internal object ThemeInfo {

    //Color of MaterialTheme.colorScheme.primary.toArgb()
    val operationActionColor = Color(-16750968)
    //Color of MaterialTheme.colorScheme.tertiary.toArgb()
    val normalItemColor=Color(-12490474)
    val targetItemColor=Color.Red
    val nodeColor=Color.Blue
    val processingNodeColor=Color.Yellow
}

/**
 * Return black or white based on background(this) color
 */
fun Color.contentColor() = if (this.luminance() < 0.5f) Color.White else Color.Black