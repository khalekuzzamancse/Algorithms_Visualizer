plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.materialIconsExtended)
                implementation(projects.core.commonUi.commonUi)

            }
        }

    }


}
android {
    namespace = "graph_editor"
}
