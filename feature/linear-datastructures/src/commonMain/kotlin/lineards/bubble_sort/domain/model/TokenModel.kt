@file:Suppress("UNUSED")
package lineards.bubble_sort.domain.model

/**
 * Will be help the UI to highlight the text as a code editor does, Since It is UI responsibility to
 * color the text that is do not hold any color here
 * - Keyword and operator is common and there are only few of them so UI should identify them,because
 * if we mention them here then they will be duplicate for each algorithm which is unnecessary
 */
data class TokenModel(
    /** A user-defined name for variables or functions, such as "myVariable*/
    val identifier: List<String>,
    /**A literal value such as a number, string, or boolean (e.g., 123, "text", true)*/
    val literal: List<String>,
    /**A function declaration, like "myFunction()*/
    val function: List<String>,
)