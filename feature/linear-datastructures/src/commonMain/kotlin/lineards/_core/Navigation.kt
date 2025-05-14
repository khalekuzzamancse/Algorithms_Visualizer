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
import lineards.DiContainer
import kotlin.reflect.KClass

object Navigation {
    const val INPUT_ROUTE = "LinearSearchScreenInput"
    const val LINEAR_SEARCH_ROUTE = "LinearSearchScreen"

}

internal class MyViewModel : ViewModel(){
    val controller=DiContainer.lsSearchController()
}
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

fun NavHostController.createNavGraph(
    navigationIcon:@Composable ()->Unit={}
): NavGraph {
    return createGraph(startDestination = "main_flow") {
        val navController=this@createNavGraph
        navigation(
            startDestination = Navigation.INPUT_ROUTE,
            route = "main_flow"
        ) {
            composable(Navigation.INPUT_ROUTE) { entry ->
                val viewModel = entry.sharedViewModel<MyViewModel>(navController, "main_flow") {
                    MyViewModelFactory()
                }
                SearchInputView(
                    navigationIcon = navigationIcon,
                    onStartRequest = { array, target ->
                        viewModel.controller.onInput(array,target)
                        navController.navigate(Navigation.LINEAR_SEARCH_ROUTE)
                    }
                )
            }

            composable(Navigation.LINEAR_SEARCH_ROUTE) { entry ->
                val viewModel = entry.sharedViewModel<MyViewModel>(navController, "main_flow") {
                    MyViewModelFactory()
                }
                Route(
                    modifier = Modifier,
                    controller = viewModel.controller,
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ){
                            Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "back")
                        }
                    }
                )
            }
        }
    }
}



internal class MyViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
            return MyViewModel() as T
    }
}

@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
    parentRoute: String,
    crossinline factory: () -> ViewModelProvider.Factory
): VM {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(parentRoute)
    }
    return viewModel(viewModelStoreOwner = parentEntry, factory = factory())
}
