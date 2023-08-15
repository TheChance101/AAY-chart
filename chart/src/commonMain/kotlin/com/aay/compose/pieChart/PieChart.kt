package com.aay.compose.pieChart

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.ChartDescription
import com.aay.compose.pieChart.model.PiePart
import java.awt.SystemColor.text

@OptIn(ExperimentalTextApi::class)
@Composable
fun PieChart(
    pieParts: List<PiePart>,
    modifier: Modifier = Modifier,
    animDuration: Int = 1000,
    chartBarWidth: Dp,
    radiusOuter: Dp,
) {
    var totalSum = 0.0f
    pieParts.forEach {
        totalSum += it.data
    }
    val textMeasure = rememberTextMeasurer()
    val floatValue = mutableListOf<Float>()
    pieParts.forEachIndexed { index, part ->
        floatValue.add(index, 360 * part.data / totalSum)
    }

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f
    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // 90f is used to complete 1/4 of the rotation
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // to play the animation only once when the function is Created or Recomposed
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(animateSize.dp).padding(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .rotate(animateRotation)
                    .size(radiusOuter * 2f)
                    .background(Color.Magenta)
            ) {

                val canvasWidth = size.width
                val canvasHeight = size.height
                val x = (canvasWidth ) / 2
                val y = (canvasHeight ) / 2
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        textMeasurer = textMeasure,
                        text = " dataPoint",
                        style = TextStyle.Default,
                        topLeft = Offset(x,y)
                    )
                }
                // draw each Arc for each data entry in Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = pieParts[index].color,
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {

            items(pieParts) { details ->
                ChartDescription(
                    chartColor = details.color,
                    chartName = details.partName,
                    descriptionStyle = TextStyle.Default,
                )
            }
        }

    }

}
