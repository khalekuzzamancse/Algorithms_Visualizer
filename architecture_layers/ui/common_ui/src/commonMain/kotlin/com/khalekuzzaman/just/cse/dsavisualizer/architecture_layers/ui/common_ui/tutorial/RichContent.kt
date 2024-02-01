package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.LinkType
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.RichContent
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.RichContentCombiner
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.TextParser
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.TextType
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.TutorialContentBuilder
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.network_image.ImageLoader

@Composable
fun RichContent(richString:String) {
    val parsedContents = TextParser(richString).parseText()
    Column {
        RichContentCombiner(parsedContents).parse().forEach { content ->
            when (content) {
                is RichContent.Text -> {
                    Text(content.text)
                }
                is RichContent.Image -> {
                   ImageLoader(
                       url="https://www.tutorialspoint.com/data_structures_algorithms/images/linear_search.gif"
                   )
                }
                is RichContent.Video -> {
                    ImageLoader(url = "https://i.ytimg.com/vi/3On8Wk1ufZ0/maxresdefault.jpg")
                }

            }

        }
    }
}
@Composable
fun RichContentUI() {
    val inputString =
        "<b>BoldText<b/> Normal Text <img:www.com> Normal Text <vid:www.video.com> Normal " +
                "<high> High Lighted Text</high>"
    val generatedString = TutorialContentBuilder()
        .appendText("This is regular text", TextType.NONE)
        .appendText("This is bold text", TextType.BOLD)
        .appendText("This is regular text", TextType.NONE)
        .appendLink("image-link-here", LinkType.IMAGE)
        .appendText("This is highlighted text", TextType.HIGHLIGHT)
        .appendLink("video-link-here", LinkType.VIDEO)
        .build()
    RichContent(generatedString)
}
