@file:Suppress("unused")
package graphbfs.domain.model


data class CodeStateModel(
    /**Label of queue.front()*/
    val current: String? = OUT_OF_SCOPE,

    /**Label of source node*/
    val source: String? = OUT_OF_SCOPE,

    /**Label of nodes in queue,example as [1,2,3], denote empty pass: [ ]*/
    val queue: String? = OUT_OF_SCOPE,

    /**Label of unVisited neighbour ,example as [1,2,3]*/
    val unvisitedNeighbours: String? = OUT_OF_SCOPE,

    /** either true or false , example as "true"*/
    val queueIsEmpty: String? = OUT_OF_SCOPE
) {
    companion object {
        /**Represent that a variable is no longer active,it is dead*/
        val OUT_OF_SCOPE = null
    }

    fun killCurrent() = this.copy(current = OUT_OF_SCOPE)
    fun killSource() = this.copy(source = OUT_OF_SCOPE)
    fun killQueue() = this.copy(queue = OUT_OF_SCOPE)
    fun killUnvisitedNeighbours() = this.copy(unvisitedNeighbours = OUT_OF_SCOPE)
    fun killQueueIsEmpty() = this.copy(queueIsEmpty = OUT_OF_SCOPE)
}
