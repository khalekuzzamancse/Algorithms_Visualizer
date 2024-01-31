package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.custom_navigation_item.NavigationItem
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.custom_navigation_item.NavigationItemInfo
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.custom_navigation_item.NavigationItemInfo2

@OptIn(ExperimentalLayoutApi::class)
@Composable
 fun  BottomSheetNavigationSection(
    destinations: List<NavigationItemInfo>,
    currentDestinationIndex: Int,
    onItemClick: (Int) -> Unit,
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 4.dp)
    ) {
        FlowRow(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            destinations.forEachIndexed { index, _ ->
                NavigationItem(
                    navigationItem = destinations[index],
                    visibilityDelay = (index + 1) * 10L,
                    selected = currentDestinationIndex == index,
                    onClick = {
                        onItemClick(index)
                    }
                )
            }

        }
    }


}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> BottomSheetNavigationSection2(
    destinations: List<NavigationItemInfo2<T>>,
    currentDestinationIndex: Int,
    onItemClick: (Int) -> Unit,
) {
    Surface(
        modifier = Modifier.padding(8.dp),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 4.dp)
    ) {
        FlowRow(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            destinations.forEachIndexed { index, _ ->
                NavigationItem(
                    navigationItem = destinations[index],
                    visibilityDelay = (index + 1) * 10L,
                    selected = currentDestinationIndex == index,
                    onClick = {
                        onItemClick(index)
                    }
                )
            }

        }
    }


}