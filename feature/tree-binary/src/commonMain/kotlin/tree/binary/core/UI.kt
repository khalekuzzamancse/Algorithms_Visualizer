package tree.binary.core

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.max

/**
 * To follow SRP enough to hold the id, not any additional information
 * @param center null means want to hide , do not draw it
 */
data class BaseNode(
    var left:BaseNode? = null,
    var right:BaseNode? = null,
    val label:String,
    val id: String=label,
    var center: Offset?= Offset.Zero,
    val color: Color=Color.Blue
) {

    fun getDepth(): Int {
        val leftDepth = left?.getDepth() ?: 0
        val rightDepth = right?.getDepth() ?: 0
        return 1 + max(leftDepth, rightDepth)
    }

}
@Composable
fun SpacerHorizontal(width: Int) = Spacer(Modifier.width(width.dp))

@Composable
fun SpacerVertical(height: Int) = Spacer(Modifier.height(height.dp))