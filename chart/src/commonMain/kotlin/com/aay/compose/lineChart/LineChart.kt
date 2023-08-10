package com.aay.compose.lineChart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.aay.compose.lineChart.model.BackGroundGrid
import com.aay.compose.lineChart.model.LineParameters

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    linesParameters: List<LineParameters> = LineChartDefault.lineParameters,
    backGroundColor: Color = LineChartDefault.backGroundColor,
    xAxisData: List<String> = LineChartDefault.xAxisData,
    showBackgroundGrid: BackGroundGrid = LineChartDefault.backGroundGrid,
    barWidthPx: Dp = LineChartDefault.backgroundLineWidth,
    animateChart: Boolean = LineChartDefault.ANIMATED_CHART,
    pathEffect: PathEffect = LineChartDefault.pathEffect,
    descriptionStyle: TextStyle = LineChartDefault.descriptionDefaultStyle
) {

    Column {

        ChartDescription(
            chartLineDetails = linesParameters,
            descriptionStyle = descriptionStyle
        )

       Box(modifier = Modifier.background(color = Color.Green.copy(0.5f))){
           ChartContent(
               modifier = modifier,
               linesParameters = linesParameters,
               backGroundColor = backGroundColor,
               xAxisData = xAxisData,
               showBackgroundGrid = showBackgroundGrid,
               barWidthPx = barWidthPx,
               animateChart = animateChart,
               pathEffect = pathEffect
           )
       }
    }
}