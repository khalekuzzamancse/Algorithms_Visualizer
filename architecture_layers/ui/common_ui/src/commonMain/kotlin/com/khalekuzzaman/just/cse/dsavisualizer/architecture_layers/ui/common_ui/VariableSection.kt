package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

data class Variable(
    val name: String,
    val value: String? = null
)

@Composable
fun VariablesSection(
    variables: List<Variable>
) {
    Column {
        variables.forEach {
            if (it.value != null) {
                Section(
                    label = it.name,
                    value = it.value
                )
            }
        }
    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Section(
    label: String,
    value: String
) {
    FlowRow {
        Text(label)
        Text(" : ")
        Text(value)
    }
}
