plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.lineardsBinarySearch.domain)
                api(projects.feature.lineardsBinarySearch.infrastructure)
            }
        }
    }

}
android {
    namespace = "binarysearch.di"
}


