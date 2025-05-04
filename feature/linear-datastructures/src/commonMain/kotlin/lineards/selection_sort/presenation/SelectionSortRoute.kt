package lineards.selection_sort.presenation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import lineards._core.Route

@Composable
fun SelectionSortRoute(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit) {

    val viewModel = remember { SimulationViewModel() }
    Route(modifier,viewModel,navigationIcon)



}