package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.navigation.bottom_navigation

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.custom_navigation_item.NavigationItemInfo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    modifier: Modifier=Modifier,
    destinations: List<NavigationItemInfo>,
    selectedDestinationIndex:Int,
    onDestinationSelected:(Int) -> Unit,
) {
    NavigationBar(modifier = modifier) {
        destinations.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = selectedDestinationIndex == index,
                onClick = {
                    onDestinationSelected(index)
                },
                label = {
                    Text(text = destination.label)
                },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(
                        badge = {
                            if (destination.badge != null) {
                                Badge {
                                    Text(text = destination.badge.toString())
                                }
                            }
                        }
                    ) {
                        (if (index == selectedDestinationIndex) {
                            destination.focusedIcon
                        } else destination.unFocusedIcon).let {
                            Icon(
                                imageVector = it,
                                contentDescription = destination.label
                            )
                        }
                    }
                }
            )
        }
    }


}