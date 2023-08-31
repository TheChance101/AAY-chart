package com.aay.compose.lineChart.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp


internal fun DrawScope.chartCircle(
    x: Float,
    y: Float,
    color: Color,
    animatedProgress: Animatable<Float, AnimationVector1D>,
    stroke: Stroke? = null,
) {
    clipRect(right = size.width * animatedProgress.value) {
        if (stroke == null) {
            drawCircle(
                color = color,
                radius = 5.dp.toPx(),
                center = Offset(x, y),
            )
        } else {
            drawCircle(
                color = color,
                radius = 5.dp.toPx(),
                center = Offset(x, y),
                style = stroke
            )
        }
    }
}