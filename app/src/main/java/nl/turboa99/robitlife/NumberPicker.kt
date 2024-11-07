package nl.turboa99.robitlife

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
    gameViewModel: GameViewModel = viewModel()
) {
    val gameState by gameViewModel.uiState.collectAsState()
    val haptic = LocalHapticFeedback.current
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        for (value in IntRange(0, gameViewModel.situationCount - 1)) {
            if (gameState.selectedSituation == value) {
                Button(
                    onClick = {},
                    modifier = Modifier.Companion.size(40.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("${gameViewModel.getId(value)}")
                }
            } else {
                FilledTonalButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.Companion.TextHandleMove)
                        onValueChange(value)
                    },
                    modifier = Modifier.Companion.size(40.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("${gameViewModel.getId(value)}")
                }
            }
        }
    }
}