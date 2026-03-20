package com.example.pokerdice.views.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun GameEndView(
    winner: String?,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Game Finished!",
            style = MaterialTheme.typography.headlineLarge,
            color = White,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))
        Text(
            text = winner?.let { "Winner: $it" } ?: "It's a Draw!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(40.dp))
        Button(
            onClick = onFinish
        ) {
            Text("Back to Menu")
        }
    }
}