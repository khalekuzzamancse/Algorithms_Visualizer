package lineards.insertion_sort.presentation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import lineards._core.Route


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InsertionSortRoute(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit) {
    val viewModel = remember { SimulationViewModel ()}
    Route(modifier,viewModel,navigationIcon)




}
