package quick_sort.ui.visulizer.controller.ui.components

import androidx.compose.runtime.Composable
import quick_sort.ui.visulizer.contract.AlgoVariablesState
import layers.ui.common_ui.Variable
import layers.ui.common_ui.VariablesSection

@Composable
 internal fun _VariableSection(
    variables: List<AlgoVariablesState>
) {
    VariablesSection(variables.map {
        Variable(it.name, it.value)
    })

}