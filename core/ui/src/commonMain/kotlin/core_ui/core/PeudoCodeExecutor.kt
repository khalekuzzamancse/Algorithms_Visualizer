package core_ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times

/**
 * * Using this module own state holder to easily decouple from outer module
 * @param indentationLevel the number of space from the left side
 * @param variableState is the variable value ,at this moment
 */
data class CodeLine(
    val line: AnnotatedString,
    val highLighting: Boolean = false,
    val debugText:String?=null,
    val lineNumber: Int,
    val indentationLevel: Int = 0,
    val topPaddingLevel: Int = 0,
    val variableState: AnnotatedString? = null
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PseudoCodeExecutor(modifier: Modifier = Modifier, code: List<CodeLine>) {
    val containerColor = MaterialTheme.colorScheme.onBackground
    Column(modifier.background(containerColor).padding(16.dp)) {
        code.forEachIndexed{ index,codeLine ->
            val lineNo=index+1
            val paddingStart = when (codeLine.indentationLevel) {
                1 -> 16.dp
                2 -> 24.dp
                3 -> 32.dp
                else -> 0.dp
            }
            FlowRow {
                //line number
                Text(
                    text = if (lineNo<10) "0$lineNo" else "$lineNo",//add leading 0 if number <10
                    color = if (codeLine.highLighting) MaterialTheme.colorScheme.tertiaryContainer
                    else MaterialTheme.colorScheme.background,
                    fontSize = 15.sp
                )
                Spacer(Modifier.width(4.dp))
                FlowRow(Modifier.padding(start = paddingStart, top = codeLine.topPaddingLevel * 4.dp)) {
                    Text(
                        text = codeLine.line,
                        color = if (codeLine.highLighting) MaterialTheme.colorScheme.tertiaryContainer
                        else MaterialTheme.colorScheme.background,
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.width(2.dp))
                    // if VariableActive is active
                    if (codeLine.debugText != null) {
                        Text(
                            text = "// ${codeLine.debugText}",
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

        }

    }

}