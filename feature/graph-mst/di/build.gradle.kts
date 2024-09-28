plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.graphMst.domain)
                api(projects.feature.graphMst.infrastructure)
            }
        }
    }

}
android {
    namespace = "mst.di"
}