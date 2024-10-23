package mst.domain.service

data class CodeStateModel(
    /**Label of queue.front() or min node that picked*/
    val processing: String? = OUT_OF_SCOPE,
    /**Label of source node*/
    val source: String? = OUT_OF_SCOPE,

    /**Label of nodes in ,example as [1,2,3], denote empty pass: [ ]*/
    val pendingNodes: String? = OUT_OF_SCOPE,
    /**Label of pending neighbour ,example as [1,2,3]*/
    val pendingNeighbours: String? = OUT_OF_SCOPE,
    /** either true or false , example as "true"*/
    val pendingIsNotEmpty: String? = OUT_OF_SCOPE,
) {
    companion object {
        /**Represent that a variable is no longer active,it is dead*/
        val OUT_OF_SCOPE = null
    }

    fun killProcessing() = this.copy(processing = OUT_OF_SCOPE)
    fun killSource() = this.copy(source = OUT_OF_SCOPE)
    fun killPending() = this.copy(pendingNodes = OUT_OF_SCOPE)
    fun killPendingNeighbours() = this.copy(pendingNeighbours = OUT_OF_SCOPE)
    fun killPendingIsNotEmpty() = this.copy(pendingIsNotEmpty = OUT_OF_SCOPE)
}

object PseudocodeGenerator {
    fun generate(model: CodeStateModel) = with(model) { """
 Prims(graph, source) {
   initDistances()
   pendingNodes = graph.getAllNode() ${pendingNodes.showState()}    
    while (pendingNodes.isNotEmpty()) { ${pendingIsNotEmpty.showState()}  
        processing = pendingNodes.pullMin() ${processing.showState()}  
        neighbors = graph.getPendingNeighbors() ${pendingNeighbours.showState()}  
        updateDistanceAndParentOf(neighbors, processing) ${showParent()}
     
    }
}
""".trimIndent()
    }
    private fun CodeStateModel.showParent()=if(processing!=null&&pendingNeighbours!=null) "//parent:$processing" else ""
    private fun String?.showState() = this?.let { "//$it" } ?: ""
}