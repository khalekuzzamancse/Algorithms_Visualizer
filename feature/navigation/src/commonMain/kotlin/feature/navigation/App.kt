package feature.navigation


import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.compose.AppTheme
import feature.navigation.home.HomeDestination
import feature.navigation.home.dashboard.Destination
import kotlinx.coroutines.launch
import mst.ui.PrimsSimulationScreen

@Composable
fun MyApplication() {
    AppTheme {
       //   NavHost()
      //  DijkstraSimulationScreen()
        // GraphEditor()
        PrimsSimulationScreen()

    }


}

@Composable
private fun NavHost() {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var destination by remember { mutableStateOf(Destination.Home) }
    val gotoHome: () -> Unit = {
        destination = Destination.Home
    }
    val navigateTo: (Destination) -> Unit = {
        if (destination == Destination.None) {
            destination = Destination.Home
            scope.launch {
                scope.launch {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(
                        message = "Not implemented yet...",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
        destination = it
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { scaffoldPadding ->
        Box(modifier = Modifier.padding(scaffoldPadding)) {
            AnimatedContent(destination) { selected ->
                when (selected) {
//                    Destination.LinearSearch -> LinearSearchRoute(onExitRequest = gotoHome)
//                    Destination.BinarySearch -> BinarySearchRoute(onExitRequest = gotoHome)
//                    Destination.BubbleSort -> BubbleSortRoute(onExitRequest = gotoHome)
//                    Destination.SelectionSort -> SelectionSortSimulator(onExitRequest = gotoHome)
//                    Destination.BFS -> BFSSimulation()
//                    Destination.DFS -> DFSSimulation()
//                    Destination.TopologicalSort -> TopologicalSortSimulation()
                    else -> {
                        HomeDestination(
                            onDestinationClick = navigateTo,
                            onContactUsClick = { navigateTo(Destination.None) },
                            onAboutUsRequest = { navigateTo(Destination.None) }
                        )
                    }
                }
            }
        }
    }
}
