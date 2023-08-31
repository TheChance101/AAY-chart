package com.aay.compose.donutChart.component

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

internal fun DrawScope.drawLines(
    ratioLineColor: Color,
    lineStart: Offset,
    lineEnd: Offset,
    secondLineEnd: Offset,
) {
    drawLine(
        color = ratioLineColor,
        start = lineStart,
        end = lineEnd,
        strokeWidth = 2.dp.toPx()
    )
    drawLine(
        color = ratioLineColor,
        start = lineEnd,
        end = secondLineEnd,
        strokeWidth = 2.dp.toPx()
    )
}