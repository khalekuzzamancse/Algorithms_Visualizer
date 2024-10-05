plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.feature.graphDijkstra.domain)
                implementation(projects.feature.graphDijkstra.di)
                implementation(projects.core.commonUi.graph)
                implementation(projects.core.commonUi.commonUi)
            }
        }
        val commonTest by getting {
            dependencies{
                implementation(libs.bundles.test)
            }
        }
    }

}
android {
    namespace = "dijkstra.ui"
}