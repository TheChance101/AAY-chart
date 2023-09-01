package com.aay.compose.lineChart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.baseComponents.ChartDescription
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.utils.ChartDefaultValues
/**
 * Composable function to render a line chart with optional legends.
 *
 * @param modifier Modifier for configuring the layout and appearance of the line chart.
 * @param linesParameters List of LineParameters describing the data and style for each line in the chart.
 * @param gridColor Color of the grid lines (default is Gray).
 * @param xAxisData List of labels for the X-axis.
 * @param isGrid Flag to determine whether to display grid lines (default is true).
 * @param barWidthPx Width of the grid lines (default is 1.0).
 * @param animateChart Flag to enable chart animations (default is true).
 * @param showGridWithSpacer Flag to add background spacing when showing grid lines (default is true).
 * @param descriptionStyle TextStyle for configuring the appearance of chart description (legend) text.
 * @param yAxisStyle TextStyle for configuring the appearance of the Y-axis labels.
 * @param xAxisStyle TextStyle for configuring the appearance of the X-axis labels.
 * @param chartRatio Aspect ratio of the chart (default is 0 for automatic sizing).
 * @param horizontalArrangement Horizontal arrangement for legend items (default is [Arrangement.Center]).
 * @param yAxisRange Range of values for the Y-axis (default is 0 to 100).
 * @param showXAxis Flag to determine whether to display the X-axis (default is true).
 * @param showYAxis Flag to determine whether to display the Y-axis (default is true).
 * @param oneLineChart Flag to specify if the chart should display only one line (default is false).
 * @param gridOrientation Orientation of grid lines (default is [GridOrientation.HORIZONTAL]).
 * @param legendPosition Position of the legend within the chart (default is [LegendPosition.TOP]).
 *
 * @see LineParameters
 * @see LegendPosition
 * @see GridOrientation
 */
@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    linesParameters: List<LineParameters> = ChartDefaultValues.lineParameters,
    gridColor: Color = ChartDefaultValues.gridColor,
    xAxisData: List<String> = emptyList(),
    isGrid: Boolean = ChartDefaultValues.IS_SHOW_GRID,
    barWidthPx: Dp = ChartDefaultValues.backgroundLineWidth,
    animateChart: Boolean = ChartDefaultValues.ANIMATED_CHART,
    showGridWithSpacer: Boolean = ChartDefaultValues.SHOW_BACKGROUND_WITH_SPACER,
    descriptionStyle: TextStyle = ChartDefaultValues.descriptionDefaultStyle,
    yAxisStyle: TextStyle = ChartDefaultValues.axesStyle,
    xAxisStyle: TextStyle = ChartDefaultValues.axesStyle,
    chartRatio: Float = ChartDefaultValues.chartRatio,
    horizontalArrangement: Arrangement.Horizontal = ChartDefaultValues.headerArrangement,
    yAxisRange: Int = ChartDefaultValues.yAxisRange,
    showXAxis: Boolean = ChartDefaultValues.showXAxis,
    showYAxis: Boolean = ChartDefaultValues.showyAxis,
    oneLineChart: Boolean = ChartDefaultValues.specialChart,
    gridOrientation: GridOrientation = ChartDefaultValues.gridOrientation,
    legendPosition: LegendPosition = ChartDefaultValues.legendPosition
) {
    val clickedPoints = remember { mutableStateListOf<Pair<Float, Float>>() }

    Box(modifier.wrapContentHeight()) {
        Column() {
            when(legendPosition){
                LegendPosition.TOP -> {
                    LazyRow(
                        horizontalArrangement = horizontalArrangement,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        items(linesParameters) { details ->
                            ChartDescription(
                                chartColor = details.lineColor,
                                chartName = details.label,
                                descriptionStyle = descriptionStyle,
                            )
                        }
                    }

                    ChartContent(
                        modifier = if (chartRatio == 0f) Modifier.padding(top = 16.dp).wrapContentSize()
                        else Modifier.padding(top = 16.dp).aspectRatio(chartRatio)
                            .fillMaxSize(),
                        linesParameters = linesParameters,
                        gridColor = gridColor,
                        xAxisData = xAxisData,
                        isShowGrid = isGrid,
                        barWidthPx = barWidthPx,
                        animateChart = animateChart,
                        showGridWithSpacer = showGridWithSpacer,
                        yAxisStyle = yAxisStyle,
                        xAxisStyle = xAxisStyle,
                        yAxisRange = yAxisRange,
                        showXAxis = showXAxis,
                        showYAxis = showYAxis,
                        specialChart = oneLineChart,
                        onChartClick = { x, y ->
                            clickedPoints.add(x to y)
                        },
                        clickedPoints = clickedPoints,
                        gridOrientation = gridOrientation
                    )
                }
                LegendPosition.BOTTOM -> {

                    ChartContent(
                        modifier = if (chartRatio == 0f) Modifier.padding(top = 16.dp).wrapContentSize().weight(1f)
                        else Modifier.padding(top = 16.dp).aspectRatio(chartRatio)
                            .fillMaxSize().weight(1f),
                        linesParameters = linesParameters,
                        gridColor = gridColor,
                        xAxisData = xAxisData,
                        isShowGrid = isGrid,
                        barWidthPx = barWidthPx,
                        animateChart = animateChart,
                        showGridWithSpacer = showGridWithSpacer,
                        yAxisStyle = yAxisStyle,
                        xAxisStyle = xAxisStyle,
                        yAxisRange = yAxisRange,
                        showXAxis = showXAxis,
                        showYAxis = showYAxis,
                        specialChart = oneLineChart,
                        onChartClick = { x, y ->
                            clickedPoints.add(x to y)
                        },
                        clickedPoints = clickedPoints,
                        gridOrientation = gridOrientation
                    )
                    LazyRow(
                        horizontalArrangement = horizontalArrangement,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        items(linesParameters) { details ->
                            ChartDescription(
                                chartColor = details.lineColor,
                                chartName = details.label,
                                descriptionStyle = descriptionStyle,
                            )
                        }
                    }

                }
                LegendPosition.DISAPPEAR -> {
                    ChartContent(
                        modifier = if (chartRatio == 0f) Modifier.padding(top = 16.dp).wrapContentSize()
                        else Modifier.padding(top = 16.dp).aspectRatio(chartRatio)
                            .fillMaxSize(),
                        linesParameters = linesParameters,
                        gridColor = gridColor,
                        xAxisData = xAxisData,
                        isShowGrid = isGrid,
                        barWidthPx = barWidthPx,
                        animateChart = animateChart,
                        showGridWithSpacer = showGridWithSpacer,
                        yAxisStyle = yAxisStyle,
                        xAxisStyle = xAxisStyle,
                        yAxisRange = yAxisRange,
                        showXAxis = showXAxis,
                        showYAxis = showYAxis,
                        specialChart = oneLineChart,
                        onChartClick = { x, y ->
                            clickedPoints.add(x to y)
                        },
                        clickedPoints = clickedPoints,
                        gridOrientation = gridOrientation
                    )
                }
            }

        }
    }
}