package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.tutorial

import androidx.compose.runtime.Composable
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.AlgorithmTutorial
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.TutorialContent
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.section.TutorialTheorySection

@Composable
fun TutorialTheoryPreview() {
    val dummyTutorial = AlgorithmTutorial(
        tutorialSections = listOf(
            TutorialContent.TextContent(
                "For instance, in the given animated diagram, " +
                        "we are searching for an element 33. Therefore, the linear search method" +
                        " searches for it sequentially from the very " +
                        "first element until it finds a match. This returns a successful search."
            ),
            TutorialContent.ImageContent("https://www.tutorialspoint.com/data_structures_algorithms/images/linear_search.gif",""),
            TutorialContent.TextContent("In the same diagram, if we have to search for an element 46, then it returns an unsuccessful search since 46 is not present in the input."),

            )
    )
    TutorialTheorySection(
        content = dummyTutorial
    )
}