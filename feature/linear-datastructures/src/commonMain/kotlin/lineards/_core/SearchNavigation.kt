@file:Suppress("className")

package lineards._core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.createGraph
import core.ui.core.SearchInputView
import kotlin.reflect.KClass

private object _RouteNames {
    const val INPUT_ROUTE = "InputScreen"
    const val VISUALIZATION_ROUTE = "VisualizationScreen"
}

private   class _SearchViewModel(val controller: SearchRouteControllerBase) : ViewModel()

/**
 * This navigation graph scopes the MyViewModel to the "main_flow" navigation graph
 * using a shared ViewModelStoreOwner. This ensures that MyViewModel is created only once
 * and shared across all composable destinations within "main_flow".
 *
 * To achieve this:
 * - Both INPUT_ROUTE and LINEAR_SEARCH_ROUTE are nested inside a `navigation()` block with route "main_flow".
 * - sharedViewModel() uses navController.getBackStackEntry("main_flow") to get a shared ViewModelStoreOwner.
 *
 * This prevents a new ViewModel from being created for each composable.
 */

internal fun NavHostController.createSearchNavGraph(
    controller: SearchRouteControllerBase,
    navigationIcon: @Composable () -> Unit = {}
): NavGraph {
    return createGraph(startDestination = "main_flow") {
        val navController = this@createSearchNavGraph
        navigation(
            startDestination = _RouteNames.INPUT_ROUTE,
            route = "main_flow"
        ) {
            composable(_RouteNames.INPUT_ROUTE) { entry ->
                val viewModel = entry._sharedViewModel<_SearchViewModel>(navController, "main_flow") {
                    _MyViewModelFactory(controller)
                }
                SearchInputView(
                    navigationIcon = navigationIcon,
                    onStartRequest = { array, target ->
                        viewModel.controller.onInput(array, target)
                        navController.navigate(_RouteNames.VISUALIZATION_ROUTE)
                    }
                )
            }

            composable(_RouteNames.VISUALIZATION_ROUTE) { entry ->
                val viewModel = entry._sharedViewModel<_SearchViewModel>(navController, "main_flow") {
                    _MyViewModelFactory(controller)
                }
                Route(
                    modifier = Modifier,
                    controller = viewModel.controller,
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            }
        }
    }
}


private class _MyViewModelFactory(
    val controller: SearchRouteControllerBase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        return _SearchViewModel(controller) as T
    }
}

@Composable
private  inline fun <reified VM : ViewModel> NavBackStackEntry._sharedViewModel(
    navController: NavHostController,
    parentRoute: String,
    crossinline factory: () -> ViewModelProvider.Factory
): VM {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(parentRoute)
    }
    return viewModel(viewModelStoreOwner = parentEntry, factory = factory())
}
