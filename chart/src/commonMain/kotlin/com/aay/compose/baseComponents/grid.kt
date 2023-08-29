package com.aay.compose.baseComponents

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.utils.formatToThousandsMillionsBillions

@OptIn(ExperimentalTextApi::class)
fun DrawScope.grid(
    xAxisDataSize: Int,
    isShowGrid: Boolean,
    gridColor: Color,
    backgroundLineWidth: Float,
    showGridWithSpacer: Boolean,
    spacingY: Dp,
    spacingX: Dp,
    yAxisRange: Int,
    specialChart: Boolean,
    upperValue: Float,
    textMeasurer: TextMeasurer,
    isFromBarChart: Boolean,
    orientation: Orientation
) {
    if (specialChart) {
        return
    }
    val yTextLayoutResult = textMeasurer.measure(
        text = AnnotatedString(upperValue.formatToThousandsMillionsBillions()),
    ).size.width

    val xAxisMaxValue = size.width


    val yAxisList = mutableListOf<Float>()

    if (isShowGrid) {

        if (orientation == Orientation.Horizontal) {

            (0..yAxisRange).forEach { i ->
                yAxisList.add(
                    size.height.toDp()
                        .toPx() - (spacingY.toPx()) - i * (size.height.toDp() - spacingY).toPx() / yAxisRange
                )
                val yAlignmentValue = yAxisList[i] + 9.dp.toPx()


                drawLine(
                    gridColor,
                    start = Offset(yTextLayoutResult.toDp().toPx() + 32.dp.toPx(), yAlignmentValue),
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
        } else {

            val maxValue = size.height - 40.dp.toPx()
            (0..xAxisDataSize).forEach { i ->

                val startSpace = (spacingX) + (yTextLayoutResult).dp
                val spaceBetweenXes = (size.width - startSpace.toPx()) / (xAxisDataSize - 1)

                val xLength = (yTextLayoutResult.toDp()) + (i * spaceBetweenXes).toDp()


                drawLine(
                    gridColor,
                    start = Offset(xLength.toPx() + (yTextLayoutResult / 2) + 32.dp.toPx(), 10.toDp().toPx()),
                    end = Offset(xLength.toPx() + (yTextLayoutResult / 2) + 32.dp.toPx(), maxValue),
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