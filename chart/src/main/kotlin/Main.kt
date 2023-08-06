import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import lineChart.composables.CustomDropdown

@Composable
@Preview
fun App() {
    MaterialTheme {
        CustomDropdown<String>(listOf("a", "b", "c")) {

        }
    }
}