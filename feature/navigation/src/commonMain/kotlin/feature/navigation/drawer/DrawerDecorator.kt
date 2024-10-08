@file:Suppress("unused")

package feature.navigation.drawer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/*
 * Used to loose coupling,so that directly this file can be copy -paste without the nav-rail dependency
Copy-paste and edit this file directly according to requirement.
since this is not a library,it is copy-pasting-editing,so to keep the client code simpler and easy to use
define the necessary thing here
 */


/*
 * Manage the own navRail so that does not need to copy-paste or implement the nav-rail file separately
 * Maintain can calculating the window size manually so that does not copy-paste or implement the window decorator  file separately
 */
/**
 * * Decorate the bottom bar
 * * It manage it own navRail version so that
 * * Manage it own Scaffold,since scaffold is sub compose layout so making it parent
 * as scrabble without defining it size can causes crash.
 * * But the  can be scrollable without any effect
 * * If you used it inside another sub compose layout such as Scaffold or Lazy List then
 * and make the parent scrollable then it can causes crash,so use modifier to define it size in that case

 *
 */


//TODO("Defining state")
//TODO("Defining state")

/**
 * is short and clean enough
 * * Needed to refactor for coroutine scope
 */
class NavigationDrawerController {
    private val _selected = MutableStateFlow<Destination?>(null)
    val selected = _selected.asStateFlow()
    private val _drawerState = MutableStateFlow(DrawerState(DrawerValue.Closed))
    internal val drawerState = _drawerState.asStateFlow()

    fun select(destination: Destination) {
        _selected.update { destination }
        closeDrawer()
    }

    private fun closeDrawer() {
        _drawerState.update { DrawerState(DrawerValue.Closed) }
    }

    fun openDrawer() {
        _drawerState.update { DrawerState(DrawerValue.Open) }
        //do not create new instance of DrawerState
        //use drawerState.open, fix the coroutine issue first,then do it
    }
}

