package com.aay.compose.donutChart.component

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.ratioText(
    textMeasurer: TextMeasurer,
    ratio: Int,
    textRatioStyle: TextStyle,
    topLeft: Offset,
) {
    drawContext.canvas.nativeCanvas.apply {
        drawText(
            textMeasurer = textMeasurer,
            text = "${ratio}%",
            style = textRatioStyle,
            topLeft = topLeft,
            overflow = TextOverflow.Visible
        )
    }
}