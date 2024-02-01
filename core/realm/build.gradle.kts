plugins {
    kotlin("multiplatform")
    id("com.android.library")
    alias(libs.plugins.realm)
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
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.database.realm.base)
            }
        }
        val androidMain by getting{
            dependencies {

            }
        }
        val desktopMain by getting{
            dependencies {
                //dependency to support coroutine desktop
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")

            }
        }
    }


}
android {
    namespace = "com.khalekuzzaman.just.cse.dsavisualizer.core.realm"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}

