package com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.linear_search.ui.other.tutorial

import androidx.compose.runtime.Composable
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.section.tutorial.TutorialContent
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.section.steps.TutorialStepsNPseudoCodeSection

@Composable
fun StepsSectionPreview() {
    // Example preview with dummy data
    val dummySteps = TutorialContent.Steps(
        steps = listOf(
            "Step 1: Start",
            "Step 2: Initialize the search key",
            "Step 3: Begin iterating through the array",
            "Step 4: Compare the current element with the search key",
            "Step 5: If a match is found, return the index",
            "Step 6: If the end of the array is reached and no match is found, return -1",
            "Step 7: End"
        ),
        pseudocode = """
        function linearSearch(arr, key):
            for i from 0 to length(arr) - 1:
                if arr[i] equals key:
                    return i // Return the index if key is found
            return -1 // Return -1 if key is not found
    """.trimIndent()
    )

    TutorialStepsNPseudoCodeSection(steps = dummySteps)
}