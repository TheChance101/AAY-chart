package com.aay.compose.barChart

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
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.utils.ChartDefaultValues

/**
 * Composable function to render a bar chart with an optional legend.
 *
 * @param chartParameters List of BarParameters describing the data for the bar chart.
 * @param gridColor Color of the grid lines (default is Gray).
 * @param xAxisData List of labels for the X-axis.
 * @param isShowGrid Flag to determine whether to display grid lines (default is true).
 * @param animateChart Flag to enable chart animations (default is true).
 * @param showGridWithSpacer Flag to add background spacing when showing grid lines (default is true).
 * @param descriptionStyle TextStyle for configuring the appearance of chart description (legend) text.
 * @param yAxisStyle TextStyle for configuring the appearance of the Y-axis labels.
 * @param xAxisStyle TextStyle for configuring the appearance of the X-axis labels.
 * @param horizontalArrangement Horizontal arrangement for legend items (default is [Arrangement.Center]).
 * @param backgroundLineWidth Width of the background grid lines (default is 1.0).
 * @param yAxisRange Range of values for the Y-axis (default is 0 to 100).
 * @param showXAxis Flag to determine whether to display the X-axis (default is true).
 * @param showYAxis Flag to determine whether to display the Y-axis (default is true).
 * @param barWidth Width of the bars in the chart (default is automatic).
 * @param spaceBetweenBars Space between bars within the same group (default is automatic).
 * @param spaceBetweenGroups Space between groups of bars (default is automatic).
 * @param legendPosition Position of the legend within the chart (default is [LegendPosition.TOP]).
 * @param barCornerRadius radius of the bar corner in the chart (default is zero).
 *
 * @see BarParameters
 * @see LegendPosition
 */
@Composable
fun BarChart(
    chartParameters: List<BarParameters> = ChartDefaultValues.barParameters,
    gridColor: Color = ChartDefaultValues.gridColor,
    xAxisData: List<String> = emptyList(),
    isShowGrid: Boolean = ChartDefaultValues.IS_SHOW_GRID,
    animateChart: Boolean = ChartDefaultValues.ANIMATED_CHART,
    showGridWithSpacer: Boolean = ChartDefaultValues.SHOW_BACKGROUND_WITH_SPACER,
    descriptionStyle: TextStyle = ChartDefaultValues.descriptionDefaultStyle,
    yAxisStyle: TextStyle = ChartDefaultValues.axesStyle,
    xAxisStyle: TextStyle = ChartDefaultValues.axesStyle,
    horizontalArrangement: Arrangement.Horizontal = ChartDefaultValues.headerArrangement,
    backgroundLineWidth: Float = ChartDefaultValues.backgroundLineWidth.value,
    yAxisRange: Int = ChartDefaultValues.yAxisRange,
    showXAxis: Boolean = ChartDefaultValues.showXAxis,
    showYAxis: Boolean = ChartDefaultValues.showyAxis,
    barWidth: Dp = ChartDefaultValues.barWidth,
    spaceBetweenBars: Dp = ChartDefaultValues.spaceBetweenBars,
    spaceBetweenGroups: Dp = ChartDefaultValues.spaceBetweenGroups,
    legendPosition: LegendPosition = ChartDefaultValues.legendPosition,
    barCornerRadius: Dp = ChartDefaultValues.barCornerRadius
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        when (legendPosition) {
            LegendPosition.TOP -> {
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
                    animateChart = animateChart,
                    showGridWithSpacer = showGridWithSpacer,
                    yAxisStyle = yAxisStyle,
                    xAxisStyle = xAxisStyle,
                    backgroundLineWidth = backgroundLineWidth,
                    yAxisRange = yAxisRange,
                    showXAxis = showXAxis,
                    showYAxis = showYAxis,
                    barWidth = barWidth,
                    spaceBetweenBars = spaceBetweenBars,
                    spaceBetweenGroups = spaceBetweenGroups,
                    barCornerRadius = barCornerRadius
                )
            }

            LegendPosition.BOTTOM -> {
                BarChartContent(
                    barsParameters = chartParameters,
                    gridColor = gridColor,
                    xAxisData = xAxisData,
                    isShowGrid = isShowGrid,
                    animateChart = animateChart,
                    showGridWithSpacer = showGridWithSpacer,
                    yAxisStyle = yAxisStyle,
                    xAxisStyle = xAxisStyle,
                    backgroundLineWidth = backgroundLineWidth,
                    yAxisRange = yAxisRange,
                    showXAxis = showXAxis,
                    showYAxis = showYAxis,
                    barWidth = barWidth,
                    spaceBetweenBars = spaceBetweenBars,
                    spaceBetweenGroups = spaceBetweenGroups,
                    modifier = Modifier.weight(1f),
                    barCornerRadius = barCornerRadius
                )

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
            }

            LegendPosition.DISAPPEAR -> {
                BarChartContent(
                    barsParameters = chartParameters,
                    gridColor = gridColor,
                    xAxisData = xAxisData,
                    isShowGrid = isShowGrid,
                    animateChart = animateChart,
                    showGridWithSpacer = showGridWithSpacer,
                    yAxisStyle = yAxisStyle,
                    xAxisStyle = xAxisStyle,
                    backgroundLineWidth = backgroundLineWidth,
                    yAxisRange = yAxisRange,
                    showXAxis = showXAxis,
                    showYAxis = showYAxis,
                    barWidth = barWidth,
                    spaceBetweenBars = spaceBetweenBars,
                    spaceBetweenGroups = spaceBetweenGroups,
                    barCornerRadius = barCornerRadius
                )

            }
        }

    }
}