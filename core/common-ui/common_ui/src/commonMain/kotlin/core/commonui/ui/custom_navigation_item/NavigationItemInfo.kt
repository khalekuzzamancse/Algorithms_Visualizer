package core.commonui.ui.custom_navigation_item

import androidx.compose.ui.graphics.vector.ImageVector




//use key to uniquely identify
data class NavigationItemInfo(
    val label: String,
    val unFocusedIcon: ImageVector,
    val focusedIcon: ImageVector = unFocusedIcon,
    val route: String = label,
    val badge: String? = null,
)
data class NavigationItemInfo2<T>(
    val key:T,
    val label: String,
    val iconText: String,
    val route: String = label,
    val badge: String? = null,
)