package com.aay.compose.ringChart

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.donutChart.component.PieChartDescriptionComposable
import com.aay.compose.ringChart.model.RingChartData
import kotlin.math.min

/**
 * Composable function to render a ring chart similar to an iOS fitness app.
 *
 * @param modifier Modifier for configuring the layout and appearance of the ring chart.
 * @param ringChartData List of data for the ring chart, including values, max values, and colors.
 * @param centerContent Optional composable to display in the center of the ring chart.
 * @param animation Animation specification for the ring chart transitions (default is a 1.5-second linear animation).
 * @param descriptionStyle TextStyle for configuring the appearance of the chart description (legend) text.
 * @param ringWidth Width of the rings as a percentage of the available space (default is 0.15f).
 * @param ringGap Gap between concentric rings as a percentage of the available space (default is 0.02f).
 * @param legendPosition Position of the legend within the chart (default is [LegendPosition.BOTTOM]).
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun RingChart(
    modifier: Modifier = Modifier,
    ringChartData: List<RingChartData>,
    centerContent: @Composable BoxScope.() -> Unit = {},
    animation: AnimationSpec<Float> = TweenSpec(durationMillis = 1500),
    descriptionStyle: TextStyle = TextStyle.Default,
    ringWidth: Float = 0.15f,
    ringGap: Float = 0.02f,
    legendPosition: LegendPosition = LegendPosition.BOTTOM,
) {
    // Convert RingChartData to PieChartData for legend
    val pieChartDataForLegend = ringChartData.map {
        com.aay.compose.donutChart.model.PieChartData(
            partName = it.name,
            data = it.value,
            color = it.color
        )
    }

    val transitionProgress = remember(ringChartData) { Animatable(initialValue = 0F) }

    LaunchedEffect(ringChartData) {
        transitionProgress.animateTo(1F, animationSpec = animation)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        when (legendPosition) {
            LegendPosition.TOP -> {
                PieChartDescriptionComposable(
                    pieChartData = pieChartDataForLegend,
                    descriptionStyle = descriptionStyle,
                    modifier = Modifier.fillMaxWidth().weight(0.5f)
                )
                DrawRingChart(
                    modifier = Modifier.weight(1.5f),
                    ringChartData = ringChartData,
                    centerContent = centerContent,
                    ringWidth = ringWidth,
                    ringGap = ringGap,
                    transitionProgress = transitionProgress
                )
            }
            LegendPosition.BOTTOM -> {
                DrawRingChart(
                    modifier = Modifier.weight(1.5f),
                    ringChartData = ringChartData,
                    centerContent = centerContent,
                    ringWidth = ringWidth,
                    ringGap = ringGap,
                    transitionProgress = transitionProgress
                )
                PieChartDescriptionComposable(
                    pieChartData = pieChartDataForLegend,
                    descriptionStyle = descriptionStyle,
                    modifier = Modifier.fillMaxWidth().weight(0.5f)
                )
            }
            else -> {
                DrawRingChart(
                    modifier = Modifier.weight(1.5f),
                    ringChartData = ringChartData,
                    centerContent = centerContent,
                    ringWidth = ringWidth,
                    ringGap = ringGap,
                    transitionProgress = transitionProgress
                )
            }
        }
    }
}

@Composable
private fun DrawRingChart(
    modifier: Modifier = Modifier,
    ringChartData: List<RingChartData>,
    centerContent: @Composable BoxScope.() -> Unit,
    ringWidth: Float,
    ringGap: Float,
    transitionProgress: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = modifier.fillMaxSize()
            .drawBehind {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val minValue = min(canvasWidth, canvasHeight)
                    .coerceAtMost(canvasHeight / 2)
                    .coerceAtMost(canvasWidth / 2)
                
                // Calculate the starting radius for the outermost ring
                val baseRadius = minValue / 2
                
                // Draw rings from outside to inside
                ringChartData.forEachIndexed { index, data ->
                    val ringRadius = baseRadius - (index * (ringWidth + ringGap) * baseRadius)
                    val strokeWidth = ringWidth * baseRadius
                    
                    // Draw a background ring with the same color as the progress ring but with transparency
                    drawArc(
                        color = data.color.copy(alpha = 0.2f),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round
                        ),
                        size = Size(ringRadius * 2, ringRadius * 2),
                        topLeft = Offset(center.x - ringRadius, center.y - ringRadius)
                    )
                    
                    // Calculate progress angle
                    val progress = (data.value / data.maxValue).coerceIn(0.0, 1.0)
                    val sweepAngle = (progress * 360 * transitionProgress.value).toFloat()
                    
                    // Draw a progress ring
                    drawArc(
                        color = data.color,
                        startAngle = -90f, // Start from the top
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round
                        ),
                        size = Size(ringRadius * 2, ringRadius * 2),
                        topLeft = Offset(center.x - ringRadius, center.y - ringRadius)
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Center content
        centerContent()
    }
}