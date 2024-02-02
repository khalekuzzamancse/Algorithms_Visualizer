package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.LinkType
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.RichContent
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.RichContentCombiner
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.TextParser
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.TextType
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.content_and_parser.TutorialContentBuilder
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.network_image.ImageLoader

@Composable
internal fun RichContent(richString: String) {
    val parsedContents = TextParser(richString).parseText()
    LazyColumn (Modifier.padding(8.dp)){
        items(RichContentCombiner(parsedContents).parse()) { content ->
            when (content) {
                is RichContent.Text -> {
                    Text(content.text)
                }

                is RichContent.Image -> {
                    ImageLoader(
                        url = "https://www.tutorialspoint.com/data_structures_algorithms/images/linear_search.gif"
                    )
                }

                is RichContent.Video -> {
                    ImageLoader(url = "https://i.ytimg.com/vi/3On8Wk1ufZ0/maxresdefault.jpg")
                }

            }

        }

    }
}
