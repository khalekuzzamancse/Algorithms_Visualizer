package binary_search.ui.destinations

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.array.newdd.array.controller.ArrayController
import layers.ui.common_ui.ControlSection
import layers.ui.common_ui.decorators.tab_layout.TabDestination
import layers.ui.common_ui.decorators.tab_layout.TabLayoutDecorator
import binary_search.PackageLevelAccess
import binary_search.domain.Pseudocode
import binary_search.domain.VisualizationState
import binary_search.ui.BinarySearchViewModel
import binary_search.ui.components.ArraySection
import binary_search.ui.components.PseudoCodeSection
import binary_search.ui.components.TopBar
import binary_search.ui.components._ResultSummary

@OptIn(PackageLevelAccess::class)//okay to use  within the UI layer
@PackageLevelAccess //avoid to access other layer such domain or data/infrastructure
@Composable
internal fun <T : Comparable<T>> VisualizationDestination(
    modifier: Modifier = Modifier,
    arrayCellSize: Dp,
    viewModel: BinarySearchViewModel<T>,
    onExitRequest: () -> Unit,
    onResetRequest: () -> Unit,
    onAutoPlayRequest: () -> Unit,
) {

    val tabController = viewModel.tabController
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
                                arrayElements = viewModel.elements.collectAsState().value,
                                arrayController = viewModel.arrayController.collectAsState().value,
                                endState = viewModel.endState.collectAsState(null).value,
                                pseudocode = viewModel.pseudocode.collectAsState().value,
                                low = viewModel.low.collectAsState().value,
                                high = viewModel.high.collectAsState().value,
                                mid = viewModel.mid.collectAsState().value,
                                onNext = viewModel::onNext,
                                onResetRequest = onResetRequest,
                                onAutoPlayRequest = onAutoPlayRequest
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
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun <T : Comparable<T>> _VisualizationDestination(
    modifier: Modifier = Modifier,
    cellSize: Dp,
    arrayElements: List<T>,
    arrayController: ArrayController<T>,
    endState: VisualizationState.Finished?,
    pseudocode: List<Pseudocode.Line>?,//can be hide and show
    low: Int?,
    high: Int?,
    mid: Int?,
    onNext: () -> Unit,
    onResetRequest: () -> Unit,
    onAutoPlayRequest: () -> Unit,
) {
    var showPseudocode by remember { mutableStateOf(true) }
    //okay to store pseudocode as locally,to avoid extra state keep to present
    //because this state does not need to preserve on activity recreation.
    //because this not a important state so if we lost it,then it's okay.
    FlowRow(
        modifier = modifier,
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
                onCodeVisibilityToggleRequest = { showPseudocode=! showPseudocode },
                onResetRequest = onResetRequest,
                onAutoPlayRequest = onAutoPlayRequest
            )
            Spacer(Modifier.height(16.dp))
            ArrayNResultNCodeSection(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()), cellSize = cellSize, arrayElements = arrayElements,
                arrayController = arrayController, endState = endState, pseudocode = pseudocode,
                low = low, high = high, mid = mid, showPseudocode = showPseudocode
            )

        }
    }

}

@OptIn(PackageLevelAccess::class)////okay to use  within the UI layer
@Composable
private fun <T> ArrayNResultNCodeSection(
    modifier: Modifier = Modifier,
    cellSize: Dp,
    arrayElements: List<T>,
    arrayController: ArrayController<T>,
    endState: VisualizationState.Finished?,
    showPseudocode: Boolean,
    pseudocode: List<Pseudocode.Line>?,//can be hide and show
    low: Int?,
    high: Int?,
    mid: Int?,
) {


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        if (endState != null) {
            _ResultSummary(endState)
            Spacer(Modifier.height(16.dp))
        }
        ArraySection(
            list = arrayElements,
            cellSize = cellSize,
            arrayController = arrayController,
            low = low,
            high = high,
            mid = mid
        )
        AnimatedVisibility(showPseudocode) {
            pseudocode?.let { PseudoCodeSection(it) }

        }
    }

}

