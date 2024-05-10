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
    ":common",
    ":common:data",
    ":common:domain",
    ":common:ui",
)
val applicationModules = listOf(
    ":applications",
    ":applications:android",
    ":applications:desktop",
    ":applications:web",
    ":applications:ios"
)
val uiLyaers = listOf(
    ":common:ui:common_ui",
    ":common:ui:array",
)

val coreModules = listOf(
    ":core",
    ":core:realm",
    ":core:database",
//    ":core:database:mongodb", disconnecting it
)
val featuresModules = listOf(
    ":feature",
    ":feature:linear_search",":feature:binary_search",
    ":feature:bubble_sort",":feature:selection_sort",":feature:insertion_sort",
    ":feature:navigation",
    ":feature:admin_section",
    ":feature:graph", ":feature:graph:graph_editor", ":feature:graph:graph_viewer",":feature:graph:bfs"
)

rootProject.name = "AlgorithmVisualizer"
include(applicationModules+architectureLayers+coreModules+featuresModules+uiLyaers)
