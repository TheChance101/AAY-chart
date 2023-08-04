package chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composables.CustomDropDownHeader
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

    )

    val lineParameters2 = LineParameters(
        dataName = "revenue",
        data = listOf(
            50000.0,
            40000.0,
            30000.0,
            50000.0,
            20000.0,
            30000.0,
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
    var selectedText by remember { mutableStateOf("Month") }
    Box(modifier = Modifier.padding(16.dp)){
        Card(modifier = Modifier.wrapContentSize()
            .background(MaterialTheme.colors.background),
            shape = RoundedCornerShape(16.dp)
        ){ Column(modifier = Modifier.width(1000.dp).padding(16.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
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
                }
                AxesDrawing(
                    modifier = Modifier.height(400.dp).width(1000.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    linesParameters = chart.lines,
                    xAxisData = chart.xAxisData,
                )
            }

        }
    }
}