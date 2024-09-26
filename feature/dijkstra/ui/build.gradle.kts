plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.feature.dijkstra.domain)
                implementation(projects.feature.dijkstra.di)
                implementation(projects.feature.graph.graphEditor) //to take graph input from user
                implementation(projects.feature.graph.graphViewer) //to show uneditable graph with highlight state
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