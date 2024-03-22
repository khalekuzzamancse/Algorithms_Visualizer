package selection_sort.ui.route

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import selection_sort.ui.visulizer.controller.ui.UIController
import selection_sort.ui.visulizer.controller.ui.components.ArraySection
import selection_sort.ui.visulizer.controller.ui.components.PseudoCodeSection
import selection_sort.ui.visulizer.controller.ui.components._VariableSection
import layers.ui.common_ui.controll_section.ControlSection


@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun <T:Comparable<T>>VisualizationRoute(
    cellSize: Dp,
    uiController: UIController<T>
) {
    val arrayController = uiController.arrayController
    val algoController = uiController.algoController
    val i = uiController.i.collectAsState(null).value
    val minIndex=uiController.minIndex.collectAsState(null).value

    FlowRow(
        Modifier.verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ControlSection(
            onNext = algoController::next,
            showPseudocode = uiController.showPseudocode.collectAsState().value,
            onCodeVisibilityToggleRequest = uiController::togglePseudocodeVisibility
        )
        Spacer(Modifier.height(64.dp))
        ArraySection(
            list = uiController.list,
            cellSize = cellSize,
            arrayController = arrayController,
            i = i,
            minIndex = minIndex,
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

