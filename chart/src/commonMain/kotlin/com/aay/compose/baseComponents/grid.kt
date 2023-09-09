package com.aay.compose.baseComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.utils.formatToThousandsMillionsBillions

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.grid(
    xAxisDataSize: Int,
    isShowGrid: Boolean,
    gridColor: Color,
    backgroundLineWidth: Float,
    showGridWithSpacer: Boolean,
    spacingY: Dp,
    yAxisRange: Int,
    specialChart: Boolean,
    upperValue: Float,
    textMeasurer: TextMeasurer,
    gridOrientation: GridOrientation,
    xRegionWidth: Dp,
) {
    if (specialChart) {
        return
    }

    val yTextLayoutResult = textMeasurer.measure(
        text = AnnotatedString(upperValue.formatToThousandsMillionsBillions()),
    ).size.width
    val textSpace = yTextLayoutResult - (yTextLayoutResult/4)

    if (isShowGrid) {
        when (gridOrientation) {
            GridOrientation.HORIZONTAL -> drawHorizontalGrid(
                spacingY = spacingY,
                yAxisRange = yAxisRange,
                gridColor = gridColor,
                backgroundLineWidth = backgroundLineWidth,
                showGridWithSpacer = showGridWithSpacer,
                yTextLayoutResult = textSpace
            )

            GridOrientation.VERTICAL -> drawVerticalGrid(
                xAxisDataSize = xAxisDataSize,
                xRegionWidth = xRegionWidth,
                gridColor = gridColor,
                backgroundLineWidth = backgroundLineWidth,
                showGridWithSpacer = showGridWithSpacer,
                yTextLayoutResult = textSpace
            )

            else -> {
                drawHorizontalGrid(
                    spacingY = spacingY,
                    yAxisRange = yAxisRange,
                    gridColor = gridColor,
                    xEndLength = 38.dp.toPx(),
                    backgroundLineWidth = backgroundLineWidth,
                    showGridWithSpacer = showGridWithSpacer,
                    yTextLayoutResult = textSpace
                )

                drawVerticalGrid(
                    xAxisDataSize = xAxisDataSize,
                    xRegionWidth = xRegionWidth,
                    gridColor = gridColor,
                    yEndLength = 9f.toDp(),
                    backgroundLineWidth = backgroundLineWidth,
                    showGridWithSpacer = showGridWithSpacer,
                    yTextLayoutResult = textSpace
                )
            }

        }
    }
}

private fun DrawScope.drawHorizontalGrid(
    spacingY: Dp,
    yAxisRange: Int,
    gridColor: Color,
    xEndLength: Float = 0f,
    backgroundLineWidth: Float,
    showGridWithSpacer: Boolean,
    yTextLayoutResult: Int,
) {

    val xAxisMaxValue = size.width
    val yAxisList = mutableListOf<Float>()

    val textSpace = yTextLayoutResult - (yTextLayoutResult/4)

    (0..yAxisRange).forEach { i ->
        yAxisList.add(
            size.height.toDp()
                .toPx() - (spacingY.toPx()) - i * (size.height.toDp() - spacingY).toPx() / yAxisRange
        )
        val yAlignmentValue = yAxisList[i] + 9.dp.toPx()

        drawGrid(
            gridColor = gridColor,
            xStart = (yTextLayoutResult * 1.5.toFloat().toDp()).toPx(),
            yStart = yAlignmentValue,
            xEnd = xAxisMaxValue - (textSpace/0.9.toFloat().toDp().toPx()),
            yEnd = yAlignmentValue,
            backgroundLineWidth = backgroundLineWidth,
            showGridWithSpacer = showGridWithSpacer
        )
    }

}


private fun DrawScope.drawVerticalGrid(
    xAxisDataSize: Int,
    xRegionWidth: Dp,
    gridColor: Color,
    backgroundLineWidth: Float,
    showGridWithSpacer: Boolean,
    yTextLayoutResult: Int,
    yEndLength: Dp = 10.5.toFloat().toDp()
) {
    (0..xAxisDataSize).forEach { i ->
        val xLength = (i * xRegionWidth)

        drawGrid(
            gridColor = gridColor,
            xStart = xLength.toPx() + (yTextLayoutResult * 1.5.toFloat().toDp()).toPx(),
            yStart = 10.dp.toPx(),
            xEnd = xLength.toPx() + (yTextLayoutResult * 1.5.toFloat().toDp()).toPx(),
            yEnd = size.height - (size.height.toDp() / yEndLength),
            backgroundLineWidth = backgroundLineWidth,
            showGridWithSpacer = showGridWithSpacer
        )
    }
}

private fun DrawScope.drawGrid(
    gridColor: Color,
    xStart: Float,
    yStart: Float,
    xEnd: Float,
    yEnd: Float,
    backgroundLineWidth: Float,
    showGridWithSpacer: Boolean
) {
    drawLine(
        color = gridColor,
        start = Offset(xStart, yStart),
        end = Offset(xEnd, yEnd),
        strokeWidth = backgroundLineWidth,
        pathEffect = PathEffect.dashPathEffect(
            if (showGridWithSpacer) floatArrayOf(16f, 16f)
            else floatArrayOf(1f, 1f),
            0f
        )
    )
}