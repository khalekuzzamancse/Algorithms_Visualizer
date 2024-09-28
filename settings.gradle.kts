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
}

val applicationModules = listOf(
    ":applications",
    ":applications:android",
    ":applications:desktop",
    ":applications:web",
    ":applications:ios"
)


val coreModules = listOf(
    ":core", ":core:database",
    ":core:common-ui",":core:common-ui:array",":core:common-ui:common_ui", ":core:common-ui:graph",
)

val featuresModules = listOf(
    ":feature",
//    ":feature:linear_search",":feature:binary_search",
//    ":feature:bubble_sort",":feature:selection_sort",":feature:insertion_sort",
    ":feature:navigation",
   // ":feature:admin_section",
//    ":feature:graph:bfs",":feature:graph:dfs",":feature:graph:topological_sort",
    ":feature:graph-dijkstra",":feature:graph-dijkstra:domain",":feature:graph-dijkstra:ui",":feature:graph-dijkstra:di",":feature:graph-dijkstra:infrastructure",
    ":feature:graph-mst",":feature:graph-mst:domain",":feature:graph-mst:ui",":feature:graph-mst:di",":feature:graph-mst:infrastructure",
)

rootProject.name = "AlgorithmVisualizer"
include(applicationModules+coreModules+featuresModules)
