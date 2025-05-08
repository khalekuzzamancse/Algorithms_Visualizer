package core.ui.graph.editor.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.ui.graph.editor.model.GraphType


@Composable
internal fun GraphTypeInputDialog(
    supportedTypes: List<GraphType>,
    onInputComplete: (GraphType) -> Unit,
) {


    var selectedOption by remember { mutableStateOf(supportedTypes[0]) }

    Dialog(onDismissRequest = {  }) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TitleText()

                GraphTypeSelectionFlowRow(
                    options = supportedTypes,
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedOption = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ConfirmButton(
                    onConfirmClick = {
                        onInputComplete(selectedOption)
                    }
                )
            }
        }
    }
}

@Composable
private fun TitleText() {
    Text(
        text = "Select Graph Type",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GraphTypeSelectionFlowRow(
    options: List<GraphType>,
    selectedOption: GraphType,
    onOptionSelected: (GraphType) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { option ->
            GraphTypeOptionRow(
                option = option,
                selected = option == selectedOption,
                onSelect = { onOptionSelected(option) }
            )
        }
    }
}

@Composable
private fun GraphTypeOptionRow(
    option: GraphType,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onSelect)
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = option.label)
    }
}


@Composable
private fun ConfirmButton(onConfirmClick: () -> Unit) {
    Button(
        onClick = { onConfirmClick() },
        modifier = Modifier.widthIn(max=200.dp)
    ) {
        Text(text = "Confirm")
    }
}
