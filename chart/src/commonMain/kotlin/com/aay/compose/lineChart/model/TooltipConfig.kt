package com.aay.compose.lineChart.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.utils.formatToThousandsMillionsBillions

/**
 * Configuration for customizing the tooltip appearance and content when clicking on line chart points.
 *
 * @param enabled Whether to show the tooltip when clicking on points
 * @param backgroundColor Background color of the tooltip box
 * @param borderColor Border color of the tooltip box. If null, uses the line color
 * @param textColor Color of the text inside the tooltip
 * @param textSize Font size of the tooltip text
 * @param cornerRadius Corner radius of the tooltip box
 * @param size Size configuration for the tooltip box
 * @param content Content configuration defining what to display in the tooltip
 * @param padding Internal padding of the tooltip box
 * @param markerStyle Style configuration for the circular marker at the clicked point
 */
data class TooltipConfig(
    val enabled: Boolean = true,
    val backgroundColor: Color = Color.White,
    val borderColor: Color? = null,
    val textColor: Color = Color.Black,
    val textSize: TextUnit = 8.sp,
    val cornerRadius: Dp = 16.dp,
    val size: TooltipSize = TooltipSize.Auto,
    val content: TooltipContent = TooltipContent.YValue(),
    val padding: Dp = 8.dp,
    val markerStyle: MarkerStyle = MarkerStyle.Stroke(),
)

/**
 * Defines the size of the tooltip box.
 */
sealed class TooltipSize {
    /**
     * Automatically calculates size based on text content
     */
    object Auto : TooltipSize()
    
    /**
     * Fixed size for the tooltip box
     * @param width Width of the tooltip
     * @param height Height of the tooltip
     */
    data class Fixed(val width: Dp, val height: Dp) : TooltipSize()
}

/**
 * Defines the content to display in the tooltip.
 */
sealed class TooltipContent {
    /**
     * Display only the Y value (data point value)
     * @param label Label text to show before the value (e.g., "Value", "القيمة")
     * @param formatter Function to format the Y value for display
     */
    data class YValue(
        val label: String = "Value",
        val formatter: (Double) -> String = { it.toFloat().formatToThousandsMillionsBillions() }
    ) : TooltipContent()
    
    /**
     * Display only the X value (axis label)
     * @param label Label text to show before the X value
     * @param xAxisData List of X-axis labels to use for display
     */
    data class XValue(
        val label: String = "X",
        val xAxisData: List<String> = emptyList()
    ) : TooltipContent()
    
    /**
     * Display both X and Y values
     * @param xLabel Label for the X value
     * @param yLabel Label for the Y value
     * @param xAxisData List of X-axis labels to use for X value display
     * @param yFormatter Function to format the Y value for display
     */
    data class XYValue(
        val xLabel: String = "X",
        val yLabel: String = "Y",
        val xAxisData: List<String> = emptyList(),
        val yFormatter: (Double) -> String = { it.toFloat().formatToThousandsMillionsBillions() }
    ) : TooltipContent()
    
    /**
     * Custom tooltip content with full control over formatting
     * @param formatter Function that receives x index, y value, and x-axis data, and returns formatted string
     */
    data class Custom(
        val formatter: (xIndex: Int, yValue: Double, xAxisData: List<String>) -> String
    ) : TooltipContent()
}

/**
 * Defines the style of the circular marker shown at the clicked point.
 */
sealed class MarkerStyle {
    /**
     * Stroke (outline) circle marker
     * @param strokeWidth Width of the circle outline
     */
    data class Stroke(val strokeWidth: Dp = 2.dp) : MarkerStyle()
    
    /**
     * Solid (filled) circle marker
     */
    object Solid : MarkerStyle()
    
    /**
     * No marker circle (only show tooltip)
     */
    object None : MarkerStyle()
}
