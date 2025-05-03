@file:Suppress("unused")
package core_ui.core

import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp


/**

 */
data class Token(
    /**A reserved word in pseudocode, such as "if", "for", or "return",
     * Since there are few only and common to all that is why defining here
     **/
    internal val keyword: List<String> = listOf("for", "if", "return", "while", "break"),

    /** An operator, such as "+", "-", "=", "==", etc,
     *  Since there are few only and common to all that is why defining here
     **/
    val operator: List<String> = listOf("=", "<", "++", "==", "[", "]"),


    /** A user-defined name for variables or functions, such as "myVariable*/
    val identifier: List<String>,

    /**A literal value such as a number, string, or boolean (e.g., 123, "text", true)*/
    val literal: List<String>,
    /**A function declaration, like "myFunction()*/
    val function: List<String>,
)

@Composable
fun CodeViewer(modifier: Modifier = Modifier, code: String, token: Token=Token(
    literal = emptyList(),
    function = emptyList(),
    identifier = emptyList()
)) {
    val lines = code.lines()
    val annotatedStringBuilder = AnnotatedString.Builder()

    for (line in lines) {
        if (line.contains("//")) {
            val index = line.indexOf("//")
            val codePart = line.substring(0, index)
            val commentPart = line.substring(index)

            // Append code part with normal styling
            annotatedStringBuilder.append(codePart)

            // Append comment part with reduced font size and opacity
            annotatedStringBuilder.withStyle(
                style = SpanStyle(
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
                )
            ) {
                append(commentPart)
            }
        } else {
            // Append the entire line if no comment is present
            annotatedStringBuilder.append(line)
        }
        // Add a newline after each line
        annotatedStringBuilder.append("\n")
    }

    BasicText(
        modifier = modifier,
        text = annotatedStringBuilder.toAnnotatedString(),
        style = androidx.compose.ui.text.TextStyle(
            fontSize = 15.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.primary
        )
    )

}

data class CodeColors(
    val keywordColor: Color,
    val operatorColor: Color,
    val literalColor: Color,
    val functionColor: Color,
    val defaultColor: Color
)
val colors = CodeColors(
    keywordColor = Color.Red,
    operatorColor = Color.Blue,
    literalColor = Color.Green,
    functionColor = Color.Magenta,
    defaultColor = Color.Black
)

fun processCodePartWithTokens(
    codePart: String,
    token: Token,
): AnnotatedString {

    val annotatedStringBuilder = AnnotatedString.Builder()

    // Define regex patterns for tokens
    val keywordPattern = "\\b(${token.keyword.joinToString("|")})\\b"
    val functionPattern = "\\b(${token.function.joinToString("|")})\\b"
    val operatorPattern = token.operator.joinToString("|") { "\\Q$it\\E" }
    val literalPattern = "\\b(${token.literal.joinToString("|")})\\b"
    val numberPattern = "\\b\\d+(\\.\\d+)?\\b"
    val stringPattern = "\".*?\"|'.*?'" // Matches string literals

    // Combine all patterns into one (corrected)
    val combinedPattern = "$keywordPattern|$functionPattern|$operatorPattern|$literalPattern|$numberPattern|$stringPattern|\\s+|."

    val regex = Regex(combinedPattern)

    val matches = regex.findAll(codePart)

    for (match in matches) {
        val text = match.value
        val style = when {
            text.matches(Regex("\\s+")) -> null // Whitespace, no style
            token.keyword.contains(text) -> SpanStyle(color = colors.keywordColor)
            token.function.contains(text) -> SpanStyle(color = colors.functionColor)
            token.operator.contains(text) -> SpanStyle(color = colors.operatorColor)
            token.literal.contains(text) -> SpanStyle(color = colors.literalColor)
            text.matches(Regex(numberPattern)) -> SpanStyle(color = colors.literalColor)
            text.matches(Regex(stringPattern)) -> SpanStyle(color = colors.literalColor)
            else -> SpanStyle(color = colors.defaultColor)
        }

        if (style != null) {
            annotatedStringBuilder.withStyle(style) {
                append(text)
            }
        } else {
            annotatedStringBuilder.append(text)
        }
    }

    return annotatedStringBuilder.toAnnotatedString()
}
