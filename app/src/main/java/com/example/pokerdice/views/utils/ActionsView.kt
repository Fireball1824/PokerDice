package com.example.pokerdice.views.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokerdice.domain.game.Game

@Composable
fun ActionsView(
    game: Game,
    onRollDice: () -> Unit,
    onEndTurn: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onRollDice,
            enabled = game.rollsRemaining > 0,
            modifier = Modifier.weight(1f)
        ) {
            Text("Roll")
        }
        Button(
            onClick = onEndTurn,
            enabled = game.rollsRemaining < 3,
            modifier = Modifier.weight(1f)
        ) {
            Text("End Turn")
        }
    }
}