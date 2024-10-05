plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.graphBfs.domain)
                api(projects.feature.graphBfs.infrastructure)
            }
        }
    }

}
android {
    namespace = "graphbfs.di"
}