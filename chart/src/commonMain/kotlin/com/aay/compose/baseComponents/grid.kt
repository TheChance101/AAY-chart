package com.aay.compose.baseComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun DrawScope.grid(
    xAxisDataSize: Int,
    isShowGrid: Boolean,
    gridColor: Color,
    backgroundLineWidth: Float,
    showGridWithSpacer: Boolean,
    spacingX: Dp,
    spacingY: Dp
) {
    val minX = spacingX.toPx()
    val xAxisMaxValue = size.width + xAxisDataSize

    val yAxisList = mutableListOf<Float>()

    if (isShowGrid) {
        (0..6).forEach { i ->
            yAxisList.add((size.height.toDp() - spacingY - (i * size.height).toDp() / 7).toPx())
            val yAlignmentValue = yAxisList[i] + 5.dp.toPx()

            val xEnd = (size.width-(spacingX.toPx())).coerceAtMost(
                xAxisMaxValue - spacingX.toPx().div(0.7.dp.toPx())
            ).coerceAtLeast(minX)

            drawLine(
                gridColor,
                start = Offset(spacingX.toPx()+30.dp.toPx() / 2, yAlignmentValue),
                end = Offset(xAxisMaxValue, yAlignmentValue),
                strokeWidth = backgroundLineWidth,
                pathEffect = PathEffect.dashPathEffect(
                    if (showGridWithSpacer)
                        floatArrayOf(16f, 16f)
                    else floatArrayOf(1f, 1f),
                    0f
                )
            )
        }
    }
}