plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.dijkstra.domain)
                api(projects.feature.dijkstra.infrastructure)
            }
        }
    }

}
android {
    namespace = "di"
}