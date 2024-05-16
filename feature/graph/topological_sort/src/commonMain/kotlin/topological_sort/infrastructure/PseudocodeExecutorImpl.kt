package topological_sort.infrastructure

import androidx.compose.ui.text.AnnotatedString
import topological_sort.domain.LineForPseudocode

internal data class PseudoCodeVariablesValue(
    val currentNode: String? = null,
    val queue: String? = null,
    val isQueueEmpty: String? = null
)

internal class DebuggablePseudocodeBuilder {
    private var currentNode: String? = null
    private var queue: String? = null
    private var isQueueEmpty: String? = null

    fun build(value: PseudoCodeVariablesValue? = null): List<LineForPseudocode> {
        value?.let { updateVariables(it) }
        //log("$value")

        return listOf(
            _createLine("bfs(graph, startNode) {"),

            _createLine("queue = [startNode]") {
              if (queue!=null)  "Queue: $queue" else null //Debugging text
            },

            _createLine("while (!queue.isEmpty) {"),

            _createLine("currentNode = queue.poll()") {
               if (currentNode!=null) "Current Node: $currentNode" else null //Debugging text
            },

            _createLine("isQueueEmpty = queue.isEmpty") {
               if (isQueueEmpty!=null) "Is Queue Empty: $isQueueEmpty" else null //Debugging text
            },

            _createLine("for (neighbor in getNeighbors(currentNode)) {"),

            _createLine("if (!visited.contains(neighbor)) {"),

            _createLine("queue.add(neighbor)"),

            _createLine("visited.add(neighbor)"),

            _createLine("}"),

            _createLine("}"),

            _createLine("}")
        )
    }

    private fun updateVariables(value: PseudoCodeVariablesValue) {
        currentNode = value.currentNode
        queue = value.queue
        isQueueEmpty = value.isQueueEmpty
    }

    @Suppress("FunctionName")
    private fun _createLine(
        string: String,
        debuggingText: () -> String? = { null }
    ): LineForPseudocode {
        return LineForPseudocode(
            line = AnnotatedString(string),
            debuggingText = debuggingText()
        )
    }

    @Suppress("Unused")
    private fun log(message: String, methodName: String? = null) {
        val tag = "${this@DebuggablePseudocodeBuilder::class.simpleName}Log:"
        val method = if (methodName == null) "" else "$methodName()'s "
        val msg = "$method:-> $message"
        println("$tag::$msg")
    }
}
