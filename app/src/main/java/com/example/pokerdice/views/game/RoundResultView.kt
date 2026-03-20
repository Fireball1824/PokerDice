package com.example.pokerdice.views.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokerdice.domain.game.Game
import com.example.pokerdice.domain.game.RoundResult

@Composable
fun RoundResultView(
    roundResult: RoundResult,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Round ${roundResult.roundNumber} Results", fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))

        roundResult.results.forEach {
            Text("${it.playerName}: ${it.handRank::class.simpleName}")
        }

        Spacer(Modifier.height(16.dp))
        val winner = roundResult.winnerPlayerId?.let { id -> roundResult.results.find { it.playerId == id } }
        Text(
            text = "Winner of this round is: ${winner?.playerName ?: "Draw"}",
            fontSize = 20.sp
        )
    }
}