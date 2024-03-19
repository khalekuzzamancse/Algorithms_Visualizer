package feature.search.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import feature.search.PackageLevelAccess

@OptIn(ExperimentalMaterial3Api::class)
@PackageLevelAccess //avoid to access other layer such domain or data/infrastructure
@Composable
internal fun TopBar(onExitRequest:()->Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onExitRequest
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
        },
        title = {
            Text("Linear Search")
        },
    )
}