package feature.search.ui.visulizer.contract
/**
 * Used as separate from other module pseudocode so that
 * that module can easily detach from other module
 *
 */

internal interface Pseudocode{
    /**
     * @param indentationLevel the number of space from the left side
     * @param variableState is the variable value ,at this moment
     */
    data class Line(
        val line: String,
        val highLighting: Boolean = false,
        val lineNumber: Int,
        val indentationLevel:Int=0,
        val variableState:String?=null
    ): Pseudocode

}

