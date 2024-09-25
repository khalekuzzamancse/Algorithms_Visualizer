plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {

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