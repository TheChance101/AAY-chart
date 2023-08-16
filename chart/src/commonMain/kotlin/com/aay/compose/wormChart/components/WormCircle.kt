package com.aay.compose.wormChart.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp


fun DrawScope.wormCircle(x: Float, y: Float) {
    drawCircle(
        color = Color.Red,
        radius = 5.dp.toPx(),
        center = Offset(x, y)
    )
}