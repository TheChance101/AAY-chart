package com.aay.compose.radarChart


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

fun Path.drawPolygon(polygonCorners: List<Offset>) {
    moveTo(polygonCorners[0].x, polygonCorners[0].y)
    polygonCorners.forEachIndexed { index, offset ->
        lineTo(offset.x, offset.y)
    }
    close()
}