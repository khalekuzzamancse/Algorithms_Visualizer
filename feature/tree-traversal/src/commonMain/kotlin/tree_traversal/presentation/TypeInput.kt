package tree_traversal.presentation
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
import tree_traversal.domain.model.TraversalType

@Composable
internal fun TypeInputDialog(
    onInputComplete: (TraversalType) -> Unit,
) {
    val supportedTypes: List<TraversalType> = remember { TraversalType.entries.toList() }
    var selectedOption by remember { mutableStateOf(TraversalType.BFS) }

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
                _TitleText()
                _TypeSelectionRow(
                    options = supportedTypes,
                    selectedOption = selectedOption,
                    onOptionSelected = { selectedOption = it }
                )

                Spacer(modifier = Modifier.height(16.dp))
                _ConfirmButton(
                    onConfirmClick = {
                        onInputComplete(selectedOption)
                    }
                )
            }
        }
    }
}

@Composable
private fun _TitleText() {
    Text(
        text = "Select Traversal Type",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun _TypeSelectionRow(
    options: List<TraversalType>,
    selectedOption: TraversalType,
    onOptionSelected: (TraversalType) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { option ->
            _TypeOptionRow(
                option = option,
                selected = option == selectedOption,
                onSelect = { onOptionSelected(option) }
            )
        }
    }
}

@Composable
private fun _TypeOptionRow(
    option: TraversalType,
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
private fun _ConfirmButton(onConfirmClick: () -> Unit) {
    Button(
        onClick = { onConfirmClick() },
        modifier = Modifier.widthIn(max=200.dp)
    ) {
        Text(text = "Confirm")
    }
}
