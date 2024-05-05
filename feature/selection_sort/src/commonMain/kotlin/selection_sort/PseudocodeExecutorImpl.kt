package selection_sort

import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/* TODO:
 - Make a Adapter that will takes the algo state and return the new pseudocode
 by using the pseudocode builder.
 The adapter may observe the algo state and then emit new pseudocode for each state change.
 *
 */
/**
 * - Expose the variable that state will be used for debugging
 */
internal class PseudocodeExecutorImpl : PseudocodeExecutor {
    private val _codes = MutableStateFlow(DebuggablePseudocodeBuilder().build())
    override val pseudocode = _codes.asStateFlow()

    override fun highLightLine(lineNumber: Int) {
        TODO("Not yet implemented")
    }
}


internal data class PseudoCodeVariablesValue(
    val i: String? = null,
    val len: String? = null,
    val minIndex: String? = null,
    val isMinFound: String? = null,
    val current: String? = null,
    val min: String? = null
)
/*
  Need to maintain the state of:
  len, i, minIndex, isMinFound ,current=list[i], min=list[minIndex]
   */

internal class DebuggablePseudocodeBuilder() {
    private var i: String? = null
    private var len: String? = null
    private var minIndex: String? = null
    private var isMinFound: String? = null
    private var current: String? = null
    private var min: String? = null


    fun build(value:PseudoCodeVariablesValue?=null): List<LineForPseudocode> {
        value?.let { updateVariables(it) }

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

            _createLine("swap( list[i], list[minIndex] )") {
                if (current != null && min != null) "swap($current, $min)" else null     //debuggingText
            },

            _createLine("}"),

            _createLine("}")

        )
    }
    private fun updateVariables(value: PseudoCodeVariablesValue){
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


}


