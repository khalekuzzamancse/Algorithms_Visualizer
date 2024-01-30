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
    ":architecture_layers",
    ":architecture_layers:data",
    ":architecture_layers:domain",
    ":architecture_layers:ui",
)
val applicationModules = listOf(
    ":applications",
    ":applications:android",
    ":applications:desktop",
    ":applications:web",
    ":applications:ios"
)
val uiLyaers=listOf(
    ":architecture_layers:ui:common_ui",
    ":architecture_layers:ui:array",
)

val coreModules = listOf(":core")
val featuresModules=listOf(
    ":feature",
    ":feature:linear_search", ":feature:linear_search:data",":feature:linear_search:domain",":feature:linear_search:ui",":feature:linear_search:destination",
    ":feature:navigation",
)

rootProject.name = "DSAVisualier2024"
include(applicationModules)
include(architectureLayers)
include(coreModules)
include(featuresModules)
include(uiLyaers)
 