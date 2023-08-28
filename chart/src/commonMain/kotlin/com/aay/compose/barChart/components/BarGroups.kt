package com.aay.compose.barChart.components

import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.aay.compose.barChart.model.BarParameters

internal fun DrawScope.drawBarGroups(
    barsParameters: List<BarParameters>,
    upperValue: Double,
    lowerValue: Double,
    barWidth: Dp,
    xRegionWidth: Dp,
    spaceBetweenBars: Dp,
    maxWidth: Dp,
    height: Dp,
) {

    barsParameters.forEachIndexed { barIndex, bar ->

        bar.data.forEachIndexed { index, data ->
            val ratio = (data.toFloat() ) / (upperValue)
            val barLength = (ratio * (height/ 2.toDp().toPx())) - lowerValue.dp

            val xAxisLength = (index * xRegionWidth)
            val lengthWithRatio = xAxisLength + (barIndex * (barWidth + spaceBetweenBars))

            drawRoundRect(
                brush = Brush.verticalGradient(listOf(bar.barColor, bar.barColor)),
                topLeft = Offset(
                    lengthWithRatio.coerceAtMost(maxWidth).toPx(),
                    (height.value - barLength.toPx())
                ),
                size = Size(
                    width = barWidth.toPx(),
                    height = barLength.toPx()
                ),
                cornerRadius = CornerRadius(50.dp.toPx(), 50.dp.toPx())
            )
        }
    }
}