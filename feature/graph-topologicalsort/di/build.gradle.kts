plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.graphTopologicalsort.domain)
                api(projects.feature.graphTopologicalsort.infrastructure)
            }
        }
    }

}
android {
    namespace = "graphtopologicalsort.di"
}