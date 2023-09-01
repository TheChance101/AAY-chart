package com.aay.compose.donutChart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.donutChart.component.PieChartDescriptionComposable
import com.aay.compose.donutChart.component.draPieCircle
import com.aay.compose.donutChart.component.drawPedigreeChart
import com.aay.compose.donutChart.model.ChartTypes
import com.aay.compose.donutChart.model.PieChartData
import com.aay.compose.utils.checkIfDataIsNegative
import kotlin.math.min
import androidx.compose.animation.core.*
import androidx.compose.ui.text.*
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.utils.ChartDefaultValues
/**
 * Composable function to render a pie chart with an optional legend.
 *
 * @param modifier Modifier for configuring the layout and appearance of the pie chart.
 * @param pieChartData List of data for the pie chart, including labels and values.
 * @param animation Animation specification for the pie chart transitions (default is a 3-second linear animation).
 * @param textRatioStyle TextStyle for configuring the appearance of ratio text labels (default font size is 12sp).
 * @param outerCircularColor Color of the outer circular border of the pie chart (default is Gray).
 * @param ratioLineColor Color of the lines connecting ratio labels to chart segments (default is Gray).
 * @param descriptionStyle TextStyle for configuring the appearance of the chart description (legend) text.
 * @param legendPosition Position of the legend within the chart (default is [LegendPosition.TOP]).
 *
 * @see PieChartData
 * @see LegendPosition
 */

@OptIn(ExperimentalTextApi::class)
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    pieChartData: List<PieChartData>,
    animation: AnimationSpec<Float> = TweenSpec(durationMillis = 3000),
    textRatioStyle: TextStyle = TextStyle.Default.copy(fontSize = 12.sp),
    outerCircularColor: Color = Color.Gray,
    ratioLineColor: Color = Color.Gray,
    descriptionStyle: TextStyle = TextStyle.Default,
    legendPosition: LegendPosition = ChartDefaultValues.legendPosition,
) {
    var totalSum = 0.0f
    val pieValueWithRatio = mutableListOf<Float>()
    pieChartData.forEach {
        totalSum += it.data.toFloat()
    }
    pieChartData.forEachIndexed { index, part ->
        pieValueWithRatio.add(index, 360 * part.data.toFloat() / totalSum)
    }

    val textMeasure = rememberTextMeasurer()


    checkIfDataIsNegative(data = pieChartData.map { it.data })
    val transitionProgress = remember(pieValueWithRatio) { Animatable(initialValue = 0F) }

    LaunchedEffect(pieChartData) {
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
                    pieChartData = pieChartData,
                    descriptionStyle = descriptionStyle,
                    modifier = Modifier.fillMaxWidth().weight(0.5f)
                )
                drawPieChart(
                    modifier = Modifier.weight(1.5f),
                    pieChartData = pieChartData,
                    textRatioStyle = textRatioStyle,
                    outerCircularColor = outerCircularColor,
                    ratioLineColor = ratioLineColor,
                    pieValueWithRatio = pieValueWithRatio,
                    totalSum = totalSum,
                    transitionProgress = transitionProgress,
                    textMeasure = textMeasure
                )
            }

            LegendPosition.BOTTOM -> {
                drawPieChart(
                    modifier = Modifier.weight(1.5f),
                    pieChartData = pieChartData,
                    textRatioStyle = textRatioStyle,
                    outerCircularColor = outerCircularColor,
                    ratioLineColor = ratioLineColor,
                    pieValueWithRatio = pieValueWithRatio,
                    totalSum = totalSum,
                    transitionProgress = transitionProgress,
                    textMeasure = textMeasure
                )

                PieChartDescriptionComposable(
                    pieChartData = pieChartData,
                    descriptionStyle = descriptionStyle,
                    modifier = Modifier.fillMaxWidth().weight(0.5f)
                )
            }

            else -> {
                drawPieChart(
                    modifier = Modifier.weight(1.5f),
                    pieChartData = pieChartData,
                    textRatioStyle = textRatioStyle,
                    outerCircularColor = outerCircularColor,
                    ratioLineColor = ratioLineColor,
                    pieValueWithRatio = pieValueWithRatio,
                    totalSum = totalSum,
                    transitionProgress = transitionProgress,
                    textMeasure = textMeasure
                )
            }
        }
    }

}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun drawPieChart(
    modifier: Modifier = Modifier,
    pieChartData: List<PieChartData>,
    textRatioStyle: TextStyle,
    outerCircularColor: Color,
    ratioLineColor: Color,
    pieValueWithRatio: MutableList<Float>,
    totalSum: Float,
    transitionProgress: Animatable<Float, AnimationVector1D>,
    textMeasure: TextMeasurer,
) {
    Box(
        modifier = modifier.fillMaxSize()
            .drawBehind {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val minValue = min(canvasWidth, canvasHeight)
                    .coerceAtMost(canvasHeight / 2)
                    .coerceAtMost(canvasWidth / 2)
                val arcWidth =
                    (size.minDimension.dp.toPx() * 0.13f).coerceAtMost(minValue / 4)

                drawPedigreeChart(
                    pieValueWithRatio = pieValueWithRatio,
                    pieChartData = pieChartData,
                    totalSum = totalSum,
                    transitionProgress = transitionProgress,
                    textMeasure = textMeasure,
                    textRatioStyle = textRatioStyle,
                    ratioLineColor = ratioLineColor,
                    arcWidth = arcWidth,
                    minValue = minValue,
                    pieChart = ChartTypes.PIE_CHART
                )
                //draw outer circle
                draPieCircle(
                    circleColor = outerCircularColor,
                    radiusRatioCircle = (minValue / 2) + (arcWidth / 1.5f)
                )

            })

}
