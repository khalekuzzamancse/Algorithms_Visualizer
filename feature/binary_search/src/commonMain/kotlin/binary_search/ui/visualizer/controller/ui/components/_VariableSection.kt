package binary_search.ui.visualizer.controller.ui.components

import androidx.compose.runtime.Composable
import binary_search.ui.visualizer.contract.AlgoVariablesState
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