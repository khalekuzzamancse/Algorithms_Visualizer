package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.navigation.modal_drawer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.custom_navigation_item.NavigationItemInfo
import com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.navigation.modal_drawer.sheet.Sheet

@Composable
fun ModalDrawerDecorator(
    drawerState: ModalDrawerState,
    visibilityDelay:Long = 70L,
    destinations: List<NavigationItemInfo>,
    selectedDesertionIndex: Int,
    onDestinationSelected: (Int) -> Unit = {},
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalDrawer(
        modifier = Modifier,
        drawerState = drawerState.drawerState.collectAsState().value,
        sheet = {
            Sheet(
                visibilityDelay = visibilityDelay,
                selectedDestinationIndex = selectedDesertionIndex,
                destinations = destinations,
                onDestinationSelected = { index ->
                    onDestinationSelected(index)
                    drawerState.closeDrawer()
                },
                header = header
            )
        },
        content=content
    )
}



@Composable
fun ModalDrawerDecorator(
    destinations: List<NavigationItemInfo>,
    visibilityDelay:Long = 70L,
    selectedDesertionIndex: Int,
    drawerState: DrawerState,
    onDestinationSelected: (Int) -> Unit = {},
    header:@Composable ()->Unit,
    content: @Composable () -> Unit,
) {

    ModalDrawer(
        modifier = Modifier,
        drawerState = drawerState,
        sheet = {
           AnimatedVisibility(
               visible =drawerState.currentValue==DrawerValue.Open,
           ){
               Sheet(
                   visibilityDelay=visibilityDelay,
                   selectedDestinationIndex = selectedDesertionIndex,
                   destinations = destinations,
                   onDestinationSelected = { index ->
                       onDestinationSelected(index)
                   },
                   header = header
               )
           }

        },
        content=content
    )
}

