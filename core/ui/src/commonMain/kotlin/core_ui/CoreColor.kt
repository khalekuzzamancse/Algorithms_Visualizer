package core_ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

fun Color.textColor()=if(this.luminance()<0.5f) Color.White else Color.Black
/**
 * - These color works for both dark and white and need to scope of composable function
 * that is why Defining here
 * - These may be need to use by multiple class such as node editor,edge editor,...etc that is
 * why maintain single place of truth
 * - Maintain the color is important because based on color will identify which edge/node
 * is selected or not
 * - and selection is important state to do some operation such as dragging, removing etc
 */
 object GlobalColors {
    internal val turquoiseBlue = Color(0xFF00B8D4)
    internal val sunsetOrange = Color(0xFFFF7043)
    val selectedNodeColor=turquoiseBlue
    val activeNodeColor =sunsetOrange
    val selectedEdgePointColor= turquoiseBlue
    val edgeColor= sunsetOrange
    private val limeGreen = Color(0xFFC0CA33)
    val nodeColor= limeGreen

}
object ArrayColor {
    val CELL_COLOR=Color.Transparent
    val CELL_BORDER=GlobalColors.turquoiseBlue
    val ELEMENT=GlobalColors.sunsetOrange
    val POINTER_1= Color.White
    val CURRENT_ELEMENT=GlobalColors.sunsetOrange
    val FOUND_ELEMENT_COLOR=GlobalColors.turquoiseBlue
    val VISITED_ELEMENT_COLOR=GlobalColors.sunsetOrange
}

// Define a data class representing a theme for the CodeViewer
data class CodeViewerTheme(
    val name:String,
    val background: Color,
    val toolbarBackground: Color,
    val border: Color,
    val text: Color,
    val comment: Color,
    val highlight: Color
)

object CodeViewerColor {

    private fun blueishDark(): CodeViewerTheme = CodeViewerTheme(
        name = "Blueish Dark",
        background = Color(0xFF1a198e),
        toolbarBackground = Color(0xFF2a29b0),
        border = Color(0xFF2422ba),
        text = Color(0xFFfdffff),
        comment = Color(0xFF007c7c),
        highlight = Color(0xFFff0081)
    )

    private fun lightKaryPro(): CodeViewerTheme = CodeViewerTheme(
        name = "Light Kary Pro",
        background = Color(0xFFF6F6F6),
        toolbarBackground = Color(0xFFE1E1E1),
        border = Color(0xFFE1E1E1),
        text = Color(0xFF2E2E2E),
        comment = Color(0xFF7B8A99),
        highlight = Color(0xFFD9730D)
    )

    private fun solarizedDark(): CodeViewerTheme = CodeViewerTheme(
        name = "Solarized Dark",
        background = Color(0xFF002B36),
        toolbarBackground = Color(0xFF073642),
        border = Color(0xFF586E75),
        text = Color(0xFF839496),
        comment = Color(0xFF586E75),
        highlight = Color(0xFFB58900)
    )

    private fun dracula(): CodeViewerTheme = CodeViewerTheme(
        name = "Dracula",
        background = Color(0xFF282A36),
        toolbarBackground = Color(0xFF44475A),
        border = Color(0xFF6272A4),
        text = Color(0xFFF8F8F2),
        comment = Color(0xFF6272A4),
        highlight = Color(0xFFFF79C6)
    )

    private fun githubLight(): CodeViewerTheme = CodeViewerTheme(
        name = "GitHub Light",
        background = Color(0xFFFFFFFF),
        toolbarBackground = Color(0xFFF6F8FA),
        border = Color(0xFFD1D5DA),
        text = Color(0xFF24292E),
        comment = Color(0xFF6A737D),
        highlight = Color(0xFF005CC5)
    )

    fun availableThemes() = listOf(
        blueishDark(),
        lightKaryPro(),
        solarizedDark(),
        dracula(),
        githubLight()
    )
}
