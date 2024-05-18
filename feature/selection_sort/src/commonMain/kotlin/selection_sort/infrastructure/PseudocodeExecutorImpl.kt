package selection_sort.infrastructure

import androidx.compose.ui.text.AnnotatedString
import selection_sort.domain.LineForPseudocode


internal data class PseudoCodeVariablesValue(
    val i: String? = null,
    val len: String? = null,
    val minIndex: String? = null,
    val isMinFound: String? = null,
    val current: String? = null,
    val min: String? = null
)


internal class DebuggablePseudocodeBuilder {
    private var i: String? = null
    private var len: String? = null
    private var minIndex: String? = null
    private var isMinFound: String? = null
    private var current: String? = null //list[i]
    private var min: String? = null //list[minIndex]


    fun build(value: PseudoCodeVariablesValue? = null): List<LineForPseudocode> {
        value?.let { updateVariables(it) }
       // log("$value")

        return listOf(
            _createLine("selectionSort( list ) {)"),

            _createLine("len = list.size") {
                if (len != null) "len:$len" else null //debuggingText
            },

            _createLine("for ( i = 0; i < len - 1; i++ ) {") {
                if (i != null) "i:$i" else null //debuggingText
            },

            _createLine("minIndex = findMinIndex(list, i, len)") {
                if (minIndex != null) "minIndex:$minIndex" else null //debuggingText
            },

            _createLine("isMinFound = ( minIndex != null )") {
                if (isMinFound != null) "isMinFound:$isMinFound" else null      //debuggingText
            },

            _createLine("if ( isMinFound )") {
                if (isMinFound != null) "isMinFound:$isMinFound" else null      //debuggingText
            },

            _createLine("list.swap(i,minIndex)") {
                if (current != null && min != null) "swap($current, $min)" else null     //debuggingText
            },

            _createLine("}"),

            _createLine("}")

        )
    }


    //TODO: Add the Helper method in this section --  Add the Helper method in this section
    //TODO: Add the Helper method in this section --  Add the Helper method in this section

    private fun updateVariables(value: PseudoCodeVariablesValue) {
        i = value.i
        len = value.len
        minIndex = value.minIndex
        isMinFound = value.isMinFound
        current = value.current
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