@Composable
fun DrawerToNavRailDecorator(
    modifier: Modifier = Modifier,
    controller: NavigationDrawerController,
    groups: List<NavGroup>,
    itemVisibilityDelay: Long? = null,
    onEvent: (NavigationEvent) -> Unit,
    topAppbar: @Composable () -> Unit = {},
    header: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    DrawerToNavRailDecorator(
        modifier = modifier,
        groups = groups,
        selected = controller.selected.collectAsState().value,
        drawerState = controller.drawerState.collectAsState().value,
        itemVisibilityDelay = itemVisibilityDelay,
        topAppbar = topAppbar,
        onEvent = onEvent,
        header = header,
        content = content
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun DrawerToNavRailDecorator(
    modifier: Modifier = Modifier,
    groups: List<NavGroup>,
    selected: Destination?,
    itemVisibilityDelay: Long?,
    drawerState: DrawerState,
    topAppbar: @Composable () -> Unit = {},
    onEvent: (NavigationEvent) -> Unit,
    header: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val windowSize = calculateWindowSizeClass().widthSizeClass
    val compact = WindowWidthSizeClass.Compact
    val medium = WindowWidthSizeClass.Medium
    val expanded = WindowWidthSizeClass.Expanded

    AnimatedContent(windowSize) { window ->
        when (window) {
            compact -> {
                onEvent(NavigationEvent.DrawerNavigationMode)
                _ModalDrawerDecorator(
                    groups = groups,
                    selected = selected,
                    itemVisibilityDelay = itemVisibilityDelay,
                    header = header,
                    onEvent = onEvent,
                    content = content,
                    drawerState = drawerState
                )
            }

            medium, expanded -> {
                onEvent(NavigationEvent.NavRailNavigationMode)
                NavRailLayout(
                    modifier = modifier,
                    groups = groups,
                    selected = selected,
                    itemVisibilityDelay = itemVisibilityDelay,
                    topAppbar = topAppbar,
                    onEvent = onEvent,
                    content = content,
                    header = header
                )
            }

        }
    }

}


/**
 * @param groups is not state because it does not change over time,that is why taking as
 * separate param
 */
@Composable
private fun _ModalDrawerDecorator(
    drawerState: DrawerState,
    selected: Destination? = null,
    itemVisibilityDelay: Long? = null,//if null then has no animation
    groups: List<NavGroup>,
    onEvent: (NavigationEvent) -> Unit,
    header: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val hasAnimation = itemVisibilityDelay != null
    if (hasAnimation) {
        _AnimateAbleDrawer(
            groups = groups,
            selected = selected,
            itemVisibilityDelay = itemVisibilityDelay,
            drawerState = drawerState,
            header = header,
            content = content,
            onEvent = onEvent
        )
    } else {
        _AnimationLessDrawer(
            groups = groups,
            selected = selected,
            itemVisibilityDelay = itemVisibilityDelay,
            drawerState = drawerState,
            header = header,
            content = content,
            onEvent = onEvent
        )
    }
}

/**
 * This will reduce the complexity for selection and navigate when we have the group
 */
interface Destination {
    val route: String

    data object None : Destination {
        override val route: String
            get() = "None"
    }
}

class NavigationItem(
    val label: String,
    val focusedIcon: ImageVector,
    val unFocusedIcon: ImageVector = focusedIcon,
    val destination: Destination = Destination.None,
)

@Immutable
data class NavGroup(
    val label: String? = null,
    val items: List<NavigationItem>
)

// TODO(Drawer  section  -- Drawer  section -- Drawer  section -Drawer  section)
// TODO(Drawer  section  -- Drawer  section -- Drawer  section -Drawer  section)
// TODO(Drawer  section  -- Drawer  section -- Drawer  section -Drawer  section)
// TODO(Drawer  section  -- Drawer  section -- Drawer  section -Drawer  section)


/**
 *  * has a distinct composable for drawer items without animation.
 *  * Handling both animated and non-animated scenarios within the same composable may inadvertently invoke the Animation API unnecessarily, leading to unwanted effects as the animation API executes on every frame.
 *  * It is crucial to exercise caution when dealing with animation APIs to avoid unintended calls or accidental object creation within the animation API, preventing unnecessary object creation.
 * */


@Composable
private fun _AnimateAbleDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    groups: List<NavGroup>,
    selected: Destination?,
    itemVisibilityDelay: Long?,
    onEvent: (NavigationEvent) -> Unit,
    header: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    _ModalDrawer(
        modifier = modifier,
        drawerState = drawerState,
        sheet = {
            AnimatedVisibility(
                visible = drawerState.currentValue == DrawerValue.Open,
            ) {
                ModalDrawerSheet(modifier = Modifier) {
                    _NavigationSheet(
                        groups = groups,
                        selected = selected,
                        header = header,
                        onEvent = onEvent,
                        itemVisibilityDelay = itemVisibilityDelay
                    )
                }
            }
        },
        content = content
    )

}


@Composable
private fun _AnimationLessDrawer(
    modifier: Modifier = Modifier,
    groups: List<NavGroup>,
    selected: Destination?,
    itemVisibilityDelay: Long?,
    drawerState: DrawerState,
    onEvent: (NavigationEvent) -> Unit,
    header: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    _ModalDrawer(
        modifier = modifier,
        drawerState = drawerState,
        sheet = {
            ModalDrawerSheet(modifier = Modifier) {
                _NavigationSheet(
                    groups = groups,
                    selected = selected,
                    itemVisibilityDelay = itemVisibilityDelay,
                    header = header,
                    onEvent = onEvent
                )
            }

        },
        content = content
    )

}

// TODO(Navigation SHEET section  --  Navigation SHEET section --  Navigation SHEET section  -Navigation SHEET section )
// TODO(Navigation SHEET section  --  Navigation SHEET section --  Navigation SHEET section  -Navigation SHEET section )
// TODO(Navigation SHEET section  --  Navigation SHEET section --  Navigation SHEET section  -Navigation SHEET section )
// TODO(Navigation SHEET section  --  Navigation SHEET section --  Navigation SHEET section  -Navigation SHEET section )

/**
 * * Event for the drawer sheet.
 * * Defining so that number of parameter  is reduced to sheet decorator
 * * Also it easy to add or remove new event easily,and propagate up to the client
 */
sealed interface NavigationEvent {
    data class Selected(val destination: Destination) : NavigationEvent
    data class Hovered(val destination: Destination) : NavigationEvent
    data object DrawerNavigationMode : NavigationEvent
    data object NavRailNavigationMode : NavigationEvent
}


@Composable
private fun _NavigationSheet(
    onEvent: (NavigationEvent) -> Unit,
    groups: List<NavGroup>,
    selected: Destination?,
    itemVisibilityDelay: Long?,
    header: (@Composable () -> Unit)? = null,
) {
    val lastIndex = groups.size - 1
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
    ) {
        if (header != null) {
            header()
        }
        groups.forEachIndexed { groupNo, group ->
            _Group(
                group = group,
                itemVisibilityDelay = itemVisibilityDelay,
                selected = selected,
                onEvent = onEvent,
            )
            if (groupNo != lastIndex) {
                HorizontalDivider()
            }

        }


    }
}


@Composable
private fun _Group(
    modifier: Modifier = Modifier,
    onEvent: (NavigationEvent) -> Unit,
    selected: Destination?,
    itemVisibilityDelay: Long?,
    group: NavGroup
) {
    group.items.forEach { item ->
        _NavItem(
            item = item,
            isSelected = item.destination == selected,
            visibilityDelay = itemVisibilityDelay,
            onClick = {
                onEvent(NavigationEvent.Selected(item.destination))
            }
        )
    }


}
// TODO("Drawer Item section  -- Drawer Item section -- Drawer Item section -Drawer Item section")
// TODO("Drawer Item section  -- Drawer Item section -- Drawer Item section -Drawer Item section")
// TODO("Drawer Item section  -- Drawer Item section -- Drawer Item section -Drawer Item section")
// TODO("Drawer Item section  -- Drawer Item section -- Drawer Item section -Drawer Item section")
/**
 * Non-default parameters: [item], [isSelected], [onClick]
 *  * has a distinct composable for drawer items without animation.
 *  * Handling both animated and non-animated scenarios within the same composable may inadvertently invoke the Animation API unnecessarily, leading to unwanted effects as the animation API executes on every frame.
 *  * It is crucial to exercise caution when dealing with animation APIs to avoid unintended calls or accidental object creation within the animation API, preventing unnecessary object creation.
 * */
