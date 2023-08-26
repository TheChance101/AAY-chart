package com.aay.compose.barChart

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.barChart.components.BarChartContent
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.baseComponents.ChartDescription
import com.aay.compose.utils.ChartDefaultValues

@Composable
fun BarChart(
    chartParameters: List<BarParameters> = ChartDefaultValues.barParameters,
    gridColor: Color = ChartDefaultValues.gridColor,
    xAxisData: List<String> = emptyList(),
    isShowGrid: Boolean = ChartDefaultValues.IS_SHOW_GRID,
    barWidthPx: Dp = ChartDefaultValues.backgroundLineWidth,
    animateChart: Boolean = ChartDefaultValues.ANIMATED_CHART,
    showGridWithSpacer: Boolean = ChartDefaultValues.SHOW_BACKGROUND_WITH_SPACER,
    descriptionStyle: TextStyle = ChartDefaultValues.descriptionDefaultStyle,
    yAxisStyle: TextStyle = ChartDefaultValues.axesStyle,
    xAxisStyle: TextStyle = ChartDefaultValues.axesStyle,
    chartRatio: Float = ChartDefaultValues.chartRatio,
    horizontalArrangement: Arrangement.Horizontal = ChartDefaultValues.headerArrangement,
    backgroundLineWidth: Float = ChartDefaultValues.backgroundLineWidth.value,
    yAxisRange: Int = ChartDefaultValues.yAxisRange,
    showXAxis: Boolean = ChartDefaultValues.showXAxis,
    showYAxis: Boolean = ChartDefaultValues.showyAxis,
    gridOrientation: Orientation = ChartDefaultValues.gridOrientation,
    boxSize:Dp
) {

    Box(Modifier.wrapContentHeight()) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LazyRow(
                horizontalArrangement = horizontalArrangement,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {

                items(chartParameters) { details ->
                    ChartDescription(
                        chartColor = details.barColor,
                        chartName = details.dataName,
                        descriptionStyle = descriptionStyle,
                    )
                }
            }

            BarChartContent(
                barsParameters = chartParameters,
                gridColor = gridColor,
                xAxisData = xAxisData,
                isShowGrid = isShowGrid,
                barWidthPx = barWidthPx,
                animateChart = animateChart,
                showGridWithSpacer = showGridWithSpacer,
                yAxisStyle = yAxisStyle,
                xAxisStyle = xAxisStyle,
                backgroundLineWidth = backgroundLineWidth,
                yAxisRange = yAxisRange,
                showXAxis = showXAxis,
                showYAxis = showYAxis,
                gridOrientation = gridOrientation,
                boxSize =boxSize,
            )
        }
    }
}