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
    barWidth: Float,
    xRegionWidth: Float,
    spaceBetweenBars: Float,
    maxWidth: Dp,
    height: Dp,
) {

    barsParameters.forEachIndexed { barIndex, bar ->

        bar.data.forEachIndexed { index, data ->
            val ratio = (data.toFloat() ) / (upperValue - lowerValue)
            val barLength = (ratio * (height/ 2.toDp().toPx())) - lowerValue.dp

            val xAxisLength = (index * xRegionWidth).dp
            val lengthWithRatio = xAxisLength + (barIndex * (barWidth + spaceBetweenBars).dp)
            println(spaceBetweenBars)
            drawRoundRect(
                brush = Brush.verticalGradient(listOf(bar.barColor, bar.barColor)),
                topLeft = Offset(
                    lengthWithRatio.coerceAtMost(maxWidth).toPx(),
                    (height.value - barLength.toPx())
                ),
                size = Size(
                    width = barWidth,
                    height = barLength.toPx()
                ),
                cornerRadius = CornerRadius(50.dp.toPx(), 50.dp.toPx())
            )
        }
    }
}