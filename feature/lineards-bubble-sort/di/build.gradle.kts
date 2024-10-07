plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.lineardsBubbleSort.domain)
                api(projects.feature.lineardsBubbleSort.infrastructure)
            }
        }
    }

}
android {
    namespace = "bubblesort.di"
}