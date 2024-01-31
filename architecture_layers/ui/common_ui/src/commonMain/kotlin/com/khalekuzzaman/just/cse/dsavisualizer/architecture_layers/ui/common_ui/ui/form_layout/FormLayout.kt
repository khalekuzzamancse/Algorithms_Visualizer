package com.khalekuzzaman.just.cse.dsavisualizer.architecture_layers.ui.common_ui.ui.form_layout


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import kotlin.math.max

@Composable
fun FormLayout(
    eachRow1stChildMaxWidth: Dp,
    verticalGap: Dp,
    horizontalGap: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier, content = content
    ) { measurables, constraints ->

        if(measurables.isEmpty())
            throw  IllegalArgumentException("total item can not be 0")
        if(measurables.size%2!=0)
            throw  IllegalArgumentException("total item can not be odd")

        val firstColumnChildMeasureAbles = measurables.filterIndexed { i, _ -> i % 2 == 0 }
        val secondColumnChildMeasureAbles = measurables.filterIndexed { i, _ -> i % 2 == 1 }
        val eachRow1stChildConstraint = Constraints(
            minWidth = 0,
            minHeight = 0,
            maxWidth = eachRow1stChildMaxWidth.toPx().toInt(),
            maxHeight = Constraints.Infinity
        )
        val firstColumnChildPlaceAbles = firstColumnChildMeasureAbles.map { measurable ->
            measurable.measure(eachRow1stChildConstraint)
        }
        val firstColumnWidth = firstColumnChildPlaceAbles.maxOf { it.width  }+horizontalGap.toPx().toInt()
        val secondColumnWidth = constraints.maxWidth - firstColumnWidth
        val eachRowSecondChildConstraint = Constraints(
            minWidth = 0,
            minHeight = 0,
            maxWidth = secondColumnWidth,
            maxHeight = Constraints.Infinity
        )
        val secondColumnChildPlaceAbles = secondColumnChildMeasureAbles.map { measurable ->
            measurable.measure(eachRowSecondChildConstraint)
        }
        val totalVerticalGap=verticalGap*firstColumnChildMeasureAbles.size
        val layoutHeight = max(firstColumnChildPlaceAbles.sumOf { it.height },
            secondColumnChildPlaceAbles.sumOf { it.height })+totalVerticalGap.toPx().toInt()
        val layoutWidth =  firstColumnChildPlaceAbles.maxBy { it.width }.width+
                secondColumnChildPlaceAbles.maxBy { it.width }.width+horizontalGap.toPx().toInt()

        layout(width = layoutWidth, height = layoutHeight) {
            var y = 0
            firstColumnChildPlaceAbles.forEachIndexed { i, label ->
                val textField = secondColumnChildPlaceAbles[i]
                //Height
                val rowHeight = max(label.height, textField.height)+verticalGap.toPx().toInt()
                val eachRow1stChildMoveDown = (rowHeight - label.height) / 2
                val eachRow2ndChildMoveDown = (rowHeight - textField.height) / 2
                label.placeRelative(0, y + eachRow1stChildMoveDown)
                textField.placeRelative(firstColumnWidth, y + eachRow2ndChildMoveDown)
                y += rowHeight
            }

        }
    }
}