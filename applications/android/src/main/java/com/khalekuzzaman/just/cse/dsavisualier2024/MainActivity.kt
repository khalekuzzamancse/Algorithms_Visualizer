package com.khalekuzzaman.just.cse.dsavisualier2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import com.khalekuzzaman.just.cse.dsavisualier2024.ui.theme.DSAVisualier2024Theme
import com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation.MyApplication
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DSAVisualier2024Theme {
                MyApplication()
               
            }
        }
    }
}


@Composable
fun TabLayout() {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Visualizer", "Theory", "Complexity Analysis","Implementation","Pseudocode")


        ScrollableTabRow(
            selectedTabIndex = state,
            modifier = Modifier.fillMaxWidth()
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    text = { Text(text = title) },
                    icon = {
                        Icon(Icons.Default.Mail,null)
                    }
                )
            }
        }


}

