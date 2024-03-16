package feature.search.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import layers.data.data.remote.TutorialRepositoryImpl
import layers.data.demo_data.linear_search.LinearSearchDemoData
import layers.ui.common_ui.tutorial.TutorialStepsNPseudoCodeSection
import layers.ui.common_ui.tutorial.TutorialContent
import layers.ui.common_ui.tutorial.TutorialImplementationSection
import layers.ui.common_ui.tutorial.TutorialSection

@Composable
internal fun TheorySection() {
    var theory by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        TutorialRepositoryImpl().getTutorialTheory().collect {
            theory = it.richString
        }
    }
    TutorialSection(theory)
}
@Composable

internal fun Implementation() {
    val data= LinearSearchDemoData.implementation
    TutorialImplementationSection(
        data.map {
            TutorialContent.Implementation(it.languageName,it.code)
        }
    )
}
@Composable
internal fun StepsSection() {
    val data=LinearSearchDemoData.steps
    TutorialStepsNPseudoCodeSection(
        steps =  TutorialContent.Steps(
            steps = data.steps,
            pseudocode = ""
        )
    )
}