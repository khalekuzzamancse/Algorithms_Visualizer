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
)


val coreModules = listOf(
    ":core", ":core:database",":core:lang",
    ":core:ui",
)

val selectionSort=":feature:lineards-selection-sort"
val insertionSort=":feature:lineards-insertion-sort"
val binarySearch=":feature:lineards-binary-search"
val featuresModules = listOf(
    ":feature",
    "feature:linear-datastructures",
    ":feature:graph",
    ":feature:navigation",
    ":feature:tree-traversal", ":feature:tree-binary"
)

rootProject.name = "AlgorithmVisualizer"
include(applicationModules+coreModules+featuresModules)
