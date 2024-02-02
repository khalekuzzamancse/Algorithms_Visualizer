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
                implementation(project(localModules.versions.core.database.mongodb.get()))

            }
        }
        val commonTest by getting {
            dependencies {
                implementation ("org.junit.jupiter:junit-jupiter-api:5.6.3")
                implementation ("org.junit.jupiter:junit-jupiter-engine:5.6.3")
                implementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
                implementation("org.mockito:mockito-core:4.8.0")

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
    namespace = "com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.data.tutorial"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}

