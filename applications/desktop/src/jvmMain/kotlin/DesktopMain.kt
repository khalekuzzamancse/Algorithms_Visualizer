import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation.Application


fun main() {
    application {
        val state= remember { WindowState(
            position = WindowPosition(0.dp, 0.dp),
        ) }
        state.size= DpSize(width = 400.dp, height =700.dp)
        Window(
            state=state,
            title = "Compose Desktop",
            onCloseRequest = ::exitApplication
        ) {
            MaterialTheme {
                Application()
            }
        }
    }

}

