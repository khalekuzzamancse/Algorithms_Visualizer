@file:Suppress("unUsed")

package tree.binary

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.FindReplace
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.commonui.ControlIconButton
import core.commonui.CustomTextField
import core.commonui.SimulationScreenEvent
import core.commonui.SimulationScreenState
import core.commonui.SimulationSlot
import core.commonui.controller.ControllerFactory.createAutoPlayer
import core.lang.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tree.binary.core.SpacerHorizontal
import tree.binary.core.SpacerVertical
import tree.binary.core.ThemeInfo
import tree.binary.core.contentColor
import tree.binary.tree_view.TreeView
import tree.binary.tree_view.TreeViewController

class BSTViewController {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val _highLight = MutableStateFlow<String?>(null)
    private val _list = MutableStateFlow<List<Int>>(emptyList())
    private val _showControls = MutableStateFlow(true)
    private val insertPending = mutableListOf<Int>()
    private val _enableNext=MutableStateFlow(true)
    val enableNext=_enableNext.asStateFlow()
    private val autoPlayer = createAutoPlayer(::next)
    fun autoPlayRequest(delay: Int) = autoPlayer.autoPlayRequest(delay)
    val showControls = _showControls.asStateFlow()

    val highLight = _highLight.asStateFlow()
    val list = _list.asStateFlow()
    private var _isInserting=false
    //TODO: make sure these auto play time means>delayOnInsertionComplete+processingTime
    //otherwise cause Buggy Render ,
    val treeViewController = TreeViewController.create<Int>(
        delayOnInsertionComplete = 1000,
        processingTime = 1000
    )

    fun startHighLight(id: String) = _highLight.update { id }
    fun stopHighLight() = _highLight.update { null }

    fun next() {
        if (_isInserting){
            Logger.on(this.javaClass.simpleName,"Already inserting wait..")
            //TODO: Show message that wait until in previous node insert finish
            return
        }

        try {
            val value = insertPending.removeAt(0)
            scope.launch {
               _insertNode(value)
            }
        } catch (_: Exception) {
        }

    }

    private suspend  fun _insertNode(value: Int){
        _isInserting=true
        _enableNext.update { false }
            treeViewController.insert(
                value = value,
                onRunning = {
                    startHighLight("$value")
                },
                onFinish = ::stopHighLight
            )
        _isInserting=false
        _enableNext.update { true }
    }
    fun insert(values: List<Int>) {
        _list.update {it+ values }
        insertPending.addAll(values)
    }

    fun insert(value: Int) {
        _list.update { it + value }
        insertPending.add(value)
    }

    fun toggleControlsVisibility() = _showControls.update { !it }
    fun reset() {
        scope.launch {
            treeViewController.resetColor()
        }
        autoPlayer.dismiss()

    }

}

@Composable
fun BSTView(navigationIcon: @Composable () -> Unit = {}) {
    val viewController = remember { BSTViewController() }
    val controller = viewController.treeViewController
    val list = viewController.list.collectAsState().value
    val highLight = viewController.highLight.collectAsState().value
    val state by remember { mutableStateOf(SimulationScreenState()) }
    val showControls = viewController.showControls.collectAsState().value

    SimulationSlot(
        modifier = Modifier,
        state = state,
        disableControls = false,
        enableNext = viewController.enableNext.collectAsState().value,
        navigationIcon = navigationIcon,
        extraActions = {
            ControlIconButton(
                onClick = viewController::toggleControlsVisibility,
                icon = if (showControls) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                contentDescription = "Autoplay",
                enabled = true
            )
        },
        visualization = {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()
            ) {
                AnimatedVisibility(showControls){
                    Column (Modifier.fillMaxWidth()){
                        _Operations(viewController = viewController)
                        SpacerVertical(16)
                        HorizontalDivider()
                        SpacerVertical(8)
                        if (list.isNotEmpty()) {
                            Items(item = list.map { "$it" }, highLight = highLight)
                            SpacerVertical(8)
                            HorizontalDivider()
                        }

                    }
                }
                SpacerVertical(16)
                TreeView(controller = controller, modifier = Modifier.fillMaxWidth())
            }
        },
        onEvent = { event ->
            when (event) {
                is SimulationScreenEvent.AutoPlayRequest -> {
                    viewController.autoPlayRequest(event.time)
                }

                SimulationScreenEvent.NextRequest -> {
                    viewController.next()
                }

                SimulationScreenEvent.NavigationRequest -> {}
                SimulationScreenEvent.ResetRequest -> {
                    viewController.reset()
                }

                else -> {}
            }

        },
    )

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun _Operations(
    modifier: Modifier = Modifier,
    viewController: BSTViewController,
) {
    val controller = viewController.treeViewController
    val scope = rememberCoroutineScope()

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        _InputButton(
            label = "Insert",
            icon = Icons.Outlined.Add,
            title = "Enter a single or multiple number",
            initialValue = "50 40 30 45 80 70 60 65 90",
            leadingIcon = Icons.AutoMirrored.Outlined.List,
            onMultipleAdded = { values ->
                viewController.insert(values)
            },
            onSingleAdded = { value ->
                viewController.insert(value)
                scope.launch {
                    controller.insert(value)
                }
            }
        )
        _InputButton(
            label = "Search",
            icon = Icons.Outlined.Search,
            onSingleAdded = { value ->
                scope.launch {
                    controller.search(
                        value = value,
                        onRunning = {
                            viewController.startHighLight("$value")
                        },
                        onFinish = viewController::stopHighLight
                    )
                }
            }
        )
        CustomButton(
            modifier = Modifier,
            label = "Min",
            icon = Icons.Outlined.BarChart,
        ) {
            scope.launch {
                controller.findMin()
            }
        }
        CustomButton(
            modifier = Modifier,
            label = "Max",
            icon = Icons.Outlined.BarChart,
        ) {
            scope.launch {
                controller.findMax()
            }
        }
        _InputButton(
            label = "Successor",
            icon = Icons.Outlined.FindReplace,
            title = "Enter the sub tree root",
            onSingleAdded = { value ->
                scope.launch {
                    controller.findSuccessor(value)
                }
            }
        )

        _InputButton(
            label = "Predecessor",
            icon = Icons.Outlined.FindReplace,
            title = "Enter the sub tree root",
            onSingleAdded = { value ->
                scope.launch {
                    controller.findPredecessor(value)
                }
            }
        )

    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Items(modifier: Modifier = Modifier, item: List<String>, highLight: String? = null) {
    FlowRow(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        item.forEach { value ->
            val background =
                if (value == highLight) ThemeInfo.tagetItemColor else ThemeInfo.normalItemColor
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(
                    color = background,
                    shape = RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp,
                        topStart = 2.dp,
                        topEnd = 2.dp
                    )
                )
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = value,
                    color = background.contentColor(),
                    fontSize = 20.sp
                )
            }
        }

    }
}


