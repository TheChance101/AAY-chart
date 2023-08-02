package chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.*

@Composable
fun TestAxesDrawing() {

    val lineParameters = LineParameters(
        dataName = "revenue",
        data = listOf(
            10000.0,
            20000.0,
            40000.0,
            50000.0,
            20000.0,
            70000.0,
            50000.0,
            90000.0,
        ),
        lineColor = Color.Blue,
        lineType = LineType.QUADRATIC_LINE,
        lineShadow = LineShadow.SHADOW
    )

    val xAxisList = listOf(
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Aug",
        "Sep",
        "Oct",
    )

    val lineParameters2 = LineParameters(
        dataName = "revenue",
        data = listOf(
            50000.0,
            40000.0,
            30000.0,
            50000.0,
            20000.0,
            90000.0,
            10000.0,
            90000.0,
        ),
        lineColor = Color.Red,
        lineType = LineType.QUADRATIC_LINE,
        lineShadow = LineShadow.BLANK
    )

    val chart = Chart(
        lines = listOf(lineParameters, lineParameters2),
        backGroundGrid = BackGroundGrid.SHOW,
        backGroundColor = Color.White,
        xAxisLabel = "month",
        yAxisLabel = "money",
        xAxisData = xAxisList
    )

    // for x or y this list?????
    val revenueData = listOf(
        Pair("Jan", 50000),
        Pair("Feb", 40000),
        Pair("Mar", 30000),
        Pair("Apr", 50000),
        Pair("May", 40000),
        Pair("Aug", 20000),
        Pair("Sep", 10000),
        Pair("Oct", 50000),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        AxesDrawing(
            modifier = Modifier.size(500.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp),
            linesParameters = chart.lines,
            xAxisData = chart.xAxisData,
        )
    }
}