package com.aay.compose.baseComponents

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

fun DrawScope.grid(
    xAxisDataSize: Int,
    isShowGrid: Boolean,
    gridColor: Color,
    backgroundLineWidth: Float,
    showGridWithSpacer: Boolean,
    spacingX: Dp,
    spacingY: Dp,
    yAxisRange: Int,
    specialChart: Boolean,
    gridOrientation: Orientation,
    isFromBarChart: Boolean,
) {
    if (specialChart){
        return
    }
    val xAxisMaxValue = size.width - spacingX.toPx()
    val xAxisMaxValue = if (gridOrientation == Orientation.Vertical)
        size.width + xAxisDataSize
    else
        size.height - spacingY.toPx()  + 10.dp.toPx()



    if (isShowGrid) {
        (0..yAxisRange ).forEach { i ->
            yAxisList.add(
                size.height.toDp().toPx() - spacingY.toPx() - i * (size.height.toDp() - spacingY).toPx() / (yAxisRange)
            )
            val yAlignmentValue = yAxisList[i] + 5.dp.toPx()
        if (gridOrientation == Orientation.Vertical) {
            val yAxisList = mutableListOf<Float>()

            (0..yAxisRange).forEach { i ->
                yAxisList.add(
                    size.height.toDp()
                        .toPx() - spacingY.toPx() - i * (size.height.toDp() - spacingY).toPx() / (yAxisRange)
                )
                val yAlignmentValue = yAxisList[i] + 5.dp.toPx()

                drawLine(
                    gridColor,
                    start = Offset(60.dp.toPx(), yAlignmentValue),
                    end = Offset(xAxisMaxValue, yAlignmentValue),
                    strokeWidth = backgroundLineWidth,
                    pathEffect = PathEffect.dashPathEffect(
                        if (showGridWithSpacer) floatArrayOf(16f, 16f)
                        else floatArrayOf(1f, 1f), 0f
                    )
                )
            }

        } else {
            val yAxisList = mutableListOf<Float>()

            (0 until xAxisDataSize).forEach { i ->
                yAxisList.add(
                    (spaceBetweenXes - 20.dp.toPx()) + (i * spaceBetweenXes)
                )

                val yAlignmentValue = yAxisList[i]

                drawLine(
                    gridColor,
                    start = Offset(yAlignmentValue, 0f),
                    end = Offset(yAlignmentValue, xAxisMaxValue),
                    strokeWidth = backgroundLineWidth,
                    pathEffect = PathEffect.dashPathEffect(
                        if (showGridWithSpacer) floatArrayOf(16f, 16f)
                        else floatArrayOf(1f, 1f),
                        0f
                    )
                )
            }
        }
    }
}