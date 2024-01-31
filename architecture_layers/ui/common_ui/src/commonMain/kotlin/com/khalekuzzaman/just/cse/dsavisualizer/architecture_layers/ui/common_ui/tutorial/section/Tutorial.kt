package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.section

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.AlgorithmTutorial
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.tutorial.TutorialContent
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.network_image.ImageLoader




/**
 * *Pseudocode with with steps
 * *Gif/Animation Section
 * *Pseudocode
 * *Time Complexity Analysis
 * *Implementation with multiple languages
 */


@Composable
fun AlgorithmTutorialUI(algorithmTutorial: AlgorithmTutorial) {
    LazyColumn {
        items(algorithmTutorial.tutorialSections.size) { index ->
            when (val section = algorithmTutorial.tutorialSections[index]) {
                is TutorialContent.TextContent -> {
                    TextSection(text = section.text)
                }

                is TutorialContent.ImageContent -> {
                    ImageSection(imageUrl = section.imageUrl)
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun TextSection(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
private fun ImageSection(imageUrl: String) {
    ImageLoader(
        url = imageUrl,
        modifier = Modifier.height(200.dp)
            .fillMaxWidth()

    )

}

