package graph.topological_sort.domain

interface TopologicalSortSimulator {
    fun next(): TopologicalSortState
}