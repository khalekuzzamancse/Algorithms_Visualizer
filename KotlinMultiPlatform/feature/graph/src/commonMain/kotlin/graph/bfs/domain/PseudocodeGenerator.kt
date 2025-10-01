@file:Suppress("unused")
package graph.bfs.domain


object PseudocodeGenerator {
    val rawCode = generate(CodeStateModel())

    fun generate(model: CodeStateModel): String {
        return with(model) {
            """
    BFS(graph, source) {
        queue=empty() ${queue.showState()}
        queue.add(source) 
        markAsVisited(source)

        while (queue.isNotEmpty()) { ${queueIsEmpty.showState()}
            current = queue.front() ${current.showState()}
            neighbours = getUnvisitedNeighbourOf(current) ${unvisitedNeighbours.showState()}
            markAsVisited(neighbours)
            enqueue(neighbours)
        }
    }
""".trimStart()
        }
    }

    private fun String?.showState() = this?.let { "//$it" } ?: ""

}

