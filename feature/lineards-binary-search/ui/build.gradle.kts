plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.feature.lineardsBinarySearch.domain)
                implementation(projects.feature.lineardsBinarySearch.di)
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
    namespace = "binarysearch.ui"
}
