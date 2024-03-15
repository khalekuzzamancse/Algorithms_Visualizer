plugins {
    kotlin("multiplatform")
    id("com.android.library")
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
                implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.11.0")
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
    namespace = "com.khalekuzzaman.just.cse.dsavisualizer.core.database.mongodb"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}

