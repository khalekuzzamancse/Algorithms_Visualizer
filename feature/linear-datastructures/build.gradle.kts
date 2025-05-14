plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.core)
                implementation(libs.common.navigation)
            }
        }
    }

}
android {
    namespace = "lineards"
}
