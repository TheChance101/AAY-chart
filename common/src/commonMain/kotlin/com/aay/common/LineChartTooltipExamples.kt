package com.aay.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.*

/**
 * Example 1: Default tooltip (backward compatibility)
 * Shows the default "Value: X" tooltip with stroke circle marker
 */
@Composable
fun LineChartWithDefaultTooltip() {
    val lineParameters = listOf(
        LineParameters(
            label = "Revenue",
            data = listOf(70.0, 80.0, 50.33, 40.0, 100.500, 50.0),
            lineColor = Color(0xFF6C3428),
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
            // No tooltipConfig specified - uses default
        )
    )

    Box(Modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = lineParameters,
            isGrid = true,
            gridColor = Color.LightGray,
            xAxisData = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun"),
            animateChart = true,
            yAxisRange = 14,
        )
    }
}

/**
 * Example 2: Localized tooltip with custom label
 * Shows how to localize the tooltip label (e.g., Arabic)
 */
@Composable
fun LineChartWithLocalizedTooltip() {
    val lineParameters = listOf(
        LineParameters(
            label = "الإيرادات",
            data = listOf(70.0, 80.0, 50.33, 40.0, 100.500, 50.0),
            lineColor = Color(0xFF6C3428),
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
            tooltipConfig = TooltipConfig(
                content = TooltipContent.YValue(
                    label = "القيمة", // "Value" in Arabic
                )
            )
        )
    )

    Box(Modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = lineParameters,
            isGrid = true,
            gridColor = Color.LightGray,
            xAxisData = listOf("يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو"),
            animateChart = true,
            yAxisRange = 14,
        )
    }
}

/**
 * Example 3: Tooltip showing both X and Y coordinates
 * Demonstrates how to display both axis values in the tooltip
 */
@Composable
fun LineChartWithXYTooltip() {
    val lineParameters = listOf(
        LineParameters(
            label = "Sales",
            data = listOf(70.0, 80.0, 50.33, 40.0, 100.500, 50.0),
            lineColor = Color(0xFF2196F3),
            lineType = LineType.DEFAULT_LINE,
            lineShadow = true,
            tooltipConfig = TooltipConfig(
                content = TooltipContent.XYValue(
                    xLabel = "Month",
                    yLabel = "Sales",
                    xAxisData = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun")
                ),
                backgroundColor = Color.White,
                textColor = Color.Black,
                textSize = 10.sp,
            )
        )
    )

    Box(Modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = lineParameters,
            isGrid = true,
            gridColor = Color.LightGray,
            xAxisData = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun"),
            animateChart = true,
            yAxisRange = 14,
        )
    }
}

/**
 * Example 4: Customized tooltip appearance
 * Shows how to customize background color, size, corner radius, and text style
 */
@Composable
fun LineChartWithCustomStyledTooltip() {
    val lineParameters = listOf(
        LineParameters(
            label = "Revenue",
            data = listOf(70.0, 80.0, 50.33, 40.0, 100.500, 50.0),
            lineColor = Color(0xFF4CAF50),
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
            tooltipConfig = TooltipConfig(
                backgroundColor = Color(0xFF1E1E1E), // Dark background
                borderColor = Color(0xFF4CAF50), // Green border
                textColor = Color.White,
                textSize = 12.sp,
                cornerRadius = 8.dp,
                size = TooltipSize.Fixed(width = 120.dp, height = 40.dp),
                padding = 12.dp,
                content = TooltipContent.YValue(
                    label = "Revenue",
                    formatter = { value -> "$${value.toInt()}" } // Custom formatter
                )
            )
        )
    )

    Box(Modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = lineParameters,
            isGrid = true,
            gridColor = Color.LightGray,
            xAxisData = listOf("Q1", "Q2", "Q3", "Q4", "Q5", "Q6"),
            animateChart = true,
            yAxisRange = 14,
        )
    }
}

/**
 * Example 5: Solid circle marker instead of stroke
 * Demonstrates how to use a filled circle marker
 */
@Composable
fun LineChartWithSolidMarker() {
    val lineParameters = listOf(
        LineParameters(
            label = "Temperature",
            data = listOf(20.0, 22.5, 25.0, 23.5, 21.0, 19.5),
            lineColor = Color(0xFFFF5722),
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
            tooltipConfig = TooltipConfig(
                markerStyle = MarkerStyle.Solid, // Solid filled circle
                backgroundColor = Color(0xFFF8F8F8),
                //borderColor = Color(0xFFFF5722),
                textColor = Color(0xFF5D4037),
                content = TooltipContent.YValue(
                    label = "Temp",
                    formatter = { value -> "${value.toInt()}°C" }
                ),
                position = TooltipPosition.Left,
                cornerRadii = TooltipCornerRadii(
                    topLeft = 8.dp,
                    topRight = 8.dp,
                    bottomLeft = 8.dp,
                    bottomRight = 2.dp
                )
            )
        )
    )

    Box(Modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = lineParameters,
            isGrid = true,
            gridColor = Color.LightGray,
            xAxisData = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
            animateChart = true,
            yAxisRange = 10,
        )
    }
}

