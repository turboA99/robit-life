package nl.turboa99.robitlife

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun WinnerScreen(modifier: Modifier = Modifier, gameViewModel: GameViewModel = viewModel()) {
    val gameState by gameViewModel.uiState.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Winner: ${gameState.lastWinner!!.char}")
    }
}
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    gameViewModel: GameViewModel = viewModel()
) {
    val gameState by gameViewModel.uiState.collectAsState()
    Column(
        modifier = modifier.padding(top = 10.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    onClick = { gameViewModel.showDialog(true) },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Icon(Icons.Default.Refresh, "Reset")
                }
            }
            Row(modifier = Modifier.clip(CircleShape)) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        "Situation",
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 15.dp
                        )
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                FilledTonalButton(
                    modifier = Modifier,
                    onClick = {
                        navHostController.navigate(NumberPickerScreen)
                    },
                    enabled = gameState.gameStatus == GameStatus.SituationPicking,
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "${gameViewModel.getId(gameState.selectedSituation)}",
                        modifier = Modifier.padding(15.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
        val navViewModifier: Modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)

        Box(modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .clip(MaterialTheme.shapes.extraLarge)
            .clipToBounds())
        {
            AnimatedContent(gameState, transitionSpec = {
                (fadeIn(tween(200)) + slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200),
                    initialOffset = { it / 3 }
                )).togetherWith(
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(200),
                        targetOffset = {
                            it / 3
                        }
                    )
                )
            }) {
                when (it.gameStatus) {
                    GameStatus.SituationPicking -> {
                        SituationPicking(modifier = navViewModifier, gameViewModel)
                    }

                    GameStatus.TeamPicking -> {
                        TeamPicker(modifier = navViewModifier, gameViewModel)
                    }

                    GameStatus.OptionPicking -> {
                        OptionPicking(modifier = navViewModifier, gameViewModel)
                    }

                    GameStatus.Timer -> {
                        Timer(modifier = navViewModifier, gameViewModel)
                    }

                    GameStatus.ShowWinner -> {

                    }
                }
            }
        }
    }
}

