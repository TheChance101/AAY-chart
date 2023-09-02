package com.aay.compose.barChart.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.*
import com.aay.compose.barChart.model.BarParameters

internal fun DrawScope.drawBarGroups(
    barsParameters: List<BarParameters>,
    upperValue: Double,
    barWidth: Dp,
    xRegionWidth: Dp,
    spaceBetweenBars: Dp,
    maxWidth: Dp,
    height: Dp,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    barCornerRadius: Dp
) {

    barsParameters.forEachIndexed { barIndex, bar ->

        bar.data.forEachIndexed { index, data ->
            val ratio = (data.toFloat()) / (upperValue.toFloat())
            val barLength = ((height / 1.02.toFloat().dp).toDp() * animatedProgress.value) * ratio

            val xAxisLength = (index * xRegionWidth)
            val lengthWithRatio = xAxisLength + (barIndex * (barWidth + spaceBetweenBars))

            drawRoundRect(
                brush = Brush.verticalGradient(listOf(bar.barColor, bar.barColor)),
                topLeft = Offset(
                    lengthWithRatio.coerceAtMost(maxWidth).toPx(),
                    height.value - barLength.toPx()
                ),
                size = Size(
                    width = barWidth.toPx(),
                    height = barLength.toPx()
                ),
                cornerRadius = CornerRadius(barCornerRadius.toPx())
            )
        }
    }
}