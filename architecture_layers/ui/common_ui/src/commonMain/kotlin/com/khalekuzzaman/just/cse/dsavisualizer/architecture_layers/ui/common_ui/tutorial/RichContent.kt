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
fun RichContent(richString: String) {
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

@Composable
fun RichContentUI() {
    val inputString =
        "<b>BoldText<b/> Normal Text <img:www.com> Normal Text <vid:www.video.com> Normal " +
                "<high> High Lighted Text</high>"
    val generatedString = TutorialContentBuilder()
        .appendText(
            "Linear search, also referred to as sequential search, is a fundamental algorithm used to find a specific element in a collection.",
            TextType.NONE
        )
        .appendText(
            " It is a simple and intuitive approach where each element is sequentially checked until a match is found or the end of the collection is reached.",
            TextType.NONE
        )
        .appendText(
            " This search method is commonly employed for small datasets or unsorted lists.",
            TextType.NONE
        )
        .appendLink("linear-search-process.png", LinkType.IMAGE)
        .appendText(
            " Let's dive into the linear search process with an illustration:",
            TextType.NONE
        )
        .appendText(" [Image: Linear Search Process]", TextType.NONE)
        .appendText(
            " In the image, the bold numbers represent the elements in the collection, and the target element is highlighted.",
            TextType.NONE
        )
        .appendText(
            " The linear search starts from the beginning, comparing each element with the target value.",
            TextType.NONE
        )
        .appendText(
            " If a match is found, the search terminates; otherwise, it continues until the end of the list is reached.",
            TextType.NONE
        )
        .appendText(
            " The highlighted text emphasizes the crucial steps in the search process.",
            TextType.HIGHLIGHT
        )
        .appendText(
            " Linear search is particularly useful when the dataset is small or unordered, offering a simple solution for locating elements.",
            TextType.NONE
        )
        .appendText(
            " However, for larger datasets, more advanced algorithms like binary search may be preferred for improved efficiency.",
            TextType.NONE
        )
        .appendLink("linear-search-demo.mp4", LinkType.VIDEO)
        .appendText(
            " To further illustrate the concept, you can watch a demonstration in the following video:",
            TextType.NONE
        )
        .appendText(" [Video: Linear Search Demo]", TextType.NONE)
        .build()

    RichContent(generatedString)
}
