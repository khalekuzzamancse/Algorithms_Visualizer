plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {

    sourceSets{
        val commonMain by getting{
            dependencies {

                implementation(compose.materialIconsExtended)

                //
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.coil3.network)
                implementation(libs.coil3)
                implementation(libs.coil3.core)

            }
        }
    }


}
android {
    namespace = "core.commonui"
}
