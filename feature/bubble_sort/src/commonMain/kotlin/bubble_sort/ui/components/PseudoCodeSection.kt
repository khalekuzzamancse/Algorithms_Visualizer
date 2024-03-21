package bubble_sort.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bubble_sort.domain.Pseudocode
import layers.ui.common_ui.pseudocode.CodeLine
import layers.ui.common_ui.pseudocode.PseudoCodeExecutor

@Composable
 internal fun PseudoCodeSection(
    code: List<Pseudocode.Line>
) {
    PseudoCodeExecutor(
        modifier = Modifier.padding(8.dp),
        code = code.map {
            CodeLine(line = it.line, highLighting = it.highLighting, lineNumber = it.lineNumber, variableState = it.variableState)
        }
    )
}

