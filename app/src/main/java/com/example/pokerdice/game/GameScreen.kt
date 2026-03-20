package com.example.pokerdice.game

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.pokerdice.ui.theme.AppTheme
import com.example.pokerdice.views.game.GameView
import kotlinx.coroutines.delay

const val showRoundScreenTime = 10_000L

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: GameViewModel,
    onGameEnd: (winner: String) -> Unit
) {
    val gameState = viewModel.uiState.collectAsState().value

    if (gameState is GameViewModel.GameUiState.RoundResultState) {
        LaunchedEffect(gameState.roundResult.roundNumber) {
            delay(showRoundScreenTime)
            viewModel.startNextRound()
        }
    }
    AppTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Poker Dice") }) }
        ) { padding ->

            GameView(
                onRollDice = { viewModel.rollDice() },
                onToggleHold = { viewModel.toggleHold(it) },
                onEndTurn = { viewModel.endTurn() },
                onGameEnd = onGameEnd,
                modifier = Modifier.padding(padding),
                uiState = gameState
            )
        }
    }
}