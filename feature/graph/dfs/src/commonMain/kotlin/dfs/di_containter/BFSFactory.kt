package dfs.di_containter

import dfs.domain.AlgorithmicGraph

object BFSFactory {
    private lateinit var graph: AlgorithmicGraph
    fun setGraph(graph: AlgorithmicGraph) {
        BFSFactory.graph = graph
    }
    fun getGraph()= graph
}