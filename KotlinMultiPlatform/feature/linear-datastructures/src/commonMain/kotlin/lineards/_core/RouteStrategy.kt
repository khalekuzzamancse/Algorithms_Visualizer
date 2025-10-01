package lineards._core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import core.lang.ComposeView
import core.lang.VoidCallback
import core.ui.core.ArrayInputView
import core.ui.core.SearchInputView

@Composable
fun SearchRouteStrategy(
    modifier: Modifier = Modifier,
    controller: SearchRouteControllerBase,
    navigationIcon: ComposeView,
    visualizationScreen: @Composable (SearchViewModel, ComposeView) -> Unit
) {
    var navigateToVisualization:VoidCallback?= remember { null }
    val viewModel= viewModel { SearchViewModel(controller) }
    FeatureNavHost(
        modifier=modifier,
        navigate = { navigateToVisualization=it},
        inputScreen = {
            SearchInputView(
                navigationIcon = navigationIcon,
                onStartRequest = { array, target ->
                    viewModel.controller.onInput(array, target)
                    navigateToVisualization?.invoke()
                }
            )
        },
        visualizationScreen ={backView->
            visualizationScreen(viewModel,backView)
        }
    )

}


@Composable
fun SortRouteStrategy(
    modifier: Modifier = Modifier,
    controller: SortRouteController,
    navigationIcon: ComposeView,
    visualizationScreen: @Composable (SortViewModel, ComposeView) -> Unit
) {
    var navigateToVisualization:VoidCallback?= remember { null }
    val viewModel= viewModel { SortViewModel(controller) }
    FeatureNavHost(
        modifier=modifier,
        navigate = { navigateToVisualization=it},
        inputScreen = {
            ArrayInputView(
                navigationIcon = navigationIcon,
                onConfirm = { array ->
                    viewModel.controller.onListInputted(array)
                    navigateToVisualization?.invoke()
                }
            )
        },
        visualizationScreen ={backView->
            visualizationScreen(viewModel,backView)
        }
    )

}
class SortViewModel(val controller: SortRouteController) : ViewModel()
class SearchViewModel(val controller: SearchRouteControllerBase) : ViewModel()