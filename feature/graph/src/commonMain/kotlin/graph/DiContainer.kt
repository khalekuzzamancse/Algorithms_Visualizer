package graph

import graph._core.domain.GraphModel
import graph._core.domain.TraversalSimulator
import graph.bfs.inf.BFSBFSSimulatorImpl
import graph.dfs.inf.DFSSimulatorImpl

internal object DiContainer {
    fun createBFSSimulator(graph: GraphModel): TraversalSimulator = BFSBFSSimulatorImpl(graph)
    fun createDFSSimulator(graph: GraphModel): TraversalSimulator = DFSSimulatorImpl(graph)
}