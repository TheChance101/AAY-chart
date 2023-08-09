package com.aay.compose.lineChart2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.aay.compose.lineChart2.model.LineParameters

@Composable
fun ChartDescription(
    chartLineDetails: List<LineParameters> = emptyList(),
    descriptionStyle: TextStyle
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
        items(chartLineDetails) { line ->
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = line.lineColor)
                        .size(8.dp)
                )
                Text(
                    text = line.dataName,
                    modifier = Modifier.padding(start = 10.dp),
                    style = descriptionStyle
                )
            }
        }
    }
}