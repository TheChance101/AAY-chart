package lineChart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lineChart.composables.CustomDropDownHeader
import lineChart.model.*

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
        "Aug"
    )

    val lineParameters2 = LineParameters(
        dataName = "revenue",
        data = listOf(
            50000.0,
            70000.0,
            50000.0,
            60000.0,
            50000.0,
            20000.0,
        ),
        lineColor = Color.Red,
        lineType = LineType.QUADRATIC_LINE,
        lineShadow = LineShadow.BLANK
    )

    val chartMonth = Chart(
        lines = listOf(lineParameters, lineParameters2),
        backGroundGrid = BackGroundGrid.SHOW,
        backGroundColor = Color.White,
        xAxisLabel = "month",
        yAxisLabel = "money",
        xAxisData = xAxisList
    )
    val lineParameters3 = LineParameters(
        dataName = "revenue",
        data = listOf(
            20000.0,
            50000.0,
            70000.0,
            80000.0,
            50000.0,
            30000.0
        ),
        lineColor = Color.Blue,
        lineType = LineType.QUADRATIC_LINE,
        lineShadow = LineShadow.SHADOW
    )

    val xAxisListYear = listOf(
        "2015",
        "2016",
        "2017",
        "2018",
        "2019",
        "2020"
    )
    val lineParameters4 = LineParameters(
        dataName = "revenue",
        data = listOf(
            80000.0,
            40000.0,
            90000.0,
            50000.0,
            80000.0,
            50000.0,
        ),
        lineColor = Color.Red,
        lineType = LineType.QUADRATIC_LINE,
        lineShadow = LineShadow.BLANK
    )

    val chartYear = Chart(
        lines = listOf(lineParameters3, lineParameters4),
        backGroundGrid = BackGroundGrid.SHOW,
        backGroundColor = Color.White,
        xAxisLabel = "Year",
        yAxisLabel = "money",
        xAxisData = xAxisListYear
    )
    val lineParameters6 = LineParameters(
        dataName = "Earnings",
        data = listOf(
            30000.0,
            20000.0,
            80000.0,
            50000.0,
            70000.0,
            40000.0,


            ),
        lineColor = Color.Blue,
        lineType = LineType.QUADRATIC_LINE,
        lineShadow = LineShadow.SHADOW
    )

    val xAxisListWeek = listOf(
        "week1",
        "week2",
        "week3",
        "week4",
        "week5",
        "week6",

        )
    val lineParameters5 = LineParameters(
        dataName = "revenue",
        data = listOf(
            50000.0,
            20000.0,
            30000.0,
            80000.0,
            80000.0,
            40000.0,
        ),
        lineColor = Color.Red,
        lineType = LineType.QUADRATIC_LINE,
        lineShadow = LineShadow.BLANK
    )

    val colorHeaderList = listOf(lineParameters5.lineColor,lineParameters6.lineColor)
    val titleHeaderList = listOf(lineParameters5.dataName, lineParameters6.dataName)

    val chartWeak = Chart(
        lines = listOf(lineParameters5, lineParameters6),
        backGroundGrid = BackGroundGrid.SHOW,
        backGroundColor = Color.White,
        xAxisLabel = "Year",
        yAxisLabel = "money",
        xAxisData = xAxisListWeek
    )


    var selectedText by remember { mutableStateOf("Month") }
    var chartSelected by remember { mutableStateOf(chartMonth) }

    Card(
        modifier = Modifier.wrapContentSize()
            .padding(16.dp)
            .background(MaterialTheme.colors.background, shape = RoundedCornerShape(16.dp)),
        border = BorderStroke(width = 1.dp, color = Color.Transparent),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("Revenue & Sales", fontSize = 20.sp)

                CustomDropDownHeader(
                    selectedText = selectedText,
                    onSelectedTextChanged = { newSelectedText ->
                        selectedText = newSelectedText
                    }
                )

                chartSelected = when (selectedText) {
                    "Week" -> chartWeak
                    "Year" -> chartYear
                    else -> {
                        chartMonth
                    }
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,

            ) {
                items(colorHeaderList.size) {
                    Box(modifier = Modifier.padding(end = 10.dp)){
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(shape = CircleShape)
                                .background(color = colorHeaderList[it])
                        )
                    }
                    Text(titleHeaderList[it])
                }
            }

            LineChart(
                modifier = Modifier.height(400.dp).width(1000.dp),
                linesParameters = chartSelected.lines,
                xAxisData = chartSelected.xAxisData,
            )
        }

    }

}