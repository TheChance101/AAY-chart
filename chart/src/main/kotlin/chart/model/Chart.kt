package chart.model

import androidx.compose.ui.graphics.Color

data class Chart(
    val lines: List<LineParameters>,
    val backGroundGrid: BackGroundGrid,
    val backGroundColor: Color,
    val xAxisLabel: String,
    val yAxisLabel: String,
    val xAxisData: List<String>
)

enum class BackGroundGrid {
    SHOW,
    BLANK,
}