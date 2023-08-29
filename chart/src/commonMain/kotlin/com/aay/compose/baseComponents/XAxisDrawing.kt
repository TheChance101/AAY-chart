package com.aay.compose.baseComponents

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.aay.compose.utils.formatToThousandsMillionsBillions


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

        val startSpace = (spacing) + (textLayoutResult).toDp()
        val spaceBetweenXes =
            ((size.width - 32.dp.toPx()) - startSpace.toPx()) / (xAxisData.size - 1)

        val xLength = (yTextLayoutResult.toDp() + 32.dp) + (index * spaceBetweenXes).toDp()

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = dataPoint.toString(),
                style = xAxisStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                topLeft = Offset(
                    xLength.coerceAtMost(size.width.toDp()).toPx(),
                    size.height / 1.07f
                )
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
fun <T> DrawScope.xAxisDrawing(
    xAxisData: List<T>,
    height: Dp,
    textMeasure: TextMeasurer,
    xAxisStyle: TextStyle,
    specialChart: Boolean,
    xRegionWidth: Dp,
    xRegionWidthWithoutSpacing: Dp,
) {
    if (specialChart) {
        return
    }

    xAxisData.forEachIndexed { index, dataPoint ->


        val xLength =
            (xRegionWidthWithoutSpacing / 3) + (index * (xRegionWidth))
        println(size.height/20)

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = dataPoint.toString(),
                style = xAxisStyle,
                maxLines = 1,
                topLeft = Offset(
                    xLength.toPx().coerceAtMost(size.width),
                    (height.value + 20.dp.toPx()).coerceAtMost(size.height)
                )
            )
        }
    }
}