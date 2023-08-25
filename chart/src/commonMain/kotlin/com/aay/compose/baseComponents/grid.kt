package com.aay.compose.baseComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun DrawScope.grid(
    isShowGrid: Boolean,
    gridColor: Color,
    backgroundLineWidth: Float,
    showGridWithSpacer: Boolean,
    spacingX: Dp,
    spacingY: Dp,
    yAxisRange : Int,
    specialChart : Boolean
) {
    if (specialChart){
        return
    }
    val xAxisMaxValue = size.width - spacingX.toPx()

    val yAxisList = mutableListOf<Float>()

    if (isShowGrid) {
        (0..yAxisRange ).forEach { i ->
            yAxisList.add(
                size.height.toDp().toPx() - spacingY.toPx() - i * (size.height.toDp() - spacingY).toPx() / (yAxisRange)
            )
            val yAlignmentValue = yAxisList[i] + 5.dp.toPx()


            drawLine(
                gridColor,
                start = Offset(spacingX.toPx() + 30.dp.toPx() / 2, yAlignmentValue),
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