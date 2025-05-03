plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.feature.treeTraversal.domain)
                implementation(projects.feature.treeTraversal.di)
                implementation(projects.core.lang)
                implementation(projects.core.ui)
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
    namespace = "tree.ui"
}