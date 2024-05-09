package feature.navigation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class QuizCard(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

@Composable
fun QuizCardComposable(quizCard: QuizCard, onOptionSelected: (Int) -> Unit) {
    var selectedIndex by remember { mutableStateOf(-1) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = quizCard.question, style = MaterialTheme.typography.headlineSmall)
        quizCard.options.forEachIndexed { index, option ->
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { selectedIndex = index; onOptionSelected(index) }
                    .padding(vertical = 8.dp)) {
                RadioButton(
                    selected = selectedIndex == index,
                    onClick = { selectedIndex = index; onOptionSelected(index) }
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun QuizListComposable(quizCards: List<QuizCard>) {
    val answers = remember { mutableStateMapOf<Int, Int>() }
    val showResults = remember { mutableStateOf(false) }

    LazyColumn {
        itemsIndexed(quizCards) { index, quizCard ->
            QuizCardComposable(quizCard) { selectedIndex ->
                answers[index] = selectedIndex
            }
        }
        item {
            Button(onClick = { showResults.value = true }) {
                Text("Submit")
            }
        }
        item {
            if (showResults.value) {
                quizCards.forEachIndexed { index, quizCard ->
                    val isCorrect = quizCard.correctAnswerIndex == answers[index]
                    Text(
                        text = "Question ${index + 1}: ${if (isCorrect) "Correct" else "Incorrect"}",
                        color = if (isCorrect) Color.Green else Color.Red
                    )
                }
            }
        }
    }



}

@Composable
fun QuizDemo() {
    val dummyQuizList = listOf(
        QuizCard(
            question = "What is the capital of Germany?",
            options = listOf("Berlin", "Paris", "Madrid", "Rome"),
            correctAnswerIndex = 0
        ),
        QuizCard(
            question = "Which planet is known as the Blue Planet?",
            options = listOf("Mars", "Earth", "Jupiter", "Saturn"),
            correctAnswerIndex = 1
        ),
        QuizCard(
            question = "What is the largest mammal?",
            options = listOf("Elephant", "Blue Whale", "Giraffe", "Lion"),
            correctAnswerIndex = 1
        ),
        QuizCard(
            question = "Which programming language is used for Android app development?",
            options = listOf("Java", "Kotlin", "Swift", "Python"),
            correctAnswerIndex = 1
        )
        // Add more dummy quiz cards as needed
    )

    QuizListComposable(dummyQuizList)

}