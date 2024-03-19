package com.khalekuzzaman.just.cse.dsavisualier2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ManageHistory
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.SystemUpdateAlt
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import com.khalekuzzaman.just.cse.dsavisualier2024.ui.theme.DSAVisualier2024Theme
import com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation.MyApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DSAVisualier2024Theme {
                MyApplication()
                Icons.Default.ManageHistory


            }
        }
    }
}

