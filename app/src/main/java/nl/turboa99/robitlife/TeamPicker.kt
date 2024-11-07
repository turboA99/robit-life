package nl.turboa99.robitlife

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun TeamPicker(modifier: Modifier = Modifier, gameViewModel: GameViewModel = viewModel()) {
    val gameState by gameViewModel.uiState.collectAsState()
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Role.entries.forEach { role ->
                val voted = gameState.currentVotes[role.ordinal] != null
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            gameViewModel.setCurrentRole(role)
                            gameViewModel.setGameStatus(GameStatus.OptionPicking)
                        }, tonalElevation = 2.dp, shape = MaterialTheme.shapes.large,
                    color = if (voted) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.surface,
                    contentColor = if (voted) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSurface
                ) {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Companion.Center
                    ) {
                        Text(role.textName)
                    }
                }
            }
        }
        if(gameState.currentVotes.all { it != null }) {
            Button(
                onClick = {
                    gameViewModel.countVotes()
                    gameViewModel.setGameStatus(GameStatus.ShowWinner)
                },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text("Finish Voting", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 10.dp))
                Icon(Icons.AutoMirrored.Default.ArrowForward, "")
            }
        }
    }
}