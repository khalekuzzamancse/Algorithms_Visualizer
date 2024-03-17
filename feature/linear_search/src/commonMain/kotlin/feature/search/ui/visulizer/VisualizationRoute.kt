package feature.search.ui.visulizer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import feature.search.ui.visulizer.components.ArraySection
import feature.search.ui.visulizer.components.PseudoCodeSection
import feature.search.ui.visulizer.controller.UIController
import layers.ui.common_ui.ControlSection

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun <T : Comparable<T>> VisualizationRoute(
    modifier: Modifier=Modifier,
    cellSize: Dp,
    uiController: UIController<T>
) {
    val arrayController = uiController.arrayController
    val algoController = uiController.algoController
    val currentIndex = uiController.currentIndex.collectAsState(null).value

    FlowRow(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ControlSection(
                onNext = algoController::next,
                isCodeOff = uiController.showPseudocode.collectAsState().value,
                onCodeVisibilityToggleRequest = uiController::togglePseudocodeVisibility
            )
            Spacer(Modifier.height(64.dp))
            ArraySection(uiController.list, cellSize, arrayController, currentIndex)
        }
        AnimatedVisibility(uiController.showPseudocode.collectAsState().value) {
            PseudoCodeSection(uiController.pseudocode.collectAsState().value)
        }

    }


}



