package layers.ui.common_ui.pseudocode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * * Using this module own state holder to easily decouple from outer module
 * @param indentationLevel the number of space from the left side
 * @param variableState is the variable value ,at this moment
 */
data class CodeLine(
    val line: String,
    val highLighting: Boolean = false,
    val lineNumber: Int,
    val indentationLevel: Int = 0,
    val variableState: String? = null
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PseudoCodeExecutor(modifier: Modifier = Modifier, code: List<CodeLine>) {
    Column(modifier) {
        code.forEach { codeLine ->
            FlowRow(
            ) {
                Text(
                    text = codeLine.line,
                    color = if (codeLine.highLighting) Color.Red else Color.Unspecified,
                    fontSize = 15.sp
                )
                Spacer(Modifier.width(2.dp))
                // if VariableActive is active
                if (codeLine.variableState != null) {
                    Text(
                        text = "// ${codeLine.variableState}",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier=Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

        }

    }

}