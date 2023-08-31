package com.aay.compose.radarChart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.aay.compose.radarChart.model.Polygon

internal fun DrawScope.drawPolygonShape(
    drawScope: DrawScope,
    polygon: Polygon,
    radius: Float,
    scalarValue: Double,
    center: Offset,
    scalarSteps: Int
) {
    val polygonEndPoints =
        getPolygonShapeEndPoints(polygon.values, radius, scalarValue, center, scalarSteps)
    val path = Path().apply {
        drawPolygon(polygonEndPoints)
    }

    drawScope.apply {
        drawPath(
            path,
            color = polygon.style.borderColor,
            style = Stroke(polygon.style.borderStrokeWidth),
            alpha = polygon.style.borderColorAlpha
        )
        drawPath(
            path,
            color = polygon.style.fillColor,
            style = Fill,
            alpha = polygon.style.fillColorAlpha
        )
    }
}

private fun Path.drawPolygon(polygonCorners: List<Offset>) {
    moveTo(polygonCorners[0].x, polygonCorners[0].y)
    polygonCorners.forEachIndexed { index, offset ->
        lineTo(offset.x, offset.y)
    }
    close()
}