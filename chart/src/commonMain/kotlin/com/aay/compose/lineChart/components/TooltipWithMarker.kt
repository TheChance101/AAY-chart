package com.aay.compose.lineChart.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.MarkerStyle
import com.aay.compose.lineChart.model.TooltipContent
import com.aay.compose.lineChart.model.TooltipSize

/**
 * Draws a tooltip with a circular marker at the clicked point on the line chart.
 * 
 * This is the main entry point for rendering tooltips when a user clicks on a data point.
 * It draws both the marker circle and the tooltip box with customizable content and styling.
 *
 * @param animatedProgress Animation state for the chart
 * @param textMeasurer Text measurer for calculating text dimensions
 * @param xIndex Index of the clicked point in the data array
 * @param yValue Y-axis value of the clicked point
 * @param line Line parameters containing tooltip configuration
 * @param x X-coordinate of the point
 * @param y Y-coordinate of the point
 * @param xAxisData List of X-axis labels for displaying X values in tooltip
 */
@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawTooltipWithMarker(
    animatedProgress: Animatable<Float, AnimationVector1D>,
    textMeasurer: TextMeasurer,
    xIndex: Int,
    yValue: Double,
    line: LineParameters,
    x: Dp,
    y: Double,
    xAxisData: List<String> = emptyList(),
) {
    val config = line.tooltipConfig
    
    if (!config.enabled) return
    
    // Draw marker circle at the point
    drawMarkerCircle(
        x = x.toPx(),
        y = y.toFloat(),
        color = line.lineColor,
        animatedProgress = animatedProgress,
        markerStyle = config.markerStyle
    )
    
    // Draw tooltip box with content
    drawTooltipBox(
        x = x,
        y = y,
        color = line.lineColor,
        textMeasurer = textMeasurer,
        xIndex = xIndex,
        yValue = yValue,
        xAxisData = xAxisData,
        config = config
    )
}

/**
 * Draws the circular marker at the clicked point.
 *
 * @param x X-coordinate of the marker center
 * @param y Y-coordinate of the marker center
 * @param color Color of the marker
 * @param animatedProgress Animation state
 * @param markerStyle Style of the marker (Stroke, Solid, or None)
 */
private fun DrawScope.drawMarkerCircle(
    x: Float,
    y: Float,
    color: Color,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    markerStyle: MarkerStyle,
) {
    when (markerStyle) {
        is MarkerStyle.Stroke -> {
            chartCircle(
                x = x,
                y = y,
                color = color,
                animatedProgress = animatedProgress,
                stroke = Stroke(width = markerStyle.strokeWidth.toPx())
            )
        }
        is MarkerStyle.Solid -> {
            chartCircle(
                x = x,
                y = y,
                color = color,
                animatedProgress = animatedProgress,
                stroke = null // null means filled circle
            )
        }
        is MarkerStyle.None -> {
            // Don't draw any marker
        }
    }
}

/**
 * Draws the tooltip box with formatted content.
 *
 * @param x X-coordinate of the point
 * @param y Y-coordinate of the point
 * @param color Default color (line color) used if borderColor is not specified
 * @param textMeasurer Text measurer for calculating text dimensions
 * @param xIndex Index of the point in the data array
 * @param yValue Y-axis value of the point
 * @param xAxisData List of X-axis labels
 * @param config Tooltip configuration
 */
@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawTooltipBox(
    x: Dp,
    y: Double,
    color: Color,
    textMeasurer: TextMeasurer,
    xIndex: Int,
    yValue: Double,
    xAxisData: List<String>,
    config: com.aay.compose.lineChart.model.TooltipConfig,
) {
    // Generate tooltip text based on content configuration
    val tooltipText = generateTooltipText(
        content = config.content,
        xIndex = xIndex,
        yValue = yValue,
        xAxisData = xAxisData
    )
    
    // Create text style
    val textStyle = TextStyle(
        fontSize = config.textSize,
        color = config.textColor
    )
    
    // Measure text to determine tooltip size
    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(tooltipText),
        style = textStyle
    )
    
    // Calculate tooltip box size
    val tooltipSize = when (config.size) {
        is TooltipSize.Auto -> {
            // Auto-size based on text with padding
            Size(
                width = textLayoutResult.size.width + (config.padding.toPx() * 2),
                height = textLayoutResult.size.height + (config.padding.toPx() * 2)
            )
        }
        is TooltipSize.Fixed -> {
            Size(
                width = config.size.width.toPx(),
                height = config.size.height.toPx()
            )
        }
    }
    
    // Calculate tooltip position (centered above the point)
    val tooltipTopLeft = Offset(
        x = x.toPx() - tooltipSize.width / 2f,
        y = y.toFloat() - tooltipSize.height - 20.dp.toPx() // 20dp gap above the point
    )
    
    val tooltipBounds = Rect(tooltipTopLeft, tooltipSize)
    
    // Draw tooltip background
    drawRoundRect(
        color = config.backgroundColor,
        topLeft = tooltipBounds.topLeft,
        size = tooltipBounds.size,
        cornerRadius = CornerRadius(config.cornerRadius.toPx()),
        style = Fill
    )
    
    // Draw tooltip border
    val borderColor = config.borderColor ?: color
    drawRoundRect(
        color = borderColor,
        topLeft = tooltipBounds.topLeft,
        size = tooltipBounds.size,
        cornerRadius = CornerRadius(config.cornerRadius.toPx()),
        style = Stroke(width = 1.dp.toPx())
    )
    
    // Calculate text position (centered in tooltip)
    val textOffset = Offset(
        x = tooltipTopLeft.x + (tooltipSize.width - textLayoutResult.size.width) / 2f,
        y = tooltipTopLeft.y + (tooltipSize.height - textLayoutResult.size.height) / 2f
    )
    
    // Draw text
    drawContext.canvas.nativeCanvas.apply {
        drawText(
            textMeasurer = textMeasurer,
            text = tooltipText,
            style = textStyle,
            topLeft = textOffset
        )
    }
}

/**
 * Generates the tooltip text content based on the configuration.
 *
 * @param content Tooltip content configuration
 * @param xIndex Index of the point
 * @param yValue Y-axis value
 * @param xAxisData List of X-axis labels
 * @return Formatted tooltip text
 */
private fun generateTooltipText(
    content: TooltipContent,
    xIndex: Int,
    yValue: Double,
    xAxisData: List<String>,
): String {
    return when (content) {
        is TooltipContent.YValue -> {
            val formattedValue = content.formatter(yValue)
            "${content.label}: $formattedValue"
        }
        is TooltipContent.XValue -> {
            val xValue = content.xAxisData.getOrElse(xIndex) { 
                xAxisData.getOrElse(xIndex) { xIndex.toString() }
            }
            "${content.label}: $xValue"
        }
        is TooltipContent.XYValue -> {
            val xValue = content.xAxisData.getOrElse(xIndex) {
                xAxisData.getOrElse(xIndex) { xIndex.toString() }
            }
            val formattedYValue = content.yFormatter(yValue)
            "${content.xLabel}: $xValue\n${content.yLabel}: $formattedYValue"
        }
        is TooltipContent.Custom -> {
            content.formatter(xIndex, yValue, xAxisData)
        }
    }
}