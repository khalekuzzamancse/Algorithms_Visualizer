package lineards.bubble_sort.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import lineards._core.Route


@Composable
fun BubbleSortRoute(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit) {
    val viewModel = remember { BubbleSortViewModel() }
    Route(modifier,viewModel,navigationIcon)
}