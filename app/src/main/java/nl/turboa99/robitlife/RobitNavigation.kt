package nl.turboa99.robitlife

import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobitNavigation(modifier: Modifier = Modifier, gameViewModel: GameViewModel = viewModel()) {
    val navController = rememberNavController()
    val gameState by gameViewModel.uiState.collectAsState()
    BackHandler(enabled = !gameState.showDialog) {

    }
    AnimatedVisibility(gameState.showDialog) {
        AlertDialog(
            onDismissRequest = {gameViewModel.showDialog(false)},
            title = { Text("Game Reset") },
            text = { Text("This will permanently reset your game progress. Are you sure you want to do that?", textAlign = TextAlign.Center)},
            icon = {Icon(Icons.Default.Warning, "", tint = MaterialTheme.colorScheme.error)},
            confirmButton = { TextButton(onClick = {
                gameViewModel.reset()
                gameViewModel.showDialog(false)
            })  {
                Text("Reset", color = MaterialTheme.colorScheme.error)
            }},
            dismissButton = {
                TextButton(onClick = {gameViewModel.showDialog(false)}) {
                    Text("Cancel")
                }
            }
        )
    }
    NavHost(navController, startDestination = HomeScreen, modifier = modifier) {
        composable<NumberPickerScreen> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                NumberPicker(
                    onValueChange = {
                        gameViewModel.selectSituation(it)
                        navController.navigateUp()
                    },
                    gameViewModel = gameViewModel
                )
            }
        }
        composable<HomeScreen> {
            MainScreen(navHostController = navController, gameViewModel = gameViewModel)
        }
    }
}

@Serializable
object HomeScreen

@Serializable
object NumberPickerScreen