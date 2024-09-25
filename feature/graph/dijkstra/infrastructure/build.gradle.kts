plugins {
    alias(libs.plugins.convention.dataModulePlugin)
}
kotlin {
    sourceSets{
        val commonMain by getting{
            dependencies {
//                implementation(projects.features.academicCalender.domain)
//                implementation(projects.core.network)
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
    namespace = "data"
}