package com.aay.compose.pieChart

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.pieChart.component.PieChartDescriptionComposable
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
    animation: AnimationSpec<Float> = TweenSpec(durationMillis = 3000),
    descriptionStyle: TextStyle = TextStyle.Default,
    testRatioStyle: TextStyle = TextStyle.Default.copy(fontSize = 12.sp),
    outerCircularColor: Color = Color.Gray,
    innerCircularColor: Color = Color.Gray,
    ratioLineColor: Color = Color.Gray
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
        modifier = modifier.padding(vertical = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Box(
            modifier = Modifier.fillMaxSize().weight(1f)
                .drawBehind {
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

                    drawPedigreeChart(
                        pieValueWithRatio = pieValueWithRatio,
                        pieChartData = pieChartData,
                        totalSum = totalSum,
                        transitionProgress = transitionProgress,
                        textMeasure = textMeasure,
                        textRatioStyle =testRatioStyle,
                        innerCircularColor = innerCircularColor,
                        outerCircularColor = outerCircularColor,
                        ratioLineColor = ratioLineColor
                    )

                }
        )

        PieChartDescriptionComposable(
            pieChartData = pieChartData,
            descriptionStyle = descriptionStyle,
            modifier = Modifier.weight(1f)
        )

    }

}