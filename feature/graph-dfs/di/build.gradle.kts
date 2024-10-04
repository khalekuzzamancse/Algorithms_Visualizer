plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.graphDfs.domain)
                api(projects.feature.graphDfs.infrastructure)
            }
        }
    }

}
android {
    namespace = "graphtraversal.di"
}