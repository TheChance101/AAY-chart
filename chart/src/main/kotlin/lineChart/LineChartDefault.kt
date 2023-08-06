package lineChart

import androidx.compose.ui.graphics.Color
import lineChart.model.*

object LineChartDefault {

    private val lineParameters: LineParameters = LineParameters(
        dataName = "revenue",
        data = emptyList(),
        lineColor = Color.Blue,
        lineType = LineType.QUADRATIC_LINE,
        lineShadow = LineShadow.SHADOW,
    )
}