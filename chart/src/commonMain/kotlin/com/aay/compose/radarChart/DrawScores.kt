package com.aay.compose.radarChart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawScores(
    points: List<Offset>,
    values: List<Double>,
    textStyle: TextStyle,
    textMeasurer: TextMeasurer,
    unit: String
) {
    val centerY = size.height / 2f

    points.forEachIndexed { index, point: Offset ->
        val score = "${values[index]} $unit"
        val textOffset = 15.toDp().toPx()
        val isBottomHalf = point.y > centerY
        val textLayoutResult = textMeasurer.measure(
            text = AnnotatedString(score),
            style = textStyle
        )
        val textPosition = Offset(
            x = point.x - textLayoutResult.size.width / 2f,
            y = if (isBottomHalf) point.y + textOffset
            else point.y - textLayoutResult.size.height - textOffset
        )

        drawText(
            textMeasurer = textMeasurer,
            text = score,
            style = textStyle,
            topLeft = textPosition
        )
    }
}