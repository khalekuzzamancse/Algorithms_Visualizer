@file:Suppress("unused")
package tree.domain.model
sealed interface  CodeStateModel
data class DFSCodeStateModel(
    /**Label of queue.front()*/
    val current: String? = OUT_OF_SCOPE,

    /**Label of source node*/
    val reverseOrderChildren: String? = OUT_OF_SCOPE,

    /**Label of nodes in  stack,example as [1,2,3], denote empty pass: [ ]*/
    val stack: String? = OUT_OF_SCOPE,

    /**Label of unVisited neighbour ,example as [1,2,3]*/
    val visited: String? = OUT_OF_SCOPE,
    val isNotVisited:String? = OUT_OF_SCOPE,
    val isVisited: String? =OUT_OF_SCOPE,
    /** either true or false , example as "true"*/
    val stackIsNotEmpty: String? = OUT_OF_SCOPE
) :CodeStateModel{
    companion object {
        /**Represent that a variable is no longer active,it is dead*/
        val OUT_OF_SCOPE = null
    }

    fun killCurrent() = this.copy(current = OUT_OF_SCOPE)
    fun killReverseOrderChildren() = this.copy(reverseOrderChildren = OUT_OF_SCOPE)
    fun killStack() = this.copy(stack = OUT_OF_SCOPE)
    fun killVisited() = this.copy(visited = OUT_OF_SCOPE)
    fun killStackIsEmpty() = this.copy(stackIsNotEmpty = OUT_OF_SCOPE)
    fun killIsNotVisited() = this.copy(isNotVisited = OUT_OF_SCOPE)
    fun killIsVisited() = this.copy(isVisited = OUT_OF_SCOPE)
}

data class BFSCodeStateModel(
    /**Label of queue.front()*/
    val current: String? = OUT_OF_SCOPE,
    val visited: String? = OUT_OF_SCOPE,

    /**Label of source node*/
    val source: String? = OUT_OF_SCOPE,

    /**Label of nodes in queue,example as [1,2,3], denote empty pass: [ ]*/
    val queue: String? = OUT_OF_SCOPE,

    val isNotVisited: String? = OUT_OF_SCOPE,

    /** either true or false , example as "true"*/
    val queueIsNotEmpty: String? = OUT_OF_SCOPE
) :CodeStateModel{
    companion object {
        /**Represent that a variable is no longer active,it is dead*/
        val OUT_OF_SCOPE = null
    }

    fun killCurrent() = this.copy(current = OUT_OF_SCOPE)
    fun killSource() = this.copy(source = OUT_OF_SCOPE)
    fun killQueue() = this.copy(queue = OUT_OF_SCOPE)
    fun killUnvisitedNeighbours() = this.copy(isNotVisited = OUT_OF_SCOPE)
    fun killQueueIsEmpty() = this.copy(queueIsNotEmpty = OUT_OF_SCOPE)
}
