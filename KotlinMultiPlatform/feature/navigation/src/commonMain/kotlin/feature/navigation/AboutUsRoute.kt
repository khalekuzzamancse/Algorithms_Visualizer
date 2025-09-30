@file:Suppress("SpellCheckingInspection")

package feature.navigation

import algorithmvisualizer.feature.navigation.generated.resources.Res
import algorithmvisualizer.feature.navigation.generated.resources.developer
import algorithmvisualizer.feature.navigation.generated.resources.just_logo
import algorithmvisualizer.feature.navigation.generated.resources.super_vicer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsPage(navigationIcon: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title={},
                navigationIcon = {
                    navigationIcon()
                },
                modifier = Modifier.height(30.dp)
            )

        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(start = 8.dp,end=8.dp, bottom = 8.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // "Supervised by" Heading
            SectionHeading()

            Spacer(modifier = Modifier.height(8.dp))

            // Supervisor Section
            SupervisorSection()

            Spacer(modifier = Modifier.height(24.dp))

            // Developer Section
            DeveloperSection()

            Spacer(modifier = Modifier.height(24.dp))

            // Copyright Notice
            CopyrightNotice()
        }
    }
}

@Composable
private fun SectionHeading() {
    Text(
        text = "Supervised by",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SupervisorSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            res = Res.drawable.super_vicer,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sk. Shalauddin Kabir",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Lecturer",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        DeptAndUniversity()

        Text(
            text = "B.Sc. (Engg.) & M.Sc. (Engg.) in CSE (JUST)",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun DeveloperSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = "Developed by",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            res = Res.drawable.developer,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Md. Khalekuzzman",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Student",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        DeptAndUniversity()

    }
}

@Composable
fun DeptAndUniversity(modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Department of  CSE at Jashore University of Science and Technology (JUST)",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CopyrightNotice() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        // Display the university logo
        Image(
            res = Res.drawable.just_logo,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Copyright and University Attribution Text
        Text(
            text = "© 2024, Jashore University of Science and Technology (JUST)",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Algorithms Visualizer App",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Developed in the Department of Computer Science and Engineering(CSE)",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun Image(modifier: Modifier = Modifier, res: DrawableResource) {
    Image(
        modifier = modifier,
        painter = painterResource(res),
        contentDescription = null
    )
}
