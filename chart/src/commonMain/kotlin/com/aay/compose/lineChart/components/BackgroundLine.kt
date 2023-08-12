package com.aay.compose.lineChart.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun DrawScope.backgroundLine(
    xAxisDataSize: Int,
    isShowBackgroundLines: Boolean,
    backGroundColor: Color,
    backgroundLineWidth: Float,
    pathEffect: PathEffect,
    spacingX:Dp,
    spacingY: Dp
) {
    val minX = spacingX.toPx()
    val xAxisMaxValue = size.width + xAxisDataSize

    val yAxisList = mutableListOf<Float>()

    if (isShowBackgroundLines) {
        (0..6).forEach { i ->
            yAxisList.add((size.height.toDp() - spacingY - (i * size.height).toDp() / 7).toPx())
            val yAlignmentValue = yAxisList[i] + 5.dp.toPx()

            val xEnd = (size.width).coerceAtMost(
                xAxisMaxValue - spacingX.toPx().div(0.7.dp.toPx())
            ).coerceAtLeast(minX)

            drawLine(
                backGroundColor,
                start = Offset(spacingX.toPx()/2, yAlignmentValue),
                end = Offset(xEnd, yAlignmentValue),
                strokeWidth = backgroundLineWidth,
                pathEffect = pathEffect
            )
        }
    }
}