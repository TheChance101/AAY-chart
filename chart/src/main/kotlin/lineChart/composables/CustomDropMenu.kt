package lineChart.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.comcus.composables.CustomIcon
import com.example.comcus.composables.CustomText

@Composable
fun<T> CustomDropdown(
    options: List<T>,
    onOptionSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options.firstOrNull()) }
    val icon = if (expanded) painterResource("keyboard_arrow_down.svg")
    else painterResource("keyboard_arrow_up.svg")

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(4.dp)
        ) {
            Text(
                text = selectedOption.toString() ?: "Select an option",
                modifier = Modifier.align(Alignment.CenterStart)
            )
            CustomIcon(
                painter = icon ,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray,RoundedCornerShape(8.dp))
            ) {
                options.forEach { option ->
                    CustomText(
                        text = option.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOption = option
                                expanded = false
                                onOptionSelected(option)
                            }
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                }
            }
        }
    }
}
@Preview
@Composable
fun DropdownPreview(){
    CustomDropdown<String>(options =listOf("2019","2020","2030"),
        onOptionSelected ={} )
}