@Composable
private fun _NavItem(
    item: NavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    visibilityDelay: Long? = null,
) {
    if (visibilityDelay == null) //no visibility==no animation
    {
        _NavItem(
            item = item,
            isSelected = isSelected,
            onClick = onClick
        )
    } else {
        _WithAnimation(
            visibilityDelay = visibilityDelay,
        ) {
            _NavItem(
                item = item,
                isSelected = isSelected,
                onClick = onClick
            )
        }
    }

}

@Composable
private fun _NavItem(
    item: NavigationItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    NavigationDrawerItem(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 4.dp, end = 4.dp),
        icon = {
            Icon(
                imageVector = if (isSelected) item.focusedIcon else item.unFocusedIcon,
                contentDescription = "Nav item"
            )
        },
        label = {
            Text(
                text = item.label
            )
        },
        selected = isSelected,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.secondary,
            selectedTextColor = MaterialTheme.colorScheme.onSecondary,
            selectedIconColor = MaterialTheme.colorScheme.onSecondary,
            unselectedIconColor = MaterialTheme.colorScheme.primary,
        )
    )

}

@Composable
private fun _WithAnimation(
    visibilityDelay: Long,
    item: @Composable () -> Unit,
) {

    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(visibilityDelay)
            visible = true
            break
        }
    }
    AnimatedVisibility(
        visible = visible
    ) {
        item()
    }
}


@Composable
private fun _ModalDrawer(
    modifier: Modifier,
    drawerState: DrawerState,
    sheet: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        gesturesEnabled = true,
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = sheet,
        content = content,
    )


}

// TODO("NavRail Section -- NavRail Section -- NavRail Section -NavRail Section")
// TODO("NavRail Section -- NavRail Section -- NavRail Section -NavRail Section")
// TODO("NavRail Section -- NavRail Section -- NavRail Section -NavRail Section")
// TODO("NavRail Section -- NavRail Section -- NavRail Section -NavRail Section")


/*
 * Used to loose coupling,so that directly this file can be copy -paste without the nav-rail dependency
 */
/**
 * * It does only the information that need to NavigationItem
 * * It does not hold any extra information
 * Storing the destination here,to reduce the client reprehensibly to figure out
 * which destination is clicked
 */

@Composable
private fun NavRailLayout(
    modifier: Modifier = Modifier,
    groups: List<NavGroup>,
    selected: Destination?,
    itemVisibilityDelay: Long?,
    onEvent: (NavigationEvent) -> Unit,
    header: @Composable () -> Unit,
    topAppbar: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Row(modifier = modifier) {
        _NavRailSheet(
            modifier = modifier,
            groups = groups,
            selected = selected,
            itemVisibilityDelay = itemVisibilityDelay,
            onEvent = onEvent,
            header = header,
        )
        Scaffold(
            modifier = Modifier,
            topBar = topAppbar,
        ) { scaffoldPadding ->
            Box(Modifier.padding(scaffoldPadding)) { content() }//takes the remaining space,after the NavRail takes place
        }

    }


}

/**
 * Used to loose coupling,so that directly this file can be copy -paste without the nav-rail dependency
 */
// TODO("NavRail SHEET Section -- NavRail SHEET Section -- NavRail SHEET Section -NavRail SHEET Section")
// TODO("NavRail SHEET Section -- NavRail SHEET Section -- NavRail SHEET Section -NavRail SHEET Section")
// TODO("NavRail SHEET Section -- NavRail SHEET Section -- NavRail SHEET Section -NavRail SHEET Section")
// TODO("NavRail SHEET Section -- NavRail SHEET Section -- NavRail SHEET Section -NavRail SHEET Section")
@Composable
private fun _NavRailSheet(
    modifier: Modifier = Modifier,
    groups: List<NavGroup>,
    selected: Destination?,
    itemVisibilityDelay: Long?,
    onEvent: (NavigationEvent) -> Unit,
    header: @Composable () -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Surface(
            modifier = Modifier.fillMaxHeight(),
            tonalElevation = 3.dp //same as bottom bar elevation
        ) {
            _NavigationSheet(
                onEvent = onEvent,
                groups = groups,
                selected = selected,
                itemVisibilityDelay = itemVisibilityDelay,
                header = header,
            )
        }
    }
}
