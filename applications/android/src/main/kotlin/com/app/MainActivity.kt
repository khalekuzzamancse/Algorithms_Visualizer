package com.app

import android.os.Bundle
import android.util.Range
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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

