package feature.search.ui.visulizer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.search.ui.visulizer.contract.Pseudocode
import layers.ui.common_ui.pseudocode.CodeLine
import layers.ui.common_ui.pseudocode.PseudoCodeExecutor

@Composable
internal fun PseudoCodeSection(
    code: List<Pseudocode.Line>
) {
    PseudoCodeExecutor(
        modifier = Modifier
            .padding(8.dp)
            .widthIn(max=500.dp)
            .heightIn(max=400.dp)
            .fillMaxHeight()
            .fillMaxWidth()

        ,
        code = code.map {
            CodeLine(
                line = it.line,
                highLighting = it.highLighting,
                lineNumber = it.lineNumber,
                indentationLevel = it.indentationLevel,
                variableState = it.variableState,
                topPaddingLevel = it.topPaddingLevel
            )
        }
    )
}

