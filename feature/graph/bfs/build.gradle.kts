plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
}
kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    jvm("desktop") {
        jvmToolchain(17)
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.animation)
                implementation(compose.animationGraphics)
                implementation(compose.materialIconsExtended)
                implementation(libs.kotlinx.coroutines.core)
                implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.3.1")

                implementation(project(localModules.versions.ui.array.get()))
                implementation(project(localModules.versions.ui.commonUI.get()))
                implementation(project(localModules.versions.common.domain.get()))
                implementation(project(localModules.versions.common.data.get()))
                //Add the Graph Editor to take the nodes information
                implementation(project(localModules.versions.features.graph.editor.get()))
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val desktopMain by getting {
            dependencies {
                //dependency to support android coil on desktop
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")
            }
        }
    }


}
android {
    namespace = "bfs"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}