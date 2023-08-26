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
    xAxisData: List<String>,
    barWidth: Float,
    xRegionWidth: Float,
    xRegionWidthWithoutSpacing: Float,
    spaceBetweenBars: Float,
    maxWidth: Dp,
    height: Dp,
    spacingY:Dp,
) {

    barsParameters.forEachIndexed { barIndex, bar ->
        val xAxisLength = barIndex * (xRegionWidth / xAxisData.size)

        bar.data.forEachIndexed { index, data ->
            val ratio = (data.toFloat() - lowerValue) / (upperValue-lowerValue)
            val barLength = ratio * (height- (spacingY*1.16.dp.toPx() ))

            val lengthWithRatio = xAxisLength.dp + (index * xRegionWidth.dp)
            drawRect(
                brush = Brush.verticalGradient(listOf(bar.barColor, bar.barColor)),
                topLeft = Offset(
                    lengthWithRatio.coerceAtMost(maxWidth).toPx(),
                    (height.value  - barLength.toPx())
                ),
                size = Size(
                    width = barWidth,
                    height = barLength.toPx()
                ),
            )
        }
    }
}