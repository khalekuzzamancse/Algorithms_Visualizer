package feature.navigation.home


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.navigation.home.dashboard.Destination
import feature.navigation.home.dashboard.DashBoard


@OptIn(ExperimentalMaterial3Api::class)
@PublishedApi
@Composable
internal fun HomeDestination(
    onAboutUsRequest: () -> Unit,
    onContactUsClick:()->Unit,
    onDestinationClick: (Destination) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                        Text("Visualizer")
                },
                actions = {
                    MyDropDownMenu(onAboutClick=onAboutUsRequest, onInfoClick = onContactUsClick)
                }

            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(Modifier.fillMaxWidth()) {
                WelcomeToHome(Modifier.align(Alignment.CenterHorizontally))
                Spacer(Modifier.height(32.dp))
                Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(4.dp))
                DashBoard(onNavigationRequest = onDestinationClick)
            }


        }
    }

}

