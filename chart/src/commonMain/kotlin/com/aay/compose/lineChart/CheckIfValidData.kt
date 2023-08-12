package com.aay.compose.lineChart

import com.aay.compose.lineChart.model.LineParameters

fun checkIfDataValid(
    xAxisData:List<String>,
    linesParameters: List<LineParameters>,
){
    val data = linesParameters.map { it.data }
    data.forEach {
        if (it.size != xAxisData.size ){
            throw Exception(" The data size of lines must be equal to the x-axis data size")
        }
        it.forEach {
            if (it < 0.0){
                throw Exception("The data can't contains negative values")
            }
        }
    }
}