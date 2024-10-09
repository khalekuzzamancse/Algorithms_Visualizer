package com.app

import android.os.Bundle
import android.util.Range
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.Pageview
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.AnnotatedString
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.theme.AndroidAppThemes
import feature.navigation.MyApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()//before super.onCreate()
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAppThemes {
                MyApplication()
            }



        }
    }
}

