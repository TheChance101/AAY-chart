package linear

import androidx.compose.ui.graphics.Color
import linear.model.*

object ChartDefault {

    private val lineParameters: LineParameters = LineParameters(
        dataName = "revenue",
        data = emptyList(),
        lineColor = Color.Blue,
        lineType = LineType.QUADRATIC_LINE,
        lineShadow = LineShadow.SHADOW,
    )

    val chart: Chart = Chart(
        lines = listOf(lineParameters),
        backGroundGrid = BackGroundGrid.SHOW,
        backGroundColor = Color.Black,
        xAxisLabel = "month",
        yAxisLabel = "money",
        xAxisData = emptyList()
    )
}