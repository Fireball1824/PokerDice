package com.example.pokerdice.views.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokerdice.domain.dice.DiceHand
import com.example.pokerdice.game.GameViewModel
import com.example.pokerdice.ui.theme.AppTheme
import com.example.pokerdice.ui.theme.neonGreen

@Composable
fun GameView(
    onRollDice: () -> Unit,
    onToggleHold: (Int) -> Unit,
    onEndTurn: () -> Unit,
    onGameEnd: (winner: String) -> Unit,
    modifier: Modifier = Modifier,
    uiState: GameViewModel.GameUiState,
) {
    AppTheme {
        when (uiState) {
            is GameViewModel.GameUiState.RoundResultState -> {
                RoundResultView(
                    uiState.roundResult,
                    modifier = modifier.fillMaxSize()
                )
            }

            is GameViewModel.GameUiState.MatchEnded -> {
                GameEndView(
                    winner = uiState.winner?.name,
                    onFinish = { onGameEnd(uiState.winner?.name ?: "Draw") },
                    modifier = modifier.fillMaxSize()
                )
            }

            is GameViewModel.GameUiState.Playing -> {
                PlayingView(
                    uiState = uiState,
                    onRollDice = onRollDice,
                    onToggleHold = onToggleHold,
                    onEndTurn = onEndTurn,
                    modifier = modifier.fillMaxSize()
                )
            }

            is GameViewModel.GameUiState.Loading -> {
                LoadingView(uiState.message)
            }
        }
    }
}

@Composable
fun DiceRow(
    diceHand: DiceHand,
    holdIndices: Set<Int>,
    enabled: Boolean,
    onToggleHold: (Int) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        diceHand.dice.forEachIndexed { idx, dice ->
            val held = idx in holdIndices
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = if (held) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(enabled = enabled) { onToggleHold(idx) },
                contentAlignment = Alignment.Center
            ) {
                Text(dice.toString(), fontSize = 24.sp)
            }
        }
    }
}