package nl.turboa99.robitlife

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import nl.turboa99.robitlife.ui.theme.RoBitLifeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val gameViewModel: GameViewModel = viewModel()
            RoBitLifeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RobitNavigation(modifier = Modifier.padding(innerPadding), gameViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamPickerPreview() {
    RoBitLifeTheme {
        TeamPicker(modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun SituationPickerPreview() {
    RoBitLifeTheme(dynamicColor = false) {
        MainScreen(modifier = Modifier.fillMaxSize(), rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun NumberPickerPreview(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        NumberPicker(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(scrollState),
            onValueChange = {  }
        )
    }
}

