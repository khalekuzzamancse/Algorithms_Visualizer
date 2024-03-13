package com.just.cse.digital_diary.two_zero_two_three.common_ui.layout.two_panes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun CompactModeLayout(
    showTopPane:Boolean,
    enter: EnterTransition = fadeIn() + expandIn(),
    exit: ExitTransition = shrinkOut() + fadeOut(),
    pane1: @Composable () -> Unit,
    topPane: @Composable () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        pane1()
        AnimatedVisibility(
            modifier = Modifier.matchParentSize(),
            enter = enter,
            exit = exit, //TODO: fix the animation transition later
            visible = showTopPane
        ){
            topPane()
        }
    }
}