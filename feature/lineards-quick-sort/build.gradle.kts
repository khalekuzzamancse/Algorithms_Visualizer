plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
                implementation(projects.feature.lineardsSelectionSort.domain)
                implementation(projects.feature.lineardsSelectionSort.di)
                implementation(projects.core.commonUi.commonUi)
                implementation(projects.core.lang)
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
    namespace = "quicksort"
}