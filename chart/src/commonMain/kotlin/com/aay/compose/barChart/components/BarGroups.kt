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
    spacingY: Dp,
    spacingX: Dp,
    xAxisData: List<String>,
    barWidthPx: Dp,
    barWidth: Float,
    xRegionWidth: Float,
    xRegionWidthWithoutSpacing: Float,
    spaceBetweenBars: Float,
) {

    val height = size.height.toDp().toPx()

    barsParameters.forEachIndexed { barIndex, bar ->

        bar.data.forEachIndexed { index, data ->
            // value of height
            val ratio = (((data.toFloat() - lowerValue) / (upperValue)) / 1.12.dp.toPx()).dp.toPx()
            val barLength = ratio * (height - (spacingY.toPx() / 9.dp.toPx()))

            // value of bar group space and the space between bars
            println("barWidthPx -------- $barWidthPx")
            println("spaceBetweenBars -------- $spaceBetweenBars")
            val barLengthInGroup = (barIndex * (barWidthPx + (barWidthPx / spaceBetweenBars)))
            val xAxisLength = barLengthInGroup + ((xRegionWidth.dp - (xRegionWidthWithoutSpacing.dp)) / xAxisData.size)
            val lengthWithRatio = xAxisLength + (index * xRegionWidthWithoutSpacing.dp)

            drawRect(
                brush = Brush.verticalGradient(listOf(bar.barColor, bar.barColor)),
                topLeft = Offset(
                    lengthWithRatio.toPx(),
                    (height - spacingY.toPx() - barLength)
                ),
                size = Size(
                    width = barWidth,
                    height = barLength
                ),
            )
        }
    }
}