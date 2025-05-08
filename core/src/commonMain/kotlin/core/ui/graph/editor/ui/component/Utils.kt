package core.ui.graph.editor.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Input
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Moving
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
 fun Instruction(
    modifier: Modifier = Modifier,
    onGraphTypeInputRequest: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
        //.verticalScroll(rememberScrollState()),
    ) {
        // Input Instructions Title
        Text(
            text = "Instructions",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Instruction Details
        Text(
            text = "Follow these steps to build the graph:",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))

        InstructionRow(
            icon = Icons.AutoMirrored.Filled.Input,
            text = "Select the Graph type first"
        )
        InstructionRow(
            icon = Icons.Filled.AddCircleOutline,
            text = "Add a node to the graph"
        )
        InstructionRow(
            icon = Icons.Filled.Moving,
            text = "Create an edge between nodes"
        )
        InstructionRow(
            icon = Icons.Filled.RemoveCircleOutline,
            text = "Remove a selected node or edge"
        )
        InstructionRow(
            icon = Icons.Filled.ArrowCircleRight,
            text = "Start Visualization"
        )


        Spacer(modifier = Modifier.height(24.dp))

        // Button to Select Graph Type
        Button(
            onClick = onGraphTypeInputRequest,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Input,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Choose Graph Type",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

// @formatter:off
fun getMaxXY(nodes: List<Offset>, starts: List<Offset>, ends: List<Offset>, controls: List<Offset>): Pair<Float, Float> {
    val allPoints = nodes + starts + ends + controls
    if(allPoints.isEmpty()) return  Pair(500f,500f)

    val maxX = allPoints.maxOf { it.x }
    val maxY = allPoints.maxOf { it.y }
    return Pair(maxX, maxY)
}

@Composable
private fun InstructionRow(
    icon: ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

/*
TODO: Top Bar Section -------------Top Bar Section -------  Top Bar Section
TODO: Top Bar Section -------------Top Bar Section -------  Top Bar Section
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GraphEditorToolBar(
    disableAll: Boolean,
    enabledRemoveNode: Boolean,
    onAddNodeRequest: () -> Unit,
    onAddEdgeRequest: () -> Unit,
    onSaveRequest: () -> Unit,
    onRemoveNodeRequest: () -> Unit,
    onClearSelectionRequest:()->Unit={},
    navigationIcon: @Composable () -> Unit,
) {

    TopAppBar(
        title = {},
        navigationIcon = navigationIcon,
        actions = {
            //for clear selection
            TopBarIconButton(
                enabled = !disableAll,
                icon = Icons.Filled.ClearAll,
                onClick = onClearSelectionRequest
            )
            // Add Node Button
            TopBarIconButton(
                enabled = !disableAll,
                icon = Icons.Filled.AddCircleOutline,
                onClick = onAddNodeRequest
            )

            // Add Edge Button
            TopBarIconButton(
                enabled = !disableAll,
                icon = Icons.Filled.Moving,
                onClick = onAddEdgeRequest
            )

            // Remove Node Button
            TopBarIconButton(
                enabled = !disableAll && enabledRemoveNode,
                icon = Icons.Filled.RemoveCircleOutline,
                onClick = onRemoveNodeRequest
            )

            //
            TopBarIconButton(
                enabled = !disableAll,
                icon = Icons.Filled.ArrowCircleRight,
                onClick = onSaveRequest
            )
        }
    )
}

@Composable
private fun TopBarIconButton(
    enabled: Boolean,
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (enabled) MaterialTheme.colorScheme.primary
            else Color.Gray
        )
    }
}

/**
 * - Size will be Square so taking the maxOf(Height,Width)
 * - Measuring the text without any style such as font size or color or other...,so
 * if you need to change the style then apply the style before calculating text size
 * - Node size will be the same as the text size so that text can fit into it
 */
@Suppress("FunctionName")
internal fun _calculateTextSizePx(label: String, measurer: TextMeasurer): Float {
    val measuredText = measurer.measure(label)
    val textHeightPx = measuredText.size.height * 1f
    val textWidthPx = measuredText.size.width * 1f
//Size will be Square so taking the maxOf(Height,Width)
    return maxOf(textHeightPx, textWidthPx)
}