package com.aay.compose.utils

import androidx.compose.ui.unit.Dp
import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.lineChart.model.LineParameters

fun checkIfDataValid(
    xAxisData: List<String>,
    linesParameters: List<LineParameters> = emptyList(),
    barParameters: List<BarParameters> = emptyList()
) {
    if (linesParameters.isEmpty()) {
        val data = barParameters.map { it.data }
        data.forEach {
            if (it.size != xAxisData.size) {
                throw Exception("The data size of bar must be equal to the x-axis data size.")
            }
            checkIfDataIsNegative(it)
        }
    } else {
        val data = linesParameters.map { it.data }
        data.forEach {
            if (it.size != xAxisData.size) {
                throw Exception("The data size of line must be equal to the x-axis data size.")
            }
            checkIfDataIsNegative(it)
        }
    }
}

fun checkIfDataIsNegative(data: List<Double>) {
    data.forEach {
        if (it < 0.0) {
            throw Exception("The data can't contains negative values.")
        }
    }
}


fun checkIfSizeValid(size: Dp, minimumSize: Dp) {
    if (size <= minimumSize) {
        throw Exception("The size of chart must be more than or equal  minimum size: $minimumSize.")
    }
}
