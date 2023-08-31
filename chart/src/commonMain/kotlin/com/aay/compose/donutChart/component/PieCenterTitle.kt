package com.aay.compose.donutChart.component

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.IntSize

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawCenterText(
    textMeasure: TextMeasurer,
    centerTitle: String,
    centerTitleStyle: TextStyle,
    canvasHeight: Float,
    canvasWidth: Float,
    textSize: IntSize
) {
    drawContext.canvas.nativeCanvas.apply {
        drawText(
            textMeasurer = textMeasure,
            text = centerTitle.take(10),
            style = centerTitleStyle,
            topLeft = Offset(
                (canvasWidth - textSize.width) / 2f,
                (canvasHeight - textSize.height) / 2f
            ),
        )
    }
}