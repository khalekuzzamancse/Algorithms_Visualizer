/**
 * Type-Safe Project Accessors, a feature introduced in Gradle 7.0 that allows you to reference project dependencies
 * in a type-safe manner without relying on string-based project paths like project(":x") as implement(projects.x)
 */
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")//build-logic as a Composite Build, for convention plugin

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
val graph=":feature"
val featuresModules = listOf(
    ":feature",
    ":feature:linear_search",":feature:binary_search",
    ":feature:bubble_sort",":feature:selection_sort",":feature:insertion_sort",
    ":feature:navigation",
    ":feature:admin_section",
    ":feature:graph", ":feature:graph:graph_editor", ":feature:graph:graph_viewer",
    ":feature:graph:bfs",":feature:graph:dfs",":feature:graph:topological_sort",
    "$graph:dijkstra","$graph:dijkstra:domain","$graph:dijkstra:ui","$graph:dijkstra:di","$graph:dijkstra:infrastructure",
)

rootProject.name = "AlgorithmVisualizer"
include(applicationModules+architectureLayers+coreModules+featuresModules+uiLyaers)
