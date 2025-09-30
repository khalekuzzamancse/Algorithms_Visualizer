package graph.dfs.domain


object DFSCodeGenerator {
    fun generate(model: CodeStateModel) = with(model) {
        """
    DFS(graph, source) {
        stack ${stack.showState()}
        while (stack.isNotEmpty()) { ${stackIsNotEmpty.showState()}
            current = stack.peek() ${current.showState()}
            neighbor = getOneUnvisitedNeighborOf(current) ${oneUnvisitedNeighbour.showState()}

            allNeighborProcessed = (neighbor == null)
            if (allNeighborProcessed) { ${hasAllNeighbourProcessed.showState()}
                stack.pop()
            } else {
                stack.push(neighbor)
            }
        }
    }
""".trimIndent()
    }

    private fun String?.showState() = this?.let { "//$it" } ?: ""

}
