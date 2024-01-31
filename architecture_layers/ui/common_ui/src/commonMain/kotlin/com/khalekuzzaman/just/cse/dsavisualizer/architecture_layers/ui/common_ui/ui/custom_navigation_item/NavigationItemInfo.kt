package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.custom_navigation_item

import androidx.compose.ui.graphics.vector.ImageVector


data class NavigationGroup(
    val name: String,
    val icon: ImageVector,
    val members: List<NavigationItemInfo>
)

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