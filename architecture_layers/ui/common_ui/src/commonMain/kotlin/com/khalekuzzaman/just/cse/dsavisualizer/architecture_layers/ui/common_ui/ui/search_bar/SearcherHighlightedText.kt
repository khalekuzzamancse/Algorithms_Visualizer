package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.search_bar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration


class SearcherHighlightedText {
    fun getHighLightedString(text: String, highLightedText: String): AnnotatedString {
        val annotatedEmailString: AnnotatedString = buildAnnotatedString {
            append(text)
            val urls =findText(text, highLightedText)
            urls.forEach { pair ->
                addStyle(
                    style = SpanStyle(
                        background = Color.Yellow,
                        textDecoration = TextDecoration.None
                    ),
                    start = pair.first,
                    end = pair.second + 1
                )
            }
        }
        return annotatedEmailString
    }

    private fun findText(text: String, key: String): List<Pair<Int, Int>> {

        val originalText=text.lowercase()
        val searchText=key.lowercase().toRegex()
        val indices = mutableListOf<Pair<Int, Int>>()

        searchText.findAll(originalText).forEach { matchResult ->
            val startIndex = matchResult.range.first
            val endIndex = matchResult.range.last
            indices.add(startIndex to endIndex)
        }
        return indices
    }
}