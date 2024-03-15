package quick_sort.ui.visulizer.controller.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import quick_sort.ui.visulizer.contract.Pseudocode
import layers.ui.common_ui.common.pseudocode.CodeLine
import layers.ui.common_ui.common.pseudocode.PseudoCodeExecutor

@Composable
 internal fun PseudoCodeSection(
    code: List<Pseudocode.Line>
) {
    PseudoCodeExecutor(
        modifier = Modifier.padding(8.dp),
        code = code.map {
            CodeLine(line = it.line, highLighting = it.highLighting, lineNumber = it.lineNumber)
        }
    )
}

