package core.commonui.dialogue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.outlined.ManageSearch
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInputDialog(
    onDismiss: (List<Int>, target: Int) -> Unit, // Kept to avoid breaking existing code
    onConfirm: (List<Int>, target: Int) -> Unit,
    navigationIcon: @Composable () -> Unit,
) {
    var arrayInput by rememberSaveable { mutableStateOf("10 20 30 40 50 60") }
    var targetInput by rememberSaveable { mutableStateOf("") }
    val arrayList = arrayInput.split("\\s*,\\s*|\\s+".toRegex()) // Split by comma or space
        .mapNotNull { it.toIntOrNull() }

    val isInputValid = arrayList.isNotEmpty() && targetInput.toIntOrNull() != null
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
                "Enter the target number you are searching for",
                "Click 'Start Visualization' to begin"
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

            Spacer(modifier = Modifier.height(16.dp))

            // Target Input Field
            CustomTextField(
                leadingIcon = Icons.AutoMirrored.Outlined.ManageSearch,
                label = "Target",
                value = targetInput,
                onValueChange = { targetInput = it.filter { char -> char.isDigit() } },
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Start Visualization Button
            Button(
                onClick = {
                    val targetValue = targetInput.toIntOrNull()
                    if (targetValue != null) {
                        onConfirm(arrayList, targetValue)
                    }
                },
                enabled = isInputValid,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Start Visualization")
            }
        }
    }
}


//@Composable
//fun SearchInputDialog(
//    onDismiss: (List<Int>, target: Int) -> Unit,
//    onConfirm: (List<Int>, target: Int) -> Unit
//) {
//    var array by rememberSaveable { mutableStateOf("10 20 30 40 50 60") }
//    var target by rememberSaveable { mutableStateOf("") }
//    val list = array.split("\\s*,\\s*|\\s+".toRegex()) // Split by comma or space
//    AlertDialog(
//        onDismissRequest = {
//            val numberList = list.mapNotNull { it.toIntOrNull() }
//            try {
//                onConfirm(numberList, target.toInt())
//            } catch (_: Exception) {
//            }
//        },
//        title = { Text(text = "Enter List of Numbers") },
//        text = {
//            Column {
//                CustomTextField(
//                    label = "Array",
//                    value = array,
//                    onValueChange = {
//                        target = it.filter { char -> char.isDigit() || char == ',' }
//                    },
//                    keyboardType = KeyboardType.Number
//                )
//                Spacer(Modifier.height(16.dp))
//                CustomTextField(
//                    label = "Target",
//                    value = target,
//                    onValueChange = { target = it.filter {char-> char.isDigit() } },
//                    keyboardType = KeyboardType.Number
//                )
//            }
//
//
//        },
//        confirmButton = {
//            Button(
//                onClick = {
//                    val numberList = list.mapNotNull { it.toIntOrNull() }
//                    try {
//                        onConfirm(numberList, target.toInt())
//                    } catch (_: Exception) {
//                    }
//                }
//            ) {
//                Text("OK")
//            }
//        },
//        dismissButton = {
//            Button(onClick = {
//                val numberList = list.mapNotNull { it.toIntOrNull() }
//                try {
//                    onConfirm(numberList, target.toInt())
//                } catch (_: Exception) {
//                }
//            }) {
//                Text("Cancel")
//            }
//        }
//    )
//
//}
//

