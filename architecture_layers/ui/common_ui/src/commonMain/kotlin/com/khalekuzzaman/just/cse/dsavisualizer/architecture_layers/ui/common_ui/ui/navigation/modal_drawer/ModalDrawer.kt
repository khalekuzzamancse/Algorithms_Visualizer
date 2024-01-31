package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.navigation.modal_drawer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


/*
Passing clicked group,
passing clicked item.
do don't need to pass group item group index when passing clicked item index
because parent already know which group is selected/clicked.
But what if the case,if all group item are shown at once,in that case we need to pass
group index and item index when passing which item is clicked
 */
data class NavGroupSelectedItem(
    val groupIndex: Int = -1,
    val itemIndex: Int = -1
)





@Composable
fun ModalDrawer(
    modifier: Modifier,
    drawerState: DrawerState,
    sheet: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    AnimateVisibilityDecorator{
        ModalNavigationDrawer(
            gesturesEnabled = true,
            modifier = modifier,
            drawerState = drawerState,
            drawerContent = sheet,
            content = content,
        )
    }

}
@Composable
fun AnimateVisibilityDecorator(
    content: @Composable () -> Unit
) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    val density = LocalDensity.current
    AnimatedVisibility(
        visibleState = state,
        enter = slideInHorizontally {
            with(density) { -400.dp.roundToPx() }
        },
        exit = slideOutHorizontally(),
    ) {
        content()
    }

}