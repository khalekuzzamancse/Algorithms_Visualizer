import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}
kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(project(localModules.versions.featureModules.navigation.get()))

            }
        }
    }


}
compose.desktop {
    application {
        mainClass = "DesktopMainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Exe)
            packageName = "khalekuzzamancsedsavisulizerdesktop"
            version = "1.0.0"
        }
    }
}