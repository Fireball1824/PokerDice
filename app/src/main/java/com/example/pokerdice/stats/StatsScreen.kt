package com.example.pokerdice.stats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import com.example.pokerdice.domain.stats.AiStats
import com.example.pokerdice.repositories.StatsRepository
import com.example.pokerdice.ui.theme.AppTheme
import com.example.pokerdice.views.stats.StatsView

@Composable
fun StatsScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val repo = remember { StatsRepository(context) }
    val stats by repo.statsFlow.collectAsState(initial = AiStats())

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            StatsView(
                stats = stats,
                onBack = onBack
            )
        }
    }
}