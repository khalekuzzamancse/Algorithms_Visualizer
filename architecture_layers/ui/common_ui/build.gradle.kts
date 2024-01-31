plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}
kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    jvm("desktop"){
        jvmToolchain(17)
    }
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.animation)
                implementation(compose.animationGraphics)
                implementation(compose.materialIconsExtended)
                implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.3.1")
                //
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.coil3.network)
                implementation(libs.coil3)
                implementation(libs.coil3.core)

            }
        }
        val androidMain by getting{
            dependencies {
            }
        }
        val desktopMain by getting{
            dependencies {
                //dependency to support android coil on desktop
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")
                //for desktop video player
                implementation("uk.co.caprica:vlcj:4.7.0")
            }
        }
    }


}
android {
    namespace = "com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}
