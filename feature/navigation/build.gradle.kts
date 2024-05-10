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
               // jvmTarget = "1.8"
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
                implementation(compose.components.resources)
                implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.3.1")
                implementation(project(":feature:linear_search"))
                implementation(project(":feature:graph:graph_editor"))
                implementation(project(localModules.versions.features.graph.bfs.get()))
//                implementation(project(localModules.versions.features.graph.viewer.get()))
                implementation(project(":common:data"))
                implementation(project(localModules.versions.ui.commonUI.get()))
                implementation(project(localModules.versions.features.binarySearch.get()))
                implementation(project(localModules.versions.features.bubbleSort.get()))
                implementation(project(localModules.versions.features.selectionSort.get()))
                implementation(project(localModules.versions.features.insertionSort.get()))
                implementation(project(localModules.versions.core.realm.get()))
//                implementation(project(localModules.versions.core.database.mongodb.get()))
                //
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
    namespace = "com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation"
    compileSdk = 34
    defaultConfig {
        minSdk = 27
    }

}
