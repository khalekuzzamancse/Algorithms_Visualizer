package selection_sort.ui.components

import androidx.compose.runtime.Composable
import layers.ui.common_ui.Variable
import layers.ui.common_ui.VariablesSection
import selection_sort.domain.AlgoVariablesState

@Composable
 internal fun _VariableSection(
    variables: List<AlgoVariablesState>
) {
    VariablesSection(variables.map {
        Variable(it.name, it.value)
    })

}