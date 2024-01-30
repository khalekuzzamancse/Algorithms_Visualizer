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
                //implementation(localModules.versions.features.linearSearch.domain)
                implementation(project(":feature:linear_search:domain"))
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
            }
        }
    }


}

android {
    namespace = "com.khalekuzzaman.just.cse.dsavisualizer.feature.linear_search.data"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}
