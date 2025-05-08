import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() {
    application {
        val state= remember { WindowState(
            position = WindowPosition(0.dp, 0.dp),
        ) }
        state.size= DpSize(width = 1366.dp, height =730.dp)
        Window(
            state=state,
            title = "Algorithms Visualizer",
            onCloseRequest = ::exitApplication
        ) {
            MaterialTheme {
                Text("Hello K2")
              //  MyApplication()
            }
        }
    }

}



