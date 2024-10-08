package feature.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import feature.navigation.drawer.Destination
import feature.navigation.drawer.DrawerHeader
import feature.navigation.drawer.DrawerToNavRailDecorator
import feature.navigation.drawer.NavDestinationBuilder
import feature.navigation.drawer.NavigationEvent
import kotlinx.coroutines.launch

@Composable
fun MainNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel = remember { MainViewModel() }
    val navigator = remember { Navigator(navController) }
    val scope = rememberCoroutineScope()
    var isNavRailMode by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        navController.currentBackStack.collect { entries ->
            val lastDestinationRoute =
                entries.lastOrNull { entry -> entry.destination.route != null }?.destination?.route
            val selected: Destination? =
                NavDestinationBuilder.allDestinations.find { it.route == lastDestinationRoute }
            if (selected != null)
                viewModel.select(selected)
            else
                viewModel.select(Destination.None)

        }
    }


    DrawerToNavRailDecorator(
        groups = NavDestinationBuilder.navGroups,
        controller = viewModel.controller,
        onEvent = { event ->
            if (event is NavigationEvent.Selected) {
                scope.launch { navigator.navigate(event.destination) }
            }
            if (event is NavigationEvent.NavRailNavigationMode)
                isNavRailMode = true
            if (event is NavigationEvent.DrawerNavigationMode)
                isNavRailMode = false
        },
        header = {
            DrawerHeader()
        },

        content = {
            NavHost(
                modifier = modifier,
                navController = navController,
                graph = navController.createNavGraph(
                    isNavRailMode = isNavRailMode, openDrawerRequest = viewModel::openDrawer)
            )
        }
    )

}
