package core.commonui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.LocalContentColor
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.withStyle

@Composable
fun CodeViewer(modifier: Modifier = Modifier,code:String) {
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
                    fontSize = 10.sp,
                    color = LocalContentColor.current.copy(alpha = 0.6f)
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
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Black
        )
    )

}