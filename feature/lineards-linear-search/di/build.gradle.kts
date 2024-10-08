plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.lineardsLinearSearch.domain)
                api(projects.feature.lineardsLinearSearch.infrastructure)
            }
        }
    }

}
android {
    namespace = "linearsearch.di"
}


