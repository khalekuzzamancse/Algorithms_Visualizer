package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.side_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.custom_navigation_item.NavigationItemInfo2

@Composable
fun <T> SideSheetNavigation(
    modifier: Modifier = Modifier,
    destinations: List<NavigationItemInfo2<T>>,
    destinationModifier: Modifier=Modifier,
    currentDestinationIndex: Int,
    onDestinationSelected: (Int) -> Unit,
    onDestinationFocused: (Int) -> Unit={},
) {

    Surface(
        modifier = modifier.padding(8.dp),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
//            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            destinations.forEachIndexed { index, _ ->
                NavigationItem(
                    modifier=destinationModifier.fillMaxWidth(),
                    navigationItem = destinations[index],
                    visibilityDelay = (index + 1) * 10L,
                    selected = currentDestinationIndex == index,
                    onClick = {
                        onDestinationSelected(index)
                    },
                    onFocusing = {
                        onDestinationFocused(index)
                    }
                )
            }

        }
    }


}
