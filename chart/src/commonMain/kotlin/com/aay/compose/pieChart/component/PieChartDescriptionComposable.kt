package com.aay.compose.pieChart.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.aay.compose.baseComponents.ChartDescription
import com.aay.compose.pieChart.model.PieChartData

@Composable
internal fun PieChartDescriptionComposable(
    pieChartData: List<PieChartData>,
    descriptionStyle: TextStyle = TextStyle.Default
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 16.dp, top = 50.dp),
        contentPadding = PaddingValues(16.dp)
    ) {

        items(pieChartData) { details ->
            ChartDescription(
                chartColor = details.color,
                chartName = details.partName,
                descriptionStyle = descriptionStyle,
            )
        }
    }
}