package lineards._core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navigation
import core.lang.ComposeView
import core.lang.VoidCallback

@Composable
fun FeatureNavHost(
    modifier: Modifier = Modifier,
    navigate:(VoidCallback)->Unit,
    inputScreen: ComposeView,
    visualizationScreen: @Composable (ComposeView) -> Unit,
) {
    val navController = rememberNavController()
    LaunchedEffect(Unit){
        navigate{
            navController.navigate(Routes.VISUALIZATION)
        }
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        graph = navController.createTwoScreenFlow(inputScreen){
            visualizationScreen{
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
        }
    )
}

private fun NavHostController.createTwoScreenFlow(
    inputScreen: @Composable () -> Unit,
    visualizationScreen: @Composable () -> Unit
): NavGraph {
    return createGraph(startDestination = Routes.PARENT) {
        navigation(startDestination = Routes.INPUT, route = Routes.PARENT) {
            composable(Routes.INPUT) {
                inputScreen()
            }
            composable(Routes.VISUALIZATION) {
                visualizationScreen()
            }
        }
    }
}

private object Routes {
    const val PARENT = "main_flow"
    const val INPUT = "InputScreen"
    const val VISUALIZATION = "VisualizationScreen"
}





//@Composable
//inline fun <reified VM : ViewModel> NavBackStackEntry.sharedViewModel(
//    navController: NavHostController,
//    parentRoute: String,
//    crossinline factory: () -> ViewModelProvider.Factory
//): VM {
//    val parentEntry = remember(this) {
//        navController.getBackStackEntry(parentRoute)
//    }
//    return viewModel(viewModelStoreOwner = parentEntry, factory = factory())
//}
//
//class ControllerViewModelFactory<VM : ViewModel, C>(
//    private val controller: C,
//    private val creator: (C) -> VM
//) : ViewModelProvider.Factory {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
//        return creator(controller) as T
//    }
//}
