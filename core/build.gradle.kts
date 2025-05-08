plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.materialIconsExtended)

            }
        }

    }


}
android {
    namespace = "core"
}
