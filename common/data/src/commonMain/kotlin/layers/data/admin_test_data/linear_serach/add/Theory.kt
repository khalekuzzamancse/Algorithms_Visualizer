package layers.data.admin_test_data.linear_serach.add

import kotlinx.coroutines.runBlocking
import layers.data.TutorialCRUD
import layers.data.admin_test_data.content_builder.LinkType
import layers.data.admin_test_data.content_builder.TextType
import layers.data.admin_test_data.content_builder.TutorialContentBuilder
import layers.data.components.AlgoName
import layers.data.components.Theory

private val linearSearch = TutorialContentBuilder()
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
fun main() {
    runBlocking {
        val data = Theory(
            algoName = AlgoName.LinearSearch,
            content = linearSearch
        )
        TutorialCRUD().addTheory(data)
    }
}