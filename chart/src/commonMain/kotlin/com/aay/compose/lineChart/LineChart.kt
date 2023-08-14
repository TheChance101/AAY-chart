package com.aay.compose.lineChart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.baseComponents.ChartDescription
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.utils.ChartDefaultValues

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    linesParameters: List<LineParameters> = ChartDefaultValues.lineParameters,
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
    horizontalArrangement: Arrangement.Horizontal = ChartDefaultValues.headerArrangement
) {

    Box(modifier.wrapContentHeight()) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LazyRow(
                horizontalArrangement = horizontalArrangement,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {

                items(linesParameters) { details ->
                    ChartDescription(
                        chartColor = details.lineColor,
                        chartName = details.dataName,
                        descriptionStyle = descriptionStyle,
                    )
                }
            }
            ChartContent(
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