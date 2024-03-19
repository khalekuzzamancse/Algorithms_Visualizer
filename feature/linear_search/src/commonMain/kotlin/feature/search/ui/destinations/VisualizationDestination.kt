package feature.search.ui.destinations

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import feature.search.PackageLevelAccess
import feature.search.domain.Pseudocode
import feature.search.domain.VisualizationState
import feature.search.ui.LinearSearchViewModel
import feature.search.ui.components.ArraySection
import feature.search.ui.components.PseudoCodeSection
import feature.search.ui.components.TopBar
import feature.search.ui.components._ResultSummary
import layers.ui.common_ui.ControlSection
import layers.ui.common_ui.decorators.tab_layout.TabDestination
import layers.ui.common_ui.decorators.tab_layout.TabLayoutDecorator

@OptIn(PackageLevelAccess::class)//okay to use  within the UI layer
@PackageLevelAccess //avoid to access other layer such domain or data/infrastructure
@Composable
internal fun <T : Comparable<T>> VisualizationDestination(
    modifier: Modifier = Modifier,
    arrayCellSize: Dp,
    viewModel: LinearSearchViewModel<T>,
    onExitRequest: () -> Unit,
    onResetRequest: () -> Unit,
    onAutoPlayRequest: () -> Unit,
) {

    val tabController=viewModel.tabController
    TabLayoutDecorator(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        controller = tabController,
        topBar = { TopBar(onExitRequest) },
        content = {
            Column(modifier.fillMaxWidth()) {
                AnimatedContent(tabController.selected.collectAsState(TabDestination.Visualization).value) { selected ->
                    when (selected) {
                        TabDestination.Visualization -> {
                            _VisualizationDestination(
                                modifier = Modifier.padding(top = 16.dp),
                                cellSize = arrayCellSize,
                                onResetRequest = onResetRequest,
                                onAutoPlayRequest = onAutoPlayRequest,
                                arrayController = viewModel.arrayController.collectAsState().value,
                                onNext = viewModel::onNext,
                                currentIndex = viewModel.currentIndex.collectAsState(null).value,
                                endState = viewModel.endState.collectAsState(null).value,
                                pseudocode = viewModel.pseudocode.collectAsState().value,
                                onTogglePseudocodeVisibly = viewModel::togglePseudocodeVisibility,
                                arrayElements = viewModel.elements.collectAsState().value,
                                showPseudocode = viewModel.showPseudocode.collectAsState().value
                            )
                        }

                        else -> TutorialDestinations(selected)

                    }
                }

            }
        }
    )

}

/**
 * It's totally stateless so easier to test and debug and reuse
 */
@OptIn(ExperimentalLayoutApi::class, PackageLevelAccess::class)//okay to use  within the UI layer
@Composable
private fun <T : Comparable<T>> _VisualizationDestination(
    modifier: Modifier = Modifier,
    cellSize: Dp,
    arrayElements: List<T>,
    arrayController: ArrayController<T>,
    showPseudocode: Boolean,
    endState: VisualizationState.Finished?,
    pseudocode: List<Pseudocode.Line>?,//can be hide and show
    currentIndex: Int?,
    onNext: () -> Unit,
    onTogglePseudocodeVisibly: () -> Unit,
    onResetRequest: () -> Unit,
    onAutoPlayRequest: () -> Unit,
) {

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
                onNext = onNext,// algoController::next
                showPseudocode = showPseudocode,
                onCodeVisibilityToggleRequest = onTogglePseudocodeVisibly,
                onResetRequest = onResetRequest,
                onAutoPlayRequest = onAutoPlayRequest
            )
            Spacer(Modifier.height(64.dp))
            if (endState != null) {
                _ResultSummary(
                    foundedIndex = endState.foundedIndex,
                    comparisons = endState.comparisons
                )
                Spacer(Modifier.height(64.dp))
            }
            ArraySection(arrayElements, cellSize, arrayController, currentIndex)
        }
        AnimatedVisibility(showPseudocode) {
            pseudocode?.let { PseudoCodeSection(it) }

        }

    }


}