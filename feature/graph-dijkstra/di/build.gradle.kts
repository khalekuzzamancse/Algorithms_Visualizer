plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.graphDijkstra.domain)
                api(projects.feature.graphDijkstra.infrastructure)
            }
        }
    }

}
android {
    namespace = "di"
}