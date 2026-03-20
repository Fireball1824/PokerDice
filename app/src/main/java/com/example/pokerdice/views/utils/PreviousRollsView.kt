package com.example.pokerdice.views.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokerdice.domain.game.PlayerRollResult
import com.example.pokerdice.views.game.DiceRow

@Composable
fun PreviousRollsView(
    results: List<PlayerRollResult>
) {
    if (results.isEmpty()) return
    Column {
        Text(
            text = "Previous Rolls",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            results.forEach { result ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = result.playerName,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(Modifier.height(8.dp))
                        DiceRow(
                            diceHand = result.dice,
                            holdIndices = emptySet(),
                            enabled = false,
                            onToggleHold = {}
                        )

                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Hand: ${result.handRank::class.simpleName}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}