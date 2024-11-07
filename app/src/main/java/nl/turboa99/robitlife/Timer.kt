package nl.turboa99.robitlife

import android.media.RingtoneManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun Timer(modifier: Modifier = Modifier, gameViewModel: GameViewModel = viewModel()) {
    val startTime = System.currentTimeMillis()
    val targetTime = startTime + 2 * 60 * 30
    var remainingTime by remember { mutableLongStateOf(targetTime - startTime) }
    val ringtone = RingtoneManager.getRingtone(
        LocalContext.current,
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
    )
    LaunchedEffect(Unit) {
        while (System.currentTimeMillis() < targetTime) {
            remainingTime = targetTime - System.currentTimeMillis()
            delay(20)
        }
        remainingTime = 0L
        ringtone.play()
        delay(5000)
        ringtone.stop()
    }
    DisposableEffect(Unit) {
        onDispose {
            if (ringtone.isPlaying) ringtone.stop()
        }
    }

    Surface(modifier = modifier, tonalElevation = 2.dp) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Companion.CenterHorizontally
        ) {
            Text(
                "${remainingTime / 1000L / 60L} : ${
                    String.Companion.format(
                        Locale.ROOT,
                        "%02d",
                        remainingTime / 1000L % 60L
                    )
                } : ${
                    String.Companion.format(
                        Locale.ROOT,
                        "%03d",
                        remainingTime % 1000L
                    )
                }",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(bottom = 10.dp),
                fontWeight = FontWeight.Companion.Bold
            )
            AnimatedVisibility(remainingTime == 0L) {
                Button(onClick = {
                    gameViewModel.setGameStatus(GameStatus.TeamPicking)
                }) {
                    Text("Vote")
                }
            }
        }
    }
}