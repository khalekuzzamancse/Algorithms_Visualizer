plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(libs.windowSize)
                implementation(projects.core)
                implementation(libs.common.navigation)
//                implementation(libs.common.viewmodel)
                implementation(projects.feature.linearDatastructures)
                implementation(projects.feature.graph)
                implementation(projects.feature.treeTraversal)
                implementation(projects.feature.treeBinary)
                implementation(projects.feature.treeBinary)

            }
        }


    }


}
android {
    namespace = "navigation"
}
