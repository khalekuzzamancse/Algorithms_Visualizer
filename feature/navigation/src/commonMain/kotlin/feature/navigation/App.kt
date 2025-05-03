package feature.navigation

import androidx.compose.material3.Label
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import core_ui.GlobalMessenger
import linearsearch.ui.ui.LinearSearchRoute


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
            // MainNavHost()
            LinearSearchRoute(
                navigationIcon = {

                }
            )
        }


    }


}
