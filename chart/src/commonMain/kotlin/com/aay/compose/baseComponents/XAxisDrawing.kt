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
internal fun <T> DrawScope.xAxisDrawing(
    xAxisData: List<T>,
    textMeasure: TextMeasurer,
    xAxisStyle: TextStyle,
    specialChart: Boolean,
    upperValue: Float,
    xRegionWidth: Dp
) {
    if (specialChart) {
        return
    }
    val yTextLayoutResult = textMeasure.measure(
        text = AnnotatedString(upperValue.formatToThousandsMillionsBillions()),
    ).size.width
    val textSpace = yTextLayoutResult - (yTextLayoutResult/4)

    xAxisData.forEachIndexed { index, dataPoint ->
        val xLength = (textSpace.toDp()) + (index * xRegionWidth)

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
internal fun <T> DrawScope.xAxisDrawing(
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

        drawContext.canvas.nativeCanvas.apply {
            drawText(
                textMeasurer = textMeasure,
                text = dataPoint.toString(),
                style = xAxisStyle,
                maxLines = 1,
                topLeft = Offset(
                    xLength.toPx().coerceAtMost(size.width),
                    (height.value + 10.dp.toPx()).coerceAtMost(size.height)
                )
            )
        }
    }
}