@Composable
fun Button(modifier: Modifier = Modifier, label: String, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(label)
    }
}

@Composable
internal fun _InputButton(
    modifier: Modifier = Modifier,
    label: String,
    title: String = "Enter a number",
    initialValue: String = "",
    icon: ImageVector,
    onSingleAdded: (Int) -> Unit = {},
    leadingIcon: ImageVector = Icons.Outlined.Search,
    onMultipleAdded: ((List<Int>) -> Unit)? = null
) {
    var showDialog by remember { mutableStateOf(false) }
    CustomButton(
        modifier = modifier,
        label = label,
        icon = icon,
        onClick = {
            showDialog = true
        }
    )
    if (showDialog) {
        _InputDialog(
            initialValue = initialValue,
            title = title,
            leadingIcon = leadingIcon,
            onAdded = onSingleAdded,
            onMultipleAdded = onMultipleAdded,
            onDismiss = {
                showDialog = false
            })
    }
}

@Composable
internal fun _InputDialog(
    title: String,
    initialValue: String = "",
    onAdded: (Int) -> Unit,
    onMultipleAdded: ((List<Int>) -> Unit)? = null,
    leadingIcon: ImageVector = Icons.Outlined.Search,
    onDismiss: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf(initialValue) }


    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = {
            Column {
                CustomTextField(
                    label = title,
                    value = text,
                    onValueChange = { text = it },
                    keyboardType = KeyboardType.Text,
                    leadingIcon = leadingIcon
                )

            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val input = text.trim()
                    //Catch for typecast
                    val interestedForSingle = onMultipleAdded == null
                    try {
                        if (interestedForSingle) onAdded(input.toInt())
                        else {
                            val numbers = input.split(Regex("[,\\s]+"))
                                .mapNotNull { it.toIntOrNull() }
                            if (numbers.size == 1) {
                                onAdded(numbers.first())

                            }
                            if (numbers.size > 1 && onMultipleAdded != null) {
                                onMultipleAdded(numbers)
                            }
                        }
                        onDismiss()
                    } catch (_: Exception) {
                    }
                }
            ) {
                Text("Add")

            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val iconBackground = ThemeInfo.operationActionColor.copy(alpha = 0.8f)
    val height = 40.dp
    val shape = RoundedCornerShape(size = 8.dp)
    Surface(
        shadowElevation = 8.dp,
        shape = shape,
        modifier = modifier.height(height).background(shape = shape, color = iconBackground)
    ) {
        Row(
            modifier = Modifier
                .background(shape = shape, color = iconBackground)
                .clickable { onClick() }
        ) {
            SpacerHorizontal(16)
            Text(
                text = label,
                color = iconBackground.contentColor(),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            SpacerHorizontal(8)
            Box(
                modifier = Modifier.height(height)
                    .width(height)
                    .background(color = iconBackground, shape = shape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconBackground.contentColor(),
                    modifier = Modifier
                )
            }
        }

    }

}

