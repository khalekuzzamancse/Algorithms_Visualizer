package feature.search.ui.visulizer.components

import androidx.compose.runtime.Composable
import feature.search.ui.visulizer.contract.AlgoVariablesState
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