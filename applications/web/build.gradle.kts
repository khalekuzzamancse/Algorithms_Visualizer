import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
plugins {
    alias(libs.plugins.convention.composeMultiplatfrom)
}
kotlin {

    js(IR) {
        moduleName = "web"
        browser {
            commonWebpackConfig {
                outputFileName = "web.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets{
        val commonMain by getting{
            dependencies {

            }
        }
    }


}
android {
    namespace = "core.commonui"
}
compose.experimental {
    web.application {}
}