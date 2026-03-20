package com.example.pokerdice.matchSetup

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokerdice.domain.game.GameConfigs
import com.example.pokerdice.domain.game.GameMode
import com.example.pokerdice.ui.theme.AppTheme
import com.example.pokerdice.views.matchSetup.MatchSetupView

@Composable
fun MatchSetupScreen(
    onStartMatch: (GameConfigs) -> Unit,
    onCancel: () -> Unit
) {
    var mode by remember { mutableStateOf(GameMode.HUMAN_VS_HUMAN) }
    var player1 by remember { mutableStateOf("") }
    var player2 by remember { mutableStateOf("") }
    var rounds by remember { mutableStateOf("") }
    val roundsInt = rounds.toIntOrNull()
    val validPlayers = player1.isNotEmpty() && player2.isNotEmpty() && player1 != player2
    val validRounds = roundsInt != null && roundsInt % 2 == 0 && roundsInt <= 10

    if (mode == GameMode.HUMAN_VS_AI){
        player2 = "AI"
    }

    AppTheme {
        MatchSetupView(
            mode = mode,
            player1 = player1,
            player2 = player2,
            rounds = rounds,
            validPlayers = validPlayers,
            validRounds = validRounds,
            onModeChange = { mode = it },
            onPlayer1Change = { player1 = it },
            onPlayer2Change = { player2 = it },
            onRoundsChange = { rounds = it },
            onStartMatch = {
                    onStartMatch(
                        GameConfigs(
                            player1,
                            player2,
                            roundsInt!!,
                            mode,
                        )
                    )
            },
            onCancel = onCancel
        )
    }
}

@Preview
@Composable
fun MatchSetupScreenPreview() {
    AppTheme {
        MatchSetupScreen(
            onStartMatch = {},
            onCancel = {}
        )
    }
}