package com.example.pokerdice.views.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokerdice.game.GameViewModel
import com.example.pokerdice.views.utils.ActionsView
import com.example.pokerdice.views.utils.DiceView
import com.example.pokerdice.views.utils.HeaderView
import com.example.pokerdice.views.utils.PreviousRollsView

@Composable
fun PlayingView(
    uiState: GameViewModel.GameUiState.Playing,
    onRollDice: () -> Unit,
    onEndTurn: () -> Unit,
    onToggleHold: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val game = uiState.game
    val currentPlayer = game.players[game.currentPlayerIndex]

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HeaderView(
            round = game.round,
            totalRounds = game.configs.rounds,
            playerName = currentPlayer.name,
            rollsRemaining = game.rollsRemaining
        )

        Spacer(Modifier.height(16.dp))
        DiceView(
            game = game,
            onToggleHold = onToggleHold
        )

        Spacer(Modifier.height(16.dp))
        PreviousRollsView(uiState.results)

        Spacer(Modifier.weight(1f))
        ActionsView(
            game = game,
            onRollDice = onRollDice,
            onEndTurn = onEndTurn
        )
    }
}