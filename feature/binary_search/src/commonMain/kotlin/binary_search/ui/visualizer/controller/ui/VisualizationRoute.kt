package binary_search.ui.visualizer.controller.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import binary_search.ui.visualizer.controller.AlgoControllerImpl
import binary_search.ui.visualizer.controller.ui.components.ArraySection
import binary_search.ui.visualizer.controller.ui.components.PseudoCodeSection
import binary_search.ui.visualizer.controller.ui.components._VariableSection
import layers.ui.common_ui.ControlSection


@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun <T:Comparable<T>>VisualizationRoute(
    cellSize: Dp,
    uiController: UIController<T>
) {
    val arrayController = uiController.arrayController
    val algoController = uiController.algoController
    val low = uiController.low.collectAsState(null).value
    val high=uiController.high.collectAsState(null).value
    val mid=uiController.mid.collectAsState(null).value

    FlowRow(
        Modifier.verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ControlSection(
            onNext = algoController::next,
            isCodeOff = uiController.showPseudocode.collectAsState().value,
            onCodeVisibilityToggleRequest = uiController::togglePseudocodeVisibility
        )
        Spacer(Modifier.height(64.dp))
        ArraySection(
            list = uiController.list,
            cellSize = cellSize,
            arrayController = arrayController,
            low = low,
            high = high,
            mid=mid
        )
        Column {
            _VariableSection(uiController.variables.collectAsState(emptyList()).value)
            Spacer(Modifier.height(64.dp))
            AnimatedVisibility(uiController.showPseudocode.collectAsState().value) {
                PseudoCodeSection(uiController.pseudocode.collectAsState().value)
            }

        }

    }


}

