package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial

data class AlgorithmTutorial(
    val tutorialSections: List<TutorialContent>
)
interface TutorialContent {
    data class TextContent(val text: String) : TutorialContent
    data class ImageContent(val imageUrl: String, val figureName: String) : TutorialContent
    data class VideoContent(val imageUrl: String, val figureName: String) : TutorialContent

    /**
     * @param steps the step will showed in the bullet point
     */
    data class Steps(val steps: List<String>, val pseudocode: String) : TutorialContent
    data class Implementation(
        val languageName: String,
        val code: String,
    ) : TutorialContent


}