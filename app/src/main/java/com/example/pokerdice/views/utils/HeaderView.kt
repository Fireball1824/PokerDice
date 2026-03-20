package com.example.pokerdice.views.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun HeaderView(
    round: Int,
    totalRounds: Int,
    playerName: String,
    rollsRemaining: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Round $round / $totalRounds",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "$playerName's Turn",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Rolls remaining: $rollsRemaining",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}