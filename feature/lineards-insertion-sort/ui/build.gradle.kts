plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.feature.lineardsInsertionSort.domain)
                implementation(projects.feature.lineardsInsertionSort.di)
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
    namespace = "insertionsort.ui"
}