plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.feature.lineardsBubbleSort.domain)
                implementation(projects.feature.lineardsBubbleSort.di)
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
    namespace = "bubblesort.ui"
}