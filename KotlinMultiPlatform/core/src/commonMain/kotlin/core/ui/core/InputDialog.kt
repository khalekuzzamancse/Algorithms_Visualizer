@file:Suppress("FunctionName")

package core.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.outlined.ManageSearch
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.ui.SpacerHorizontal
import core.ui.SpacerVertical

@Composable
fun ArrayInputView(
    initialList: String = "10, 5, 4, 13, 8",
    navigationIcon:@Composable ()->Unit={},
    onConfirm: (List<Int>) -> Unit,
) {
    var arrayInput by rememberSaveable { mutableStateOf(initialList) }
    val arrayList = _ParseArrayInput(arrayInput)
    val isInputValid = arrayList.isNotEmpty()
    val keyboardController = LocalSoftwareKeyboardController.current
    _LayoutStrategy(
        enableStart = isInputValid,
        onStartClick = {
            keyboardController?.hide()
            onConfirm(arrayList)
        },
        navigationIcon =navigationIcon ,
        input = {
            _ArrayInputField(arrayInput = arrayInput) { newValue ->
                arrayInput = _FilterArrayInput(newValue)
            }
        }
    )
}

@Composable
fun SearchInputView(
    onStartRequest: (List<Int>, target: Int) -> Unit,
    navigationIcon:@Composable ()->Unit={}
) {
    var arrayInput by rememberSaveable { mutableStateOf("10 20 30 40 50 60") }
    var targetInput by rememberSaveable { mutableStateOf("") }
    val arrayList = _ParseArrayInput(arrayInput)
    val isInputValid = arrayList.isNotEmpty() && targetInput.toIntOrNull() != null
    val keyboardController = LocalSoftwareKeyboardController.current
    _LayoutStrategy(
        navigationIcon = navigationIcon,
        enableStart = isInputValid,
        onStartClick = {
            keyboardController?.hide()
            val targetValue = targetInput.toIntOrNull()
            if (targetValue != null) {
                onStartRequest(arrayList, targetValue)
            }
        },
        input = {
            _ArrayInputField(
                arrayInput=arrayInput
            ) { newValue ->
                arrayInput = _FilterArrayInput(newValue)
            }
            _TargetInputField(targetInput) { newValue ->
                targetInput = _FilterNumericInput(newValue)
            }
        }
    )

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private  fun _LayoutStrategy(
    enableStart:Boolean,
    onStartClick: () -> Unit,
    navigationIcon:@Composable ()->Unit,
    input:@Composable (Modifier)->Unit
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = navigationIcon
            )
        }
    ){
        Box(modifier = Modifier.padding(it).fillMaxWidth(), contentAlignment = Alignment.Center){
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(16.dp)
            ) {
                input(Modifier)
                _ConfirmButton(
                    isEnabled = enableStart,
                    onClick =onStartClick
                )
                SpacerVertical(8)
                _InputInstructions(
                    listOf(
                        "Input the array elements separated by space or comma",
                        "Enter the target number you are searching for",
                        "Click 'Start Visualization' to begin"
                    )
                )
                PlayInstruction()

            }
        }
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
  SpacerVertical(4)
    _InstructionList(instructions = instructions)
  SpacerVertical(16)
}

@Composable
private fun _ArrayInputField(
    modifier: Modifier=Modifier,
    arrayInput: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        modifier = modifier.widthIn(max = 500.dp),
        label = "Array",
        value = arrayInput,
        onValueChange = onValueChange,
        keyboardType = KeyboardType.Text,
        leadingIcon = Icons.AutoMirrored.Outlined.List
    )
    SpacerVertical(16)
}

@Composable
private fun _TargetInputField(
    targetInput: String,
    onValueChange: (String) -> Unit
) {
    CustomTextField(
        modifier = Modifier.widthIn(max = 200.dp),
        label = "Target",
        value = targetInput,
        onValueChange = onValueChange,
        keyboardType = KeyboardType.Number,
        leadingIcon = Icons.AutoMirrored.Outlined.ManageSearch
    )
    SpacerVertical(24)
}

@Composable
private fun _ConfirmButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier
    ) {
        Text(text = "Start Visualization")
    }
}

@Composable
private fun _InstructionList(modifier: Modifier=Modifier,instructions: List<String>) {
    Column(
        modifier = modifier
    ) {
        instructions.forEach { instruction ->
            _BulletPointText(text = instruction)
        }
    }
}

@Composable
private fun _BulletPointText(text: String) {
    Row(
        modifier = Modifier
    ) {
        Box(Modifier
            .padding(top = 4.dp)//for align center
            .size(8.dp)
            .background( color = MaterialTheme.colorScheme.primary, shape = CircleShape)
            .align(Alignment.CenterVertically)
        )
        SpacerHorizontal(4)
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
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
