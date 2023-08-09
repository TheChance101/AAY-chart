package com.aay.compose.lineChart2.components


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Dp
import com.aay.compose.lineChart2.model.BackGroundGrid

@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.chartContainer(
    xAxisData: List<T>,
    spacing: Dp,
    textMeasure: TextMeasurer,
    upperValue: Float,
    lowerValue: Float,
    isShowBackgroundLines: BackGroundGrid,
    backGroundLineColor: Color,
    backgroundLineWidth: Float,
    pathEffect: PathEffect,
) {

    xAxisDrawing(xAxisData, spacing, textMeasure)
    yAxisDrawing(upperValue, lowerValue, textMeasure,spacing)
    backgroundLine(
        xAxisDataSize = xAxisData.size,
        isShowBackgroundLines = isShowBackgroundLines,
        spacing = spacing,
        backGroundColor = backGroundLineColor,
        backgroundLineWidth = backgroundLineWidth,
        pathEffect = pathEffect
    )
}