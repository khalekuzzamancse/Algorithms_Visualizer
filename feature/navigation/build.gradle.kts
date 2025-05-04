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
                implementation(projects.feature.graphMst.ui)
                implementation(projects.feature.treeTraversal.ui)
                implementation(projects.feature.linearDatastructures)
                implementation(projects.feature.graphDfs.ui)
                implementation(projects.feature.graphBfs.ui)
                implementation(projects.feature.graphTopologicalsort.ui)
                implementation(projects.feature.treeBinary)
                implementation(projects.feature.treeBinary)



            }
        }


    }


}
android {
    namespace = "navigation"

}
