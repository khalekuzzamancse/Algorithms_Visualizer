package layers.ui.common_ui.common.tutorial.section.tutorial
interface TutorialContent {

    /**
     * @param steps the step will showed in the bullet point
     */
    data class Steps(val steps: List<String>, val pseudocode: String) : TutorialContent
    data class Implementation(
        val languageName: String,
        val code: String,
    ) : TutorialContent


}