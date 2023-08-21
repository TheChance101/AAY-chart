package com.aay.compose.pieChart

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.pieChart.component.PieChartDescriptionComposable
import com.aay.compose.pieChart.component.draPieCircle
import com.aay.compose.pieChart.component.drawCenterText
import com.aay.compose.pieChart.component.drawPedigreeChart
import com.aay.compose.pieChart.model.PieChartData

@OptIn(ExperimentalTextApi::class)
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    pieChartData: List<PieChartData>,
    centerTitle: String = "",
    centerTitleStyle: TextStyle = TextStyle.Default,
    animation: AnimationSpec<Float> = TweenSpec(durationMillis = 1000),
    radiusOuter: Dp,
    descriptionStyle: TextStyle = TextStyle.Default
) {

    var totalSum = 0.0f
    val pieValueWithRatio = mutableListOf<Float>()
    pieChartData.forEach {
        totalSum += it.data
    }
    pieChartData.forEachIndexed { index, part ->
        pieValueWithRatio.add(index, 360 * part.data / totalSum)
    }


    val textMeasure = rememberTextMeasurer()
    val textLayoutResult: TextLayoutResult = textMeasure.measure(
        text = AnnotatedString(centerTitle.take(10))
    )
    val textSize = textLayoutResult.size


    val transitionProgress = remember(pieValueWithRatio) { Animatable(initialValue = 0F) }


    LaunchedEffect(pieChartData) {
        transitionProgress.animateTo(1F, animationSpec = animation)
    }


    Column(
        modifier = modifier.wrapContentSize().padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(radiusOuter).padding(top = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.size(radiusOuter / 1.2f)

            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                drawCenterText(
                    textMeasure = textMeasure,
                    centerTitle = centerTitle,
                    centerTitleStyle = centerTitleStyle,
                    canvasHeight = canvasHeight,
                    canvasWidth = canvasWidth,
                    textSize = textSize
                )

                // draw inner circle in pie chart
                draPieCircle(
                    circleColor = Color.Gray.copy(alpha = .5f),
                    radiusRatioCircle = 3.dp.toPx()
                )

                drawPedigreeChart(
                    pieValueWithRatio = pieValueWithRatio,
                    pieChartData = pieChartData,
                    totalSum = totalSum,
                    transitionProgress = transitionProgress
                )

                // draw outer circle in pie chart
                draPieCircle(
                    circleColor = Color.Gray.copy(alpha = .5f),
                    radiusRatioCircle = 1.5.dp.toPx()
                )
            }
        }

        PieChartDescriptionComposable(
            pieChartData = pieChartData,
            descriptionStyle = descriptionStyle
        )
    }

}