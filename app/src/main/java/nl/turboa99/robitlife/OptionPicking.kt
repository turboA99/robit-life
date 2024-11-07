package nl.turboa99.robitlife

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OptionPicking(modifier: Modifier = Modifier, gameViewModel: GameViewModel = viewModel()) {
    val gameState by gameViewModel.uiState.collectAsState()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        gameViewModel.getSituation(gameState.selectedSituation).options.forEach {
            FilledTonalButton(
                onClick = {
                    gameViewModel.vote(role = gameState.currentRole!!, option = it)
                    gameViewModel.setGameStatus(GameStatus.TeamPicking)
                },
                modifier = Modifier.Companion
                    .fillMaxHeight()
                    .weight(1f)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    it.char.toString(),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    }
}