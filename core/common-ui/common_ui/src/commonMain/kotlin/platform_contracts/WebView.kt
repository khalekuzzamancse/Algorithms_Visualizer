package platform_contracts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebPageLoader(
    modifier: Modifier = Modifier,
    url: String
)