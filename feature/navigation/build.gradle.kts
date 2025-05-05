plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {

    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.core.ui)
                implementation(libs.common.navigation)
                implementation(libs.common.viewmodel)
                implementation(projects.feature.graphDijkstra.ui)
                implementation(projects.feature.treeTraversal.ui)
                implementation(projects.feature.linearDatastructures)
                implementation(projects.feature.graph)
                implementation(projects.feature.treeBinary)
                implementation(projects.feature.treeBinary)

            }
        }


    }


}
android {
    namespace = "navigation"

}
