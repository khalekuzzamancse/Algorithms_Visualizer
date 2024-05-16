package topological_sort.di_containter

import topological_sort.domain.AlgorithmicGraph

object BFSFactory {
    private lateinit var graph: AlgorithmicGraph
    fun setGraph(graph: AlgorithmicGraph) {
        BFSFactory.graph = graph
    }
    fun getGraph()= graph
}