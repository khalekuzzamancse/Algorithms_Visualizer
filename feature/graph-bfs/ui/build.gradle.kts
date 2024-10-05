plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.feature.graphDfs.domain)
                implementation(projects.feature.graphDfs.di)
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
    namespace = "graphtraversal.ui"
}