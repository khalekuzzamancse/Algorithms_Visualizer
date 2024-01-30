package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun VariablesSection(
    currentElement: Int? = null,
    isMatched: Boolean? = null,
    currentIndex: Int? = null,
) {
    Column {
        if (currentElement != null) {
            Section(
                label = "Current",
                value = "$currentElement"
            )
        }
        if (isMatched != null) {
            Section(
                label = "isMatched",
                value = "$isMatched"
            )
        }
        if (currentIndex != null) {
            Section(
                label = "currentIndex",
                value = "$currentIndex"
            )
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