/**
 * Example 6: No marker circle (tooltip only)
 * Shows tooltip without the circular marker
 */
@Composable
fun LineChartWithTooltipNoMarker() {
    val lineParameters = listOf(
        LineParameters(
            label = "Progress",
            data = listOf(10.0, 25.0, 45.0, 60.0, 80.0, 95.0),
            lineColor = Color(0xFF9C27B0),
            lineType = LineType.DEFAULT_LINE,
            lineShadow = true,
            tooltipConfig = TooltipConfig(
                markerStyle = MarkerStyle.None, // No marker circle
                backgroundColor = Color(0xFFF3E5F5),
                textColor = Color(0xFF4A148C),
                content = TooltipContent.YValue(
                    label = "Progress",
                    formatter = { value -> "${value.toInt()}%" }
                )
            )
        )
    )

    Box(Modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = lineParameters,
            isGrid = true,
            gridColor = Color.LightGray,
            xAxisData = listOf("Week 1", "Week 2", "Week 3", "Week 4", "Week 5", "Week 6"),
            animateChart = true,
            yAxisRange = 14,
        )
    }
}

/**
 * Example 7: Custom tooltip content with formatter
 * Demonstrates complete control over tooltip content
 */
@Composable
fun LineChartWithCustomTooltipContent() {
    val lineParameters = listOf(
        LineParameters(
            label = "Users",
            data = listOf(1200.0, 1850.0, 2100.0, 1950.0, 2400.0, 2800.0),
            lineColor = Color(0xFF00BCD4),
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
            tooltipConfig = TooltipConfig(
                markerStyle = MarkerStyle.Stroke(strokeWidth = 3.dp),
                backgroundColor = Color(0xFFE0F7FA),
                borderColor = Color(0xFF00BCD4),
                textColor = Color(0xFF006064),
                textSize = 11.sp,
                content = TooltipContent.Custom { xIndex, yValue, xAxisData ->
                    val month = xAxisData.getOrNull(xIndex) ?: "N/A"
                    val users = yValue.toInt()
                    "$month\n$users users"
                }
            )
        )
    )

    Box(Modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = lineParameters,
            isGrid = true,
            gridColor = Color.LightGray,
            xAxisData = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun"),
            animateChart = true,
            yAxisRange = 14,
        )
    }
}

/**
 * Example 8: Multi-line chart with different tooltip configs
 * Shows how each line can have its own tooltip configuration
 */
@Composable
fun MultiLineChartWithDifferentTooltips() {
    val lineParameters = listOf(
        LineParameters(
            label = "Revenue",
            data = listOf(70.0, 80.0, 50.33, 40.0, 100.500, 50.0),
            lineColor = Color(0xFF4CAF50),
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
            tooltipConfig = TooltipConfig(
                markerStyle = MarkerStyle.Solid,
                backgroundColor = Color(0xFFE8F5E9),
                textColor = Color(0xFF1B5E20),
                content = TooltipContent.YValue(label = "Revenue")
            )
        ),
        LineParameters(
            label = "Expenses",
            data = listOf(60.0, 70.6, 40.33, 86.232, 88.0, 90.0),
            lineColor = Color(0xFFFF5722),
            lineType = LineType.DEFAULT_LINE,
            lineShadow = true,
            tooltipConfig = TooltipConfig(
                markerStyle = MarkerStyle.Stroke(strokeWidth = 2.dp),
                backgroundColor = Color(0xFFFBE9E7),
                textColor = Color(0xFFBF360C),
                content = TooltipContent.YValue(label = "Expenses")
            )
        ),
    )

    Box(Modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = lineParameters,
            isGrid = true,
            gridColor = Color.LightGray,
            xAxisData = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun"),
            animateChart = true,
            yAxisRange = 14,
            oneLineChart = false,
        )
    }
}

/**
 * Example 9: Disabled tooltip
 * Shows how to disable the tooltip completely
 */
@Composable
fun LineChartWithDisabledTooltip() {
    val lineParameters = listOf(
        LineParameters(
            label = "Data",
            data = listOf(70.0, 80.0, 50.33, 40.0, 100.500, 50.0),
            lineColor = Color(0xFF607D8B),
            lineType = LineType.CURVED_LINE,
            lineShadow = true,
            tooltipConfig = TooltipConfig(
                enabled = false // Disable tooltip
            )
        )
    )

    Box(Modifier.fillMaxSize()) {
        LineChart(
            modifier = Modifier.fillMaxSize(),
            linesParameters = lineParameters,
            isGrid = true,
            gridColor = Color.LightGray,
            xAxisData = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun"),
            animateChart = true,
            yAxisRange = 14,
        )
    }
}
