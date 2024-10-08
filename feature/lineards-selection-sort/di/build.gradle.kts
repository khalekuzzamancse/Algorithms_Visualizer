plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.lineardsSelectionSort.domain)
                api(projects.feature.lineardsSelectionSort.infrastructure)
            }
        }
    }

}
android {
    namespace = "selectionsort.di"
}


