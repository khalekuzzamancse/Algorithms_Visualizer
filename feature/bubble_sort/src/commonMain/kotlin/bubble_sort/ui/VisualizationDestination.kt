package bubble_sort.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import bubble_sort.ui.components.ArraySection
import bubble_sort.ui.components.PseudoCodeSection
import layers.ui.common_ui.ControlSection


@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun <T:Comparable<T>>VisualizationDestination(
    cellSize: Dp,
    viewModel: BubbleSortViewModel<T>
) {
    var showPseudocode by remember { mutableStateOf(true) }
    val arrayController = viewModel.arrayController
    val i = viewModel.i.collectAsState(null).value
    val j=viewModel.j.collectAsState(null).value


    FlowRow(
        Modifier.verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ControlSection(
            onNext =viewModel::onNext,
            showPseudocode = showPseudocode,
            onCodeVisibilityToggleRequest = { showPseudocode=!showPseudocode }
        )
        Spacer(Modifier.height(64.dp))
        ArraySection(
            list = viewModel.list.collectAsState().value,
            cellSize = cellSize,
            arrayController = arrayController.collectAsState().value,
            i = i,
            j = j,
        )
        Column {
            Spacer(Modifier.height(64.dp))
            AnimatedVisibility(showPseudocode) {
                PseudoCodeSection(viewModel.pseudocode.collectAsState().value)
            }

        }

    }


}

