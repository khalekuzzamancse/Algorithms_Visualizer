package core.commonui.dialogue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.commonui.CustomTextField

/**
 * * Completely stateless component ,directly can be reused,
 * * date is returned as Milli second so that this component does not need to coupled with a dateConverter,
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArrayInputDialog(
    initialList: String = "10, 5, 4, 13, 8",
    onDismiss: (List<Int>) -> Unit = {}, //keep to avoid break existing code
    onConfirm: (List<Int>) -> Unit,
    navigationIcon: @Composable () -> Unit,
) {

    var arrayInput by rememberSaveable { mutableStateOf(initialList) }
    val arrayList = arrayInput.split("\\s*,\\s*|\\s+".toRegex()) // Split by comma or space
        .mapNotNull { it.toIntOrNull() }
    val isInputValid = arrayList.isNotEmpty()
    Scaffold(
        topBar = {
          TopAppBar(
              title = {},
              navigationIcon =   navigationIcon
          )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Input Instructions
            Text(
                text = "Instructions:",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            val instructions = listOf(
                "Input the array elements separated by space or comma",
                "Click 'Start Visualization' to begin."
            )
            InstructionList(instructions = instructions)

            Spacer(modifier = Modifier.height(16.dp))

            // Array Input Field
            CustomTextField(
                label = "Array",
                value = arrayInput,
                onValueChange = {
                    arrayInput = it.filter { char -> char.isDigit() || char == ',' || char == ' ' }
                },
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.AutoMirrored.Outlined.List
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Start Visualization Button
            Button(
                onClick = {
                    onConfirm(arrayList)

                },
                enabled = isInputValid,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Start Visualization")
            }
        }
    }



}
@Composable
fun InstructionList(
    instructions: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        instructions.forEach { instruction ->
            BulletPointText(text = instruction)
        }
    }
}

@Composable
fun BulletPointText(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        // Bullet point
        Text(
            text = "•",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 8.dp)
        )
        // Instruction text
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

