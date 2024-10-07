plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {

    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(project(":core:common-ui:graph"))
                implementation(project(":core:common-ui:common_ui"))
                implementation(projects.feature.graphDijkstra.ui)
                implementation(projects.feature.graphMst.ui)
                implementation(projects.feature.treeTraversal.ui)
                implementation(projects.feature.graphDfs.ui)
                implementation(projects.feature.graphBfs.ui)
                implementation(projects.feature.graphTopologicalsort.ui)
                implementation(projects.feature.lineardsBubbleSort.ui)


            }
        }


    }


}
android {
    namespace = "navigation"

}
