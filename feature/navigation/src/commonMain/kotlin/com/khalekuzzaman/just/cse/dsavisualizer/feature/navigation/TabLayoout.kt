package com.khalekuzzaman.just.cse.dsavisualizer.feature.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LineAxis
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LineAxis
import androidx.compose.material.icons.twotone.Code
import androidx.compose.material.icons.twotone.Language
import androidx.compose.material.icons.twotone.LineAxis
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

@Composable
fun TabLayout() {

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf(
        TabItem(
            label = "Visualization",
            unFocusedIcon = Icons.Outlined.AutoGraph,
            focusedIcon = Icons.Filled.AutoGraph
        ),
        TabItem(
            label = "Theory",
            unFocusedIcon = Icons.Outlined.Description,
            focusedIcon = Icons.Filled.Description
        ),
        TabItem(
            label = "Complexity Analysis",
            unFocusedIcon = Icons.Outlined.LineAxis,
            focusedIcon = Icons.TwoTone.LineAxis
        ),
        TabItem(
            label = "Pseudocode",
            unFocusedIcon = Icons.Outlined.Language,
            focusedIcon = Icons.TwoTone.Language
        ),
        TabItem(
            label = "Implementation",
            unFocusedIcon = Icons.Outlined.Code,
            focusedIcon = Icons.TwoTone.Code
        )
    )

    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        CustomScrollableTabs(
            selectedTabIndex = selectedTab,
            tabs = tabs,
            onClickTab = {
                selectedTab = it
            }
        )
    }


}

/**
 * The compose tab has bug such as it can not be centered as wrap content width
 * also it is not easily customizable even you edit the source code .that is why
 * we are making this custom,under the hood we are using the compose tab layout
 */
data class TabItem(
    val label: String,
    val unFocusedIcon: ImageVector,
    val focusedIcon: ImageVector = unFocusedIcon,

    )

@Composable
fun CustomScrollableTabs(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopCenter,
    selectedTabIndex: Int,
    tabs: List<TabItem>,
    onClickTab: (Int) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        Layout(
            content = {
                //This part will not be displayed, just used for calculating the whole with of all tabs
                tabs.forEachIndexed { index, tab ->
                    Column {
                        //here wrapping to Column
                        _Tab(tab, index == selectedTabIndex)
                    }
                }

                //This part is the real scrollable tab layout
                ScrollableTabRow(
                    modifier = Modifier,
                    selectedTabIndex = selectedTabIndex,
                    edgePadding = 0.dp
                ) {
                    tabs.forEachIndexed { index, tab ->
                        //here wrapping to Tab
                            Tab(
                                selected = index == selectedTabIndex,
                                onClick = { onClickTab(index) }
                            ) {
                                _Tab(tab, index == selectedTabIndex)

                            }

                    }
                }
            }
        ) { measurables, constraints ->
            //This value comes from androidx.compose.material3 TabRow.kt
            val minTabWidth = 90.dp.roundToPx()

            //find out the width of the screen
            val availableWidth = constraints.maxWidth

            //calculate the sum of all the data elements, the result should be the tab's actual width
            val elements = measurables.subList(0, measurables.size - 1).map { measurable ->
                measurable.measure(constraints)
            }
            val elementWidth = elements.sumOf {
                if (it.width < minTabWidth)
                    minTabWidth
                else
                    it.width
            }

            //let the sum-width above to be the new constraints which is used to measure the tabRow
            val width = min(availableWidth, elementWidth)
            val tabRow = measurables.last().measure(constraints.copy(maxWidth = width))
            val height = tabRow.height

            //report the width and height of this layout. It should only contains the tabs, all data
            //elements shouldn't been taken into account
            layout(width, height) {
                tabRow.placeRelative(0, 0)
            }
        }
    }
}


@Composable
private fun _Tab(tab:TabItem, isSelected:Boolean) {
        Icon(
            imageVector = if (isSelected) tab.focusedIcon else tab.unFocusedIcon,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 14.dp
            ),
            text = tab.label,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center
        )



}

