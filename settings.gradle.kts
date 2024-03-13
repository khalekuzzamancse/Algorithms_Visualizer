pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("localModules") {
            from(files("gradle/localModules.versions.toml"))
        }
    }
}
val architectureLayers = listOf(
    ":common_layers",
    ":common_layers:data",
    ":common_layers:domain",
    ":common_layers:ui",
)
val applicationModules = listOf(
    ":applications",
    ":applications:android",
    ":applications:desktop",
    ":applications:web",
    ":applications:ios"
)
val uiLyaers = listOf(
    ":common_layers:ui:common_ui",
    ":common_layers:ui:array",
)

val coreModules = listOf(
    ":core",
    ":core:realm",
    ":core:database",
    ":core:database:mongodb",
)
val featuresModules = listOf(
    ":feature",
    ":feature:linear_search",
    ":feature:navigation",
    ":feature:admin_section"
)

rootProject.name = "AlgorithmVisualizer"
include(applicationModules+architectureLayers+coreModules+featuresModules+uiLyaers)
