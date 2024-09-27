package dfs.infrastructure

import dfs.domain.AlgoSimulator
import dfs.domain.AlgorithmicGraph
import dfs.domain.AlgorithmicNode
import dfs.domain.SimulationState

internal class AlgoSimulatorImpl(
    graph: AlgorithmicGraph,
    startNode: AlgorithmicNode
) : AlgoSimulator {

    private val sequenceBuilder = _DFSSequenceBuilder(graph, startNode)
    private val iterator = sequenceBuilder.iterator.iterator()

    override fun next(): SimulationState {
        return if (iterator.hasNext()) {
            val res = iterator.next()
            log(res.toString())
            res
        } else
            SimulationState.Finished
    }

    @Suppress("Unused")
    private fun log(message: String, methodName: String? = null) {
        val tag = "${this@AlgoSimulatorImpl::class.simpleName}Log:"
        val method = if (methodName == null) "" else "$methodName()'s "
        val msg = "$method:-> $message"
        println("$tag::$msg")
    }

}