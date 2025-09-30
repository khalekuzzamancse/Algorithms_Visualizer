package lineards.quick_sort.presentation


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import core.ui.CodeViewer
import core.ui.core.ArrayInputView
import core.ui.core.SimulationScreenEvent
import core.ui.core.SimulationScreenState
import core.ui.core.SimulationSlot
import core.ui.core.array.VisualArray
import lineards._core.Route
import lineards._core.SortRouteStrategy
import lineards.bubble_sort.presentation.BubbleSortController


@Composable
fun QuickSortScreen(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit) {

    SortRouteStrategy(
        modifier=modifier,
        controller = QuickSortViewModel(),
        navigationIcon=navigationIcon,
        visualizationScreen = {viewModel,backView->
            Route(
                modifier = Modifier,
                controller = viewModel.controller,
                navigationIcon = backView
            )
        }
    )


}