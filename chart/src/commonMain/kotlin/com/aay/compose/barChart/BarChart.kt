package com.aay.compose.barChart

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.barChart.model.BarParameters

@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    linesParameters: List<BarParameters> = BarChartDefault.barParameters,
    gridColor: Color = BarChartDefault.gridColor,
    xAxisData: List<String> = emptyList(),
    isShowGrid: Boolean = BarChartDefault.IS_SHOW_GRID,
    barWidthPx: Dp = BarChartDefault.backgroundLineWidth,
    animateChart: Boolean = BarChartDefault.ANIMATED_CHART,
    showGridWithSpacer: Boolean = BarChartDefault.SHOW_BACKGROUND_WITH_SPACER,
    descriptionStyle: TextStyle = BarChartDefault.descriptionDefaultStyle,
    yAxisStyle: TextStyle = BarChartDefault.axesStyle,
    xAxisStyle: TextStyle = BarChartDefault.axesStyle,
    chartRatio: Float = BarChartDefault.chartRatio
) {

    Box(modifier.wrapContentHeight()) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ChartDescription(
                chartLineDetails = linesParameters,
                descriptionStyle = descriptionStyle,
            )

            BarChartContent(
                modifier = if (chartRatio == 0f) Modifier.wrapContentSize()
                else Modifier.aspectRatio(chartRatio)
                    .fillMaxSize(),
                linesParameters = linesParameters,
                gridColor = gridColor,
                xAxisData = xAxisData,
                isShowGrid = isShowGrid,
                barWidthPx = barWidthPx,
                animateChart = animateChart,
                showGridWithSpacer = showGridWithSpacer,
                yAxisStyle = yAxisStyle,
                xAxisStyle = xAxisStyle
            )
        }
    }
}