@file:Suppress("FunctionName")

package core_ui.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.outlined.ManageSearch
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ArrayInputDialog(
    initialList: String = "10, 5, 4, 13, 8",
    onConfirm: (List<Int>) -> Unit,
) {
    var arrayInput by rememberSaveable { mutableStateOf(initialList) }
    val arrayList = _ParseArrayInput(arrayInput)
    val isInputValid = arrayList.isNotEmpty()
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        _InputInstructions(
            listOf(
                "Input the array elements separated by space or comma",
                "Click 'Start Visualization' to begin"
            )
        )

        _ArrayInputField(arrayInput) { newValue ->
            arrayInput = _FilterArrayInput(newValue)
        }
        _ConfirmButton(
            isEnabled = isInputValid,
            onClick = {
                keyboardController?.hide()
                onConfirm(arrayList)
            }
        )
        Spacer(Modifier.height(8.dp))
        PlayInstruction()

    }
}

@Composable
fun SearchInputDialog(
    onConfirm: (List<Int>, target: Int) -> Unit,
) {
    var arrayInput by rememberSaveable { mutableStateOf("10 20 30 40 50 60") }
    var targetInput by rememberSaveable { mutableStateOf("") }
    val arrayList = _ParseArrayInput(arrayInput)
    val isInputValid = arrayList.isNotEmpty() && targetInput.toIntOrNull() != null
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        _InputInstructions(
            listOf(
                "Input the array elements separated by space or comma",
                "Enter the target number you are searching for",
                "Click 'Start Visualization' to begin"
            )
        )

        _ArrayInputField(arrayInput) { newValue ->
            arrayInput = _FilterArrayInput(newValue)
        }

        _TargetInputField(targetInput) { newValue ->
            targetInput = _FilterNumericInput(newValue)
        }

        _ConfirmButton(
            isEnabled = isInputValid,
            onClick = {
                keyboardController?.hide()
                val targetValue = targetInput.toIntOrNull()
                if (targetValue != null) {
                    onConfirm(arrayList, targetValue)
                }
            }
        )
        Spacer(Modifier.height(8.dp))
        PlayInstruction()

    }
}


@Composable
private fun _InputInstructions(instructions: List<String>) {
    Text(
        text = "Instructions:",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(8.dp))
    _InstructionList(instructions = instructions)
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun _ArrayInputField(
    arrayInput: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        label = "Array",
        value = arrayInput,
        onValueChange = onValueChange,
        keyboardType = KeyboardType.Text,
        leadingIcon = Icons.AutoMirrored.Outlined.List
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun _TargetInputField(
    targetInput: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        label = "Target",
        value = targetInput,
        onValueChange = onValueChange,
        keyboardType = KeyboardType.Number,
        leadingIcon = Icons.AutoMirrored.Outlined.ManageSearch
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun ColumnScope._ConfirmButton(
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
        Text(text = "Start Visualization")
    }
}

@Composable
private fun _InstructionList(instructions: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        instructions.forEach { instruction ->
            _BulletPointText(text = instruction)
        }
    }
}

@Composable
private fun _BulletPointText(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = "•",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

private fun _ParseArrayInput(input: String): List<Int> {
    return input.split("\\s*,\\s*|\\s+".toRegex())
        .mapNotNull { it.toIntOrNull() }
}

private fun _FilterArrayInput(input: String): String {
    return input.filter { char -> char.isDigit() || char == ',' || char == ' ' }
}

private fun _FilterNumericInput(input: String): String {
    return input.filter { char -> char.isDigit() }
}
