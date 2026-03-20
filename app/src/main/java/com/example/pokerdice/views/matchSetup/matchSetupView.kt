package com.example.pokerdice.views.matchSetup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokerdice.domain.game.GameMode
import com.example.pokerdice.ui.theme.AppTheme
import com.example.pokerdice.ui.theme.neonGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchSetupView(
    mode: GameMode,
    player1: String,
    player2: String,
    rounds: String,
    validPlayers: Boolean,
    validRounds: Boolean,
    onModeChange: (GameMode) -> Unit,
    onPlayer1Change: (String) -> Unit,
    onPlayer2Change: (String) -> Unit,
    onRoundsChange: (String) -> Unit,
    onStartMatch: () -> Unit,
    onCancel: () -> Unit
) {
    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text ="Match Setup",
                            color = neonGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                // Game Mode
                Text("Game Mode", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))

                Row {
                    RadioButton(
                        selected = mode == GameMode.HUMAN_VS_HUMAN,
                        onClick = { onModeChange(GameMode.HUMAN_VS_HUMAN) }
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Human vs Human", style = MaterialTheme.typography.bodyMedium)
                }

                Row {
                    RadioButton(
                        selected = mode == GameMode.HUMAN_VS_AI,
                        onClick = { onModeChange(GameMode.HUMAN_VS_AI) }
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Human vs AI", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(Modifier.height(16.dp))

                // Player Names
                OutlinedTextField(
                    value = player1,
                    onValueChange = onPlayer1Change,
                    label = { Text("Player 1 Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (mode == GameMode.HUMAN_VS_HUMAN) {
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = player2,
                        onValueChange = onPlayer2Change,
                        label = { Text("Player 2 Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Rounds
                OutlinedTextField(
                    value = rounds,
                    onValueChange = onRoundsChange,
                    label = { Text("Number of Rounds") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (!validRounds) {
                    Text(
                        text = "Rounds must be an odd number",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(containerColor = neonGreen)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = onStartMatch,
                        enabled = validRounds && validPlayers
                    ) {
                        Text("Start Match")
                    }
                }
            }
        }
    }
}