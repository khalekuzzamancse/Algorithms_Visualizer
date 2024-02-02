package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.other

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.section.tutorial.TutorialSection
import com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data.repoisitory.TutorialRepositoryImpl

@Composable
fun TheorySection() {
    var theory by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        TutorialRepositoryImpl().getTutorialTheory().collect {
            theory = it.richString
        }
    }
    TutorialSection(theory)
}