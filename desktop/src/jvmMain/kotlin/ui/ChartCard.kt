package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import composables.CustomDropDownHeader

@Composable
fun ChartCard(){
    var selectedText by remember { mutableStateOf("Month") }
    Column {
        CustomDropDownHeader(
            selectedText = selectedText,
            onSelectedTextChanged = { newSelectedText ->
                selectedText = newSelectedText
            }
        )
        Text(selectedText)
    }

}
