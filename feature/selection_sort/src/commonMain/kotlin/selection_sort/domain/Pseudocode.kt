package selection_sort.domain

import androidx.compose.ui.text.AnnotatedString

/**
 * - Used as separate from other module pseudocode so  that module can easily detach from other module
 * - Instead of Raw string,using Annotated String so that we can highlight it
 *
 */

interface Pseudocode {
    /**
     * @param indentationLevel the number of space from the left side
     * @param variableState is the variable value ,at this moment
     * @param line as [AnnotatedString] so that we can highlight it if needed
     */
    data class Line(
        val line: AnnotatedString,
        val highLighting: Boolean = false,
        val lineNumber: Int,
        val indentationLevel: Int = 0,
        val topPaddingLevel: Int = 0,
        val variableState: AnnotatedString? = null
    ) : Pseudocode

}

