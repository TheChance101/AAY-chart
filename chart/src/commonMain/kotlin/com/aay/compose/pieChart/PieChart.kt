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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.ChartDescription
import com.aay.compose.pieChart.model.PiePart
import java.awt.SystemColor.text

@OptIn(ExperimentalTextApi::class)
@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    pieParts: List<PiePart>,
    centerTitle: String = "",
    animDuration: Int = 1000,
    radiusOuter: Dp,
) {

    var totalSum = 0.0f
    pieParts.forEach {
        totalSum += it.data
    }

    val textMeasure = rememberTextMeasurer()
    val textLayoutResult: TextLayoutResult = textMeasure.measure(text = AnnotatedString(centerTitle))
    val textSize = textLayoutResult.size


    val floatValue = mutableListOf<Float>()
    pieParts.forEachIndexed { index, part ->
        floatValue.add(index, 360 * part.data / totalSum)
    }

    var animationPlayed by remember { mutableStateOf(false) }
    var lastValue = -90f

    // it is the diameter value of the Pie
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    // if you want to stabilize the Pie Chart you can use value -90f
    // -90f is used to complete 1/4 of the rotation
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
        modifier = modifier.wrapContentSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(radiusOuter).background(Color.Green.copy(alpha = .5f)),
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()

            ) {

                val canvasWidth = size.width
                val canvasHeight = size.height

                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        textMeasurer = textMeasure,
                        text = centerTitle.take(15),
                        style = TextStyle.Default,
                        topLeft = Offset(
                            (canvasWidth - textSize.width) / 2f,
                            (canvasHeight - textSize.height) / 2f
                        ),
                    )
                }

                // draw inner circle in pie chart
                drawCircle(
                    color = Color.Gray,
                    radius = size.minDimension.dp.toPx() / 3.dp.toPx(),
                    style = Stroke(1.dp.toPx(), cap = StrokeCap.Round)
                )


                // draw each Arc for each data entry in Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = pieParts[index].color,
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(size.maxDimension.dp.toPx() / 4.dp.toPx(), cap = StrokeCap.Butt),
                    )
                    lastValue += value
                }

                // draw outer circle in pi chart
                drawCircle(
                    color = Color.Gray,
                    radius = size.minDimension.dp.toPx() / 1.5.dp.toPx(),
                    style = Stroke(1.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 32.dp),
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
