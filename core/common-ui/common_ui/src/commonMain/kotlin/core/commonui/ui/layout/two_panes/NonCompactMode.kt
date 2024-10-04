package com.just.cse.digital_diary.two_zero_two_three.common_ui.layout.two_panes

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
internal fun NonCompactModeLayout(
    horizontalSpacing: Dp,
    pane1: @Composable () -> Unit,
    pane2: @Composable () -> Unit,
    pane1FillMaxWidth: Boolean,
    pane1MaxWithPortion:Float=0.5f,
    pane2AnimationState:Any?=false,
    showPane2: Boolean,
) {

    if (showPane2){
        TwoPanes(
            horizontalSpacing=horizontalSpacing,
            pane1=pane1,
            pane2=pane2,
            pane1MaxWithPortion=pane1MaxWithPortion,
            pane2AnimationState = pane2AnimationState

        )
    }
    else{
        SinglePane(
            pane1=pane1,
            shouldTakeFullWidth = pane1FillMaxWidth
        )
    }



}

@Composable
private fun TwoPanes(
    horizontalSpacing: Dp,
    pane1MaxWithPortion:Float=0.5f,
    pane2AnimationState:Any?=false,
    pane1: @Composable () -> Unit,
    pane2: @Composable () -> Unit,
) {

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val width=maxWidth
        Row {
            Box(Modifier.widthIn(max = width*pane1MaxWithPortion))
            {
                pane1()
            }
            Spacer(Modifier.width(horizontalSpacing))
            Box(Modifier.fillMaxWidth()) {
                AnimatedContent(
                    modifier = Modifier,
                    targetState = pane2AnimationState,
                    transitionSpec = {
                        slideIntoContainer(
                            animationSpec = tween(durationMillis = 300, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Down
                        ).togetherWith(
                            slideOutOfContainer(
                                animationSpec = tween(durationMillis = 300, easing = EaseIn),
                                towards = AnimatedContentTransitionScope.SlideDirection.Up
                            )
                        )
                    }
                ){
                    pane2()

                }

            }
        }

    }
}

@Composable
private fun SinglePane(
    pane1: @Composable () -> Unit,
    shouldTakeFullWidth: Boolean,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (shouldTakeFullWidth) {
            Box(Modifier.fillMaxWidth())
            {
                pane1()
            }
        } else {
            Box(Modifier.weight(1f))
            {
                pane1()
            }
            Spacer(Modifier.weight(1f))

        }
    }

}


