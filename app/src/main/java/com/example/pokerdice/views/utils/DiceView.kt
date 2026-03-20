package com.example.pokerdice.views.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokerdice.domain.game.Game
import com.example.pokerdice.views.game.DiceRow

@Composable
fun DiceView(
    game: Game,
    onToggleHold: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Dice",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))
            DiceRow(
                diceHand = game.diceHand,
                holdIndices = game.holdIndices,
                enabled = game.rollsRemaining in 1..2,
                onToggleHold = onToggleHold
            )
        }
    }
}