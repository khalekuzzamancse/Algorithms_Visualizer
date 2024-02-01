package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


sealed class Content {
    data class Text(val text: String, val type: TextType) : Content()

    data class Link(val text: String, val type: LinkType) : Content()

}


internal class TextParser(private val input: String) {
    fun parseText(): List<RichContent> {
        val contents = mutableListOf<RichContent>()

        val regex = Regex("<b>(.*?)<b/>|<high>(.*?)</high>|<img:(.*?)>|<vid:(.*?)>")
        val matches: Sequence<MatchResult> = regex.findAll(input)

        var currentIndex = 0

        for (match in matches) {
            val textBeforeMatch = input.substring(currentIndex, match.range.first)
            if (textBeforeMatch.isNotBlank()) {
                contents.add(RichContent.Text(AnnotatedString(textBeforeMatch)))
            }
            when {
                match.groupValues[1].isNotBlank() -> contents.add(
                    RichContent.Text(
                        AnnotatedString(
                            text = match.groupValues[1],
                            spanStyle = SpanStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    )
                )

                match.groupValues[2].isNotBlank() -> contents.add(
                    RichContent.Text(
                        AnnotatedString(
                            text = match.groupValues[2],
                            spanStyle = SpanStyle(
                                color = Color.Red
                            )
                        )
                    )
                )

                match.groupValues[3].isNotBlank() -> contents.add(
                    RichContent.Image(
                        match.groupValues[3]
                    )
                )


                match.groupValues[4].isNotBlank() -> contents.add(
                    RichContent.Video(
                        match.groupValues[4]
                    )
                )
            }

            currentIndex = match.range.last + 1
        }

        // Add the remaining text after the last match
        val remainingText = input.substring(currentIndex).trim()
        if (remainingText.isNotBlank()) {
            contents.add(RichContent.Text(AnnotatedString(remainingText)))
        }

        return contents
    }
}


internal class RichContentCombiner(
    private val parsedContents: List<RichContent>
) {
    private var annotatedString = AnnotatedString("")
    private val richContents = mutableListOf<RichContent>()
    fun parse(): List<RichContent> {
        parsedContents.forEachIndexed { index, content ->
            when (content) {
                is RichContent.Text -> {
                    onTextFound(content)
                    val isThisLastContent = index == parsedContents.size - 1
                    if (isThisLastContent) {
                        appendText()
                    }
                }

                else -> {
                    onNonTextFound(content)
                }
            }
        }
        return richContents

    }

    private fun onTextFound(content: RichContent.Text) {
        annotatedString = annotatedString.plus(content.text)
    }


    private fun onNonTextFound(content: RichContent) {
        appendText()
        when (content) {
            is RichContent.Image -> {
                richContents.add(RichContent.Image(content.url))
            }

            is RichContent.Video -> {
                richContents.add(RichContent.Video(content.url))
            }
        }

    }
    private fun appendText() {
        richContents.add(RichContent.Text(annotatedString))
        annotatedString = AnnotatedString("")
    }
}


interface RichContent {
    data class Text(val text: AnnotatedString) : RichContent
    data class Image(val url: String) : RichContent
    data class Video(val url: String) : RichContent
}


