plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.lineardsInsertionSort.domain)
                api(projects.feature.lineardsInsertionSort.infrastructure)
            }
        }
    }

}
android {
    namespace = "insertionsort.di"
}


