plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.feature.graphMst.domain)
                implementation(projects.feature.graphMst.di)
                implementation(projects.core.commonUi.graph)
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
    namespace = "mst.ui"
}