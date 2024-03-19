package quick_sort.ui.visulizer.contract

import androidx.compose.ui.text.AnnotatedString

/**
 * Used as separate from other module pseudocode so that
 * that module can easily detach from other module
 */

internal interface Pseudocode{
    data class Line(
        val line: AnnotatedString,
        val highLighting: Boolean = false,
        val lineNumber: Int,
    ): Pseudocode

}

