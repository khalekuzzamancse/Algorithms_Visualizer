package layers.ui.common_ui.common.decorators

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BackArrowDecorator(
    modifier: Modifier=Modifier,
    onBackRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        IconButton(
            onClick = onBackRequest
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back Arrow",
            )
        }
        Spacer(Modifier.height(12.dp))
        Box {
            content()
        }
    }

}