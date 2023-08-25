package com.aay.compose.radarChart.model

data class Polygon(
    val style: PolygonStyle,
    val values: List<Double>,
    val unit: String,
)