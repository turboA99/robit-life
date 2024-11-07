package nl.turboa99.robitlife

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SituationPicking(modifier: Modifier = Modifier, gameViewModel: GameViewModel = viewModel()) {
    Surface(modifier = modifier, tonalElevation = 2.dp) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "After picking the situation press button to start a timer for discussion",
                modifier = Modifier.padding(
                    horizontal = 30.dp,
                    vertical = 10.dp
                ),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Button(onClick = {
                gameViewModel.setGameStatus(GameStatus.Timer)
            }) {
                Text("Start Timer")
            }
        }
    }
}