package com.just.cse.digital_diary.two_zero_two_three.common_ui.layout.two_panes

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

data class CompactModeTopPaneAnimation(
    val enter: EnterTransition = slideIn(
        animationSpec = tween(durationMillis = 300, easing = EaseIn),
        initialOffset = { IntOffset(it.width, 0) }
    ),
    val exit: ExitTransition =  slideOut(
        animationSpec = tween(durationMillis = 300, easing = EaseIn),
        targetOffset ={ IntOffset(0, 0) }
    )
)

data class TwoPaneProps(
    val horizontalSpace: Dp = 12.dp,
    val pane1FillMaxWidth: Boolean = false,
    val pane1MaxWidthPortion: Float = 0.5f,
    val topPaneAnimation: CompactModeTopPaneAnimation = CompactModeTopPaneAnimation()
)