package feature.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import core_ui.GlobalMessenger
import tree_traversal.presentation.TreeSimulationScreen


@Composable
fun MyApplication() {
    val hostState = remember { SnackbarHostState() }
    AppTheme {
        LaunchedEffect(Unit){

            GlobalMessenger.messageToUI.collect{msg->
                if(msg!=null){
                    hostState.showSnackbar(msg)
                }

            }

        }
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = hostState)
            },
        ) {
            TreeSimulationScreen{}
           // DijkstraSimulationScreen{}
           // PrimsSimulationScreen{}
          // BFSSimulation{}
           // TopologicalSort{}
           // DFSSimulation{}
           // MainNavHost()
          //BubbleSortRoute{}
       //     BinarySearchRoute{}
//            LinearSearchRoute(
//                navigationIcon = {
//
//                }
//            )
        }


    }


}
