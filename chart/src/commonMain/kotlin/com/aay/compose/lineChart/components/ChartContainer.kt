package com.aay.compose.lineChart.components


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.chartContainer(
    xAxisData: List<T>,
    textMeasure: TextMeasurer,
    upperValue: Float,
    lowerValue: Float,
    isShowBackgroundLines: Boolean,
    backGroundLineColor: Color,
    backgroundLineWidth: Float,
    showGridWithSpacer: Boolean,
    spacingX: Dp,
    spacingY: Dp,
    yAxisStyle: TextStyle,
    xAxisStyle: TextStyle
) {
    xAxisDrawing(
        xAxisData = xAxisData,
        spacing = spacingX,
        textMeasure = textMeasure,
        xAxisStyle = xAxisStyle
    )

    yAxisDrawing(
        upperValue = upperValue,
        lowerValue = lowerValue,
        textMeasure = textMeasure,
        spacing = spacingY,
        yAxisStyle = yAxisStyle
    )

    backgroundLine(
        xAxisDataSize = xAxisData.size,
        isShowBackgroundLines = isShowBackgroundLines,
        backGroundColor = backGroundLineColor,
        backgroundLineWidth = backgroundLineWidth,
        showGridWithSpacer = showGridWithSpacer,
        spacingX = spacingX,
        spacingY = spacingY,
    )
}