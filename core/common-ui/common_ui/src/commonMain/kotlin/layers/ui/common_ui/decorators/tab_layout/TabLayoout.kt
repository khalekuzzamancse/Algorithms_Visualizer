package layers.ui.common_ui.decorators.tab_layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

/*
Putting into a single file because it will used as library so that you can copy-paste it direcly
without working about external files
 */

@Composable
fun TabLayoutDecorator(
    modifier: Modifier = Modifier,
    controller: TabDecoratorController,
    showTabs: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier) {
        AnimatedVisibility(showTabs) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                CustomScrollableTabs(
                    selectedTabIndex = controller.selected.collectAsState(TabDestination.Visualization).value.ordinal,
                    tabs = controller.tabs,
                    onClickTab = { select ->
                        controller.onDestinationSelected(TabDestination.entries[select])
                    }
                )
            }
        }
        if (showTabs)
            Spacer(Modifier.height(16.dp))
        content()
    }


}


@Composable
fun TabLayoutDecorator(
    modifier: Modifier = Modifier,
    controller: TabDecoratorController,
    topBar: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = topBar
    ) { paddingValues ->
        Column(modifier.padding(paddingValues)) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                CustomScrollableTabs(
                    selectedTabIndex = controller.selected.collectAsState(TabDestination.Visualization).value.ordinal,
                    tabs = controller.tabs,
                    onClickTab = { select ->
                        controller.onDestinationSelected(TabDestination.entries[select])
                    }
                )
            }
            content()
        }

    }


}


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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                _Tab(tab, index == selectedTabIndex)
                            }

                        }

                    }
                }
            }
        ) { measurable, constraints ->
            //This value comes from androidx.compose.material3 TabRow.kt
            val minTabWidth = 90.dp.roundToPx()

            //find out the width of the screen
            val availableWidth = constraints.maxWidth

            //calculate the sum of all the data elements, the result should be the tab's actual width
            val elements = measurable.subList(0, measurable.size - 1).map { measurable ->
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
            val tabRow = measurable.last().measure(constraints.copy(maxWidth = width))
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
private fun _Tab(tab: TabItem, isSelected: Boolean) {
    (if (isSelected) tab.focusedIcon else tab.unFocusedIcon)?.let {
        Icon(
            imageVector = it,
            contentDescription = null
        )
    }

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
    Spacer(Modifier.width(4.dp))//trailing space for horizontally


}

