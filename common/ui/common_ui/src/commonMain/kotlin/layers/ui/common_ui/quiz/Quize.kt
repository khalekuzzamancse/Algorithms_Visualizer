package layers.ui.common_ui.common.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


data class QuizCard(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

@Composable
fun QuizListScreen() {
    val quizList = remember {
        listOf(
            QuizCard(
                question = "What is the capital of France?",
                options = listOf("Berlin", "Paris", "Madrid", "Rome"),
                correctAnswerIndex = 1
            ),
            QuizCard(
                question = "Which planet is known as the Red Planet?",
                options = listOf("Earth", "Mars", "Jupiter", "Saturn"),
                correctAnswerIndex = 1
            ),
            // Add more quiz cards as needed
        )
    }

    var selectedAnswers by remember { mutableStateOf(List(quizList.size) { -1 }) }
    var isQuizSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(quizList) { quiz ->
                QuizCardItem(
                    quiz = quiz,
                    selectedAnswer = selectedAnswers[quizList.indexOf(quiz)],
                    onAnswerSelected = { answerIndex ->
                        selectedAnswers = selectedAnswers.toMutableList().apply {
                            this[quizList.indexOf(quiz)] = answerIndex
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isQuizSubmitted = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Submit Answers")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isQuizSubmitted) {
            QuizResult(quizList, selectedAnswers)
        }
    }
}

@Composable
fun QuizCardItem(
    quiz: QuizCard,
    selectedAnswer: Int,
    onAnswerSelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = quiz.question,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            quiz.options.forEachIndexed { index, option ->
                QuizOptionItem(
                    option = option,
                    isSelected = selectedAnswer == index,
                    onOptionSelected = {
                        onAnswerSelected(index)
                    }
                )
            }
        }
    }
}

@Composable
fun QuizOptionItem(
    option: String,
    isSelected: Boolean,
    onOptionSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = onOptionSelected
            )
            .padding(8.dp)
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.RadioButtonChecked,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = option)
    }
}

@Composable
fun QuizResult(
    quizList: List<QuizCard>,
    selectedAnswers: List<Int>
) {
    var correctCount = 0

    for (i in quizList.indices) {
        if (selectedAnswers[i] == quizList[i].correctAnswerIndex) {
            correctCount++
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Result:",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        for (i in quizList.indices) {
            val isCorrect = selectedAnswers[i] == quizList[i].correctAnswerIndex
            QuizResultItem(
                question = quizList[i].question,
                isCorrect = isCorrect,
                selectedOption = quizList[i].options[selectedAnswers[i]],
                correctOption = quizList[i].options[quizList[i].correctAnswerIndex]
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total Correct Answers: $correctCount / ${quizList.size}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
fun QuizResultItem(
    question: String,
    isCorrect: Boolean,
    selectedOption: String,
    correctOption: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = question,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = "Your Answer: $selectedOption",
            style = MaterialTheme.typography.bodyMedium,
            color = if (isCorrect) MaterialTheme.colorScheme.primary else Color.Red,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = "Correct Answer: $correctOption",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}



