package core.commonui.decorators.tab_layout

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * The compose tab has bug such as it can not be centered as wrap content width
 * also it is not easily customizable even you edit the source code .that is why
 * we are making this custom,under the hood we are using the compose tab layout
 */
data class TabItem(
    val label: String,
    val unFocusedIcon: ImageVector? = null,
    val focusedIcon: ImageVector? = unFocusedIcon,

    )