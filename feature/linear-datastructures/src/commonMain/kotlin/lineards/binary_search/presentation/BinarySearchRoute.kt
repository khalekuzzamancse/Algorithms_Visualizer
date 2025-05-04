package lineards.binary_search.presentation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import lineards._core.Route

@Composable
fun BinarySearchRoute(modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit) {
    val viewModel= remember { BSViewModel() }
    Route(modifier,viewModel,navigationIcon)
}
