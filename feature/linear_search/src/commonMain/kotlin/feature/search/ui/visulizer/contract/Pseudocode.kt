package feature.search.ui.visulizer.contract
/**
 * Used as separate from other module pseudocode so that
 * that module can easily detach from other module
 */

internal interface Pseudocode{
    data class Line(
        val line: String,
        val highLighting: Boolean = false,
        val lineNumber: Int,
    ): Pseudocode

}

