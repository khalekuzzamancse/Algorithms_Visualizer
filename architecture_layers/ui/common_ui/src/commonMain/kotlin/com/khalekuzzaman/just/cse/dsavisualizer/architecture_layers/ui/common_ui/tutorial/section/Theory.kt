package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.section

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.AlgorithmTutorial
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.TutorialContent

@Composable
fun TutorialTheorySection(
    modifier: Modifier=Modifier,
    content: AlgorithmTutorial
) {
    Column(modifier) {
        AlgorithmTutorialUI(content)
    }

}

