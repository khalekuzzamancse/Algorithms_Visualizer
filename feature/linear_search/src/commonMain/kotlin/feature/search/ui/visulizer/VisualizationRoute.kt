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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import feature.search.ui.visulizer.components.ArraySection
import feature.search.ui.visulizer.components.PseudoCodeSection
import feature.search.ui.visulizer.contract.SimulationState
import feature.search.ui.visulizer.controller.UIController
import layers.ui.common_ui.ControlSection

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun <T : Comparable<T>> VisualizationRoute(
    modifier: Modifier = Modifier,
    cellSize: Dp,
    uiController: UIController<T>,
    onResetRequest:()->Unit,
    onAutoPlayRequest:()->Unit,
) {
    val arrayController = uiController.arrayController
    val algoController = uiController.algoController
    val currentIndex = uiController.currentIndex.collectAsState(null).value
    val endedState = algoController.algoState.collectAsState().value


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
                onCodeVisibilityToggleRequest = uiController::togglePseudocodeVisibility,
                onResetRequest =onResetRequest,
                onAutoPlayRequest = onAutoPlayRequest
            )
            Spacer(Modifier.height(64.dp))
            if (endedState is SimulationState.Finished){
                _ResultSummary(
                    foundedIndex = endedState.foundedIndex,
                    comparisons = endedState.comparisons
                )
                Spacer(Modifier.height(64.dp))
            }
            ArraySection(uiController.list, cellSize, arrayController, currentIndex)
        }
        AnimatedVisibility(uiController.showPseudocode.collectAsState().value) {
            PseudoCodeSection(uiController.pseudocode.collectAsState().value)
        }

    }


}

@Composable
private fun _ResultSummary(
    foundedIndex: Int,
    comparisons: Int
) {
    val isSuccess = foundedIndex!= -1

        Column {
            Text(if (isSuccess)"Successfully Search" else "UnSuccessfully Search")
            if (isSuccess){
                Text("Founded at index : $foundedIndex")
            }
            Text("Total comparisons:$comparisons")

        }

}
