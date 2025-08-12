package com.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AspectRatio
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.theme.AndroidAppThemes
import feature.navigation.MyApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()//before super.onCreate()
        super.onCreate(savedInstanceState)
        setContent {

            AndroidAppThemes {
                // Using AndroidView to embed the TreeView class inside Compose
//                AndroidView(
//                    factory = { context ->
//                        TreeView(context).apply {
//                            // Optional: You can add any initialization logic here, if needed
//                        }
//                    },
//                    modifier = Modifier.fillMaxSize() // Adjust as per the screen size requirements
//                )
                MyApplication()


            }



        }
    }
}

