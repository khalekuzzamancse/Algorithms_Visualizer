package com.khalekuzzaman.just.cse.dsavisualier2024

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.khalekuzzaman.just.cse.dsavisualier2024.ui.theme.DSAVisualier2024Theme
import com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation.MyApplication

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

