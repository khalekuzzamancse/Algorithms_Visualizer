package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.section

import androidx.compose.foundation.horizontalScroll
import androidx.compose.runtime.Composable
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.TutorialContent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@Composable
fun TutorialImplementationSection(list: List<TutorialContent.Implementation>) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column (
    ){
        TabRow(selectedTabIndex = selectedTabIndex) {
            list.forEachIndexed { index, implementation ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = { Text(implementation.languageName) }
                )
            }
        }
        CodeEditor(list[selectedTabIndex].code)
    }
}

