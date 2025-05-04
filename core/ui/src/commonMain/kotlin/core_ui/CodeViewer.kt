@file:Suppress("functionName", "unused")

package core_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CodeViewer(modifier: Modifier = Modifier, code: String) {
    var theme by rememberSaveable { mutableStateOf(CodeViewerColor.availableThemes().first()) }
    var showThemeDialog by remember { mutableStateOf(false) }

    _CodeViewer(
        modifier = modifier,
        code = code,
        theme = theme,
        onThemeChangeRequest = {
            showThemeDialog = true
        }
    )

    if (showThemeDialog) {
        ThemeSelectionDialog(
            themes = CodeViewerColor.availableThemes(),
            currentTheme = theme,
            onDismissRequest = { showThemeDialog = false },
            onThemeSelected = {
                theme = it
                showThemeDialog = false
            }
        )
    }
}



@Composable
private fun _CodeViewer(modifier: Modifier = Modifier, code: String, theme: CodeViewerTheme,onThemeChangeRequest: () -> Unit) {
    _CodeViewer(
        modifier = modifier,
        code = code,
        borderColor = theme.border,
        backgroundColor = theme.background,
        toolbarBackgroundColor = theme.toolbarBackground,
        textColor = theme.text,
        commentTextColor = theme.highlight,
        onThemeChangeRequest =onThemeChangeRequest
    )
}


@Composable
private fun _CodeViewer(
    modifier: Modifier = Modifier,
    code: String,
    borderColor: Color,
    backgroundColor: Color,
    toolbarBackgroundColor: Color,
    textColor: Color,
    commentTextColor: Color,
    onThemeChangeRequest:()->Unit,
) {
    var fontSize by remember { mutableStateOf(15.sp) }
    val clipboardManager = LocalClipboardManager.current
    val viewerElevation = 8.dp

    Surface(
        shadowElevation = viewerElevation
    ) {
        Column(
            modifier = modifier
                .width(IntrinsicSize.Max)
                .border(2.dp, borderColor)
                .background(backgroundColor)
        ) {
            _ToolBar(
                modifier = Modifier.fillMaxWidth(),
                elevation = viewerElevation + 2.dp,
                background = toolbarBackgroundColor,
                onIncreaseRequest = {
                    fontSize = (fontSize.value + 2f).sp
                },
                onDecreaseRequest = {
                    if (fontSize > 8.sp) fontSize = (fontSize.value - 2f).sp
                },
                onCopyRequest = {
                    clipboardManager.setText(AnnotatedString(code))
                    GlobalMessenger.updateUiMessage("Copied")
                },
                onThemeChangeRequest=onThemeChangeRequest
            )
            _Viewer(
                modifier = Modifier.padding(8.dp),
                code = code,
                fontSize = fontSize,
                textColor = textColor,
                commentTextColor = commentTextColor
            )
        }
    }
}


@Composable
private fun _Viewer(
    modifier: Modifier = Modifier,
    code: String,
    fontSize: TextUnit,
    textColor: Color,
    commentTextColor: Color,
) {
    val lineHeightMultiplier = 1.5f
    val textStyle = MaterialTheme.typography.bodyLarge.copy(
        fontSize = fontSize,
        fontFamily = FontFamily.Monospace,
        color = textColor,
        lineHeight = fontSize * lineHeightMultiplier,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.Both
        )
    )

    val annotatedString = buildAnnotatedString {
        code.lines().forEach { line ->
            if (line.contains("//")) {
                val index = line.indexOf("//")
                append(line.substring(0, index))
                withStyle(
                    SpanStyle(
                        fontSize = fontSize * 0.87f,
                        color = commentTextColor
                    )
                ) {
                    append(line.substring(index))
                }
            } else {
                append(line)
            }
            append("\n")
        }
    }

    BasicText(
        modifier = modifier,
        text = annotatedString,
        style = textStyle
    )
}

@Composable
private fun _ToolBar(
    modifier: Modifier = Modifier,
    elevation: Dp,
    background: Color,
    onIncreaseRequest: () -> Unit,
    onDecreaseRequest: () -> Unit,
    onCopyRequest: () -> Unit,
    onThemeChangeRequest: () -> Unit,
) {

    val contentColor=background.textColor()
    Surface(
        modifier = modifier,
        shadowElevation = elevation
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(background),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onIncreaseRequest) {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = "Increase",
                    tint =contentColor
                )
            }
            IconButton(onClick = onDecreaseRequest) {
                Icon(
                    Icons.Outlined.Remove,
                    contentDescription = "Decrease",
                    tint =contentColor
                )
            }
            IconButton(onClick = onCopyRequest) {
                Icon(
                    Icons.Outlined.ContentCopy,
                    contentDescription = "Copy code",
                    tint =contentColor
                )
            }
            IconButton(onClick =onThemeChangeRequest ) {
                Icon(
                    Icons.Outlined.Palette,
                    contentDescription = "Theme Change",
                    tint = contentColor
                )
            }
        }
    }

}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ThemeSelectionDialog(
    themes: List<CodeViewerTheme>,
    currentTheme: CodeViewerTheme?,
    onDismissRequest: () -> Unit,
    onThemeSelected: (CodeViewerTheme) -> Unit
) {
    val dummyCode = """
        fun greet(name: String) {
            println("Hello!") //message
        }
    """.trimIndent()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Select Theme") },
        text = {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                themes.forEach { namedTheme ->
                    val isSelected = namedTheme == currentTheme
                    Card(
                        modifier = Modifier
                            .clickable { onThemeSelected(namedTheme) }
                            .border(
                                width = if (isSelected) 3.dp else 1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .background(namedTheme.background)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = namedTheme.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = namedTheme.text,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            _Viewer(
                                modifier = Modifier
                                    .background(namedTheme.background),
                                code = dummyCode,
                                fontSize = 12.sp,
                                textColor = namedTheme.text,
                                commentTextColor = namedTheme.comment
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Close")
            }
        }
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
    val combinedPattern =
        "$keywordPattern|$functionPattern|$operatorPattern|$literalPattern|$numberPattern|$stringPattern|\\s+|."

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
