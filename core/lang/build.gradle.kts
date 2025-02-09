plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }

    }


}
android {
    namespace = "core.lang"
}
