package com.aay.compose.baseComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.utils.formatToThousandsMillionsBillions
import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.xAxisDrawing(
    xAxisData: List<T>,
    spacing: Dp,
    textMeasure: TextMeasurer,
    xAxisStyle: TextStyle,
    specialChart: Boolean,
    upperValue: Float,
    ) {
    if (specialChart) {
        return
    }
    val yTextLayoutResult = textMeasure.measure(
        text = AnnotatedString(upperValue.formatToThousandsMillionsBillions()),
    ).size.width
    xAxisData.forEachIndexed { index, dataPoint ->
        val textLayoutResult = textMeasure.measure(
            text = AnnotatedString(xAxisData[index].toString()),
        ).size.width
//        val yDataWidth :MutableList<Int> = emptyList<Int>().toMutableList()
//        val yAxisData = linesParameters.flatMap { it.data }
//        yAxisData.forEachIndexed { i ,value ->
//            yDataWidth[i] = textMeasure.measure(
//                text = AnnotatedString(value.toString()),
//            ).size.width
//        }

        val startSpace = (spacing) + (textLayoutResult).dp
        val spaceBetweenXes = (size.width - startSpace.toPx()) / (xAxisData.size - 1)

        val xLength = (yTextLayoutResult.dp) + (index * spaceBetweenXes).toDp()
        println(yTextLayoutResult)
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = dataPoint.toString(),
                style = xAxisStyle,
                maxLines = 1,
                topLeft = Offset(
                    xLength.coerceAtMost(size.width.toDp()).toPx(),
                    size.height / 1.07f
                )
            )
        }
    }
}