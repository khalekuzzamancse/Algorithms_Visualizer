package bfs.di_containter

import bfs.domain.AlgorithmicGraph

object BFSFactory {
    private lateinit var graph: AlgorithmicGraph
    fun setGraph(graph: AlgorithmicGraph) {
        this.graph = graph
        println("BFSFactoryLog:$graph")
    }
    fun getGraph()= graph
}