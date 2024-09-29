plugins {
    alias(libs.plugins.convention.kotlinMultiplatform)
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.feature.treeTraversal.domain)
                api(projects.feature.treeTraversal.infrastructure)
            }
        }
    }

}
android {
    namespace = "tree.di"
}