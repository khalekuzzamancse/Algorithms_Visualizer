plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {

    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(project(":core:common-ui:graph"))
                implementation(project(":core:common-ui:common_ui"))
                implementation(libs.common.navigation)
                implementation(libs.common.viewmodel)
                implementation(projects.feature.graphDijkstra.ui)
                implementation(projects.feature.graphMst.ui)
                implementation(projects.feature.treeTraversal.ui)
                implementation(projects.feature.graphDfs.ui)
                implementation(projects.feature.graphBfs.ui)
                implementation(projects.feature.graphTopologicalsort.ui)
                implementation(projects.feature.lineardsBubbleSort.ui)
                implementation(projects.feature.lineardsSelectionSort.ui)
                implementation(projects.feature.lineardsInsertionSort.ui)
                implementation(projects.feature.lineardsLinearSearch.ui)
                implementation(projects.feature.lineardsBinarySearch.ui)
                implementation(projects.feature.lineardsQuickSort)
                implementation(projects.feature.treeBinary)
                implementation(projects.feature.treeBinary)



            }
        }


    }


}
android {
    namespace = "navigation"

}
