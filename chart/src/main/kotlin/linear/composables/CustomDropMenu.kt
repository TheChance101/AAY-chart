package linear.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CustomDropDownHeader(
    selectedText: String, onSelectedTextChanged: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Week", "Month", "Year")
    val interactionSource = remember { MutableInteractionSource() }
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown


    Column(
        modifier = Modifier.padding(24.dp).wrapContentWidth()
    ) {

        OutlinedTextField(
            readOnly = true,
            value = selectedText,
            onValueChange = { newText -> onSelectedTextChanged(newText) },
            modifier = Modifier.width(111.dp).defaultMinSize(minHeight = 35.dp, minWidth = 108.dp),
            trailingIcon = {
                Icon(icon, "contentDescription", Modifier.clickable(
                    interactionSource = interactionSource, indication = null
                ) { expanded = !expanded })
            },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Black.copy(0.2f),
                textColor = Color.Black.copy(0.5f),
                focusedBorderColor = Color.Black.copy(0.1f)
            )
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    onSelectedTextChanged(label)
                    expanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
}
