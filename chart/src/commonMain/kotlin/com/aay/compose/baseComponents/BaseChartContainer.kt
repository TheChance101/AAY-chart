package com.aay.compose.baseComponents


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.baseChartContainer(
    xAxisData: List<T>,
    textMeasure: TextMeasurer,
    upperValue: Float,
    lowerValue: Float,
    isShowGrid: Boolean,
    gridColor: Color,
    backgroundLineWidth: Float,
    showGridWithSpacer: Boolean,
    spacingX: Dp,
    spacingY: Dp,
    yAxisStyle: TextStyle,
    xAxisStyle: TextStyle,
    yAxisRange : Int,
    showXAxis : Boolean,
    showYAxis : Boolean,
    specialChart : Boolean = false
) {
  if(showXAxis) {
      xAxisDrawing(
          xAxisData = xAxisData,
          spacing = spacingX,
          textMeasure = textMeasure,
          xAxisStyle = xAxisStyle,
          specialChart = specialChart
      )
  }

    if (showYAxis) {
        yAxisDrawing(
            upperValue = upperValue,
            lowerValue = lowerValue,
            textMeasure = textMeasure,
            spacing = spacingY,
            yAxisStyle = yAxisStyle,
            yAxisRange = yAxisRange,
            specialChart = specialChart
        )
    }

    grid(
        xAxisDataSize = xAxisData.size,
        isShowGrid = isShowGrid,
        gridColor = gridColor,
        backgroundLineWidth = backgroundLineWidth,
        showGridWithSpacer = showGridWithSpacer,
        spacingX = spacingX,
        spacingY = spacingY,
        yAxisRange = yAxisRange ,
        specialChart = specialChart
    )
}