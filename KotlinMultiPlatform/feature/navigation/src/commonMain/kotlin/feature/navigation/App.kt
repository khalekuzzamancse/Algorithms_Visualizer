package feature.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import core.ui.GlobalMessenger


@Composable
fun MyApplication() {
    val hostState = remember { SnackbarHostState() }
    AppTheme {
        LaunchedEffect(Unit){

            GlobalMessenger.messageToUI.collect{ msg->
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
            MainNavHost()
           // TreeSimulationScreen{}
           // DijkstraSimulationScreen{}
           // PrimsSimulationScreen{}
          // BFSSimulation{}
           // TopologicalSort{}
           // DFSSimulation{}

          //BubbleSortRoute{}
       //     BinarySearchRoute{}
//            LinearSearchRoute(
//                navigationIcon = {
//
//                }
//       )
        }


    }


}
