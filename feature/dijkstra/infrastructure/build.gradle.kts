plugins {
    alias(libs.plugins.convention.dataModulePlugin)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.feature.dijkstra.domain)
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
    namespace = "infrastructure"
}