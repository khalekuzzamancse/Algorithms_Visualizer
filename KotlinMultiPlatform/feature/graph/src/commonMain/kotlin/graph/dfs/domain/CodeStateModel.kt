@file:Suppress("unused")
package graph.dfs.domain


data class CodeStateModel(
    /**Label of queue.front()*/
    val current: String? = OUT_OF_SCOPE,

    /**Label of source node*/
    val source: String? = OUT_OF_SCOPE,

    /**Label of nodes in  stack,example as [1,2,3], denote empty pass: [ ]*/
    val stack: String? = OUT_OF_SCOPE,

    /**Label of unVisited neighbour ,example as [1,2,3]*/
    val oneUnvisitedNeighbour: String? = OUT_OF_SCOPE,
    val hasAllNeighbourProcessed:String? = OUT_OF_SCOPE,
    /** either true or false , example as "true"*/
    val stackIsNotEmpty: String? = OUT_OF_SCOPE
) {
    companion object {
        /**Represent that a variable is no longer active,it is dead*/
        val OUT_OF_SCOPE = null
    }

    fun killCurrent() = this.copy(current = OUT_OF_SCOPE)
    fun killSource() = this.copy(source = OUT_OF_SCOPE)
    fun killStack() = this.copy(stack = OUT_OF_SCOPE)
    fun killOneUnvisitedNeighbour() = this.copy(oneUnvisitedNeighbour = OUT_OF_SCOPE)
    fun killStackIsEmpty() = this.copy(stackIsNotEmpty = OUT_OF_SCOPE)
    fun killHasAllNeighbourProcessed() = this.copy(hasAllNeighbourProcessed = OUT_OF_SCOPE)
}
