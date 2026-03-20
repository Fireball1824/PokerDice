package com.example.pokerdice.repositories

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.pokerdice.domain.stats.AiStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "ai_stats")

class StatsRepository(private val context: Context) {

    private object Keys {
        val GAMES_PLAYED = intPreferencesKey("games_played")
        val GAMES_WON = intPreferencesKey("games_won")
        val GAMES_LOST = intPreferencesKey("games_lost")
    }

    val statsFlow: Flow<AiStats> = context.dataStore.data.map { prefs ->
        AiStats(
            gamesPlayed = prefs[Keys.GAMES_PLAYED] ?: 0,
            gamesWon = prefs[Keys.GAMES_WON] ?: 0,
            gamesLost = prefs[Keys.GAMES_LOST] ?: 0
        )
    }

    suspend fun updateStats(update: (AiStats) -> AiStats) {
        context.dataStore.edit { prefs ->
            val current = AiStats(
                gamesPlayed = prefs[Keys.GAMES_PLAYED] ?: 0,
                gamesWon = prefs[Keys.GAMES_WON] ?: 0,
                gamesLost = prefs[Keys.GAMES_LOST] ?: 0
            )

            val newStats = update(current)
            prefs[Keys.GAMES_PLAYED] = newStats.gamesPlayed
            prefs[Keys.GAMES_WON] = newStats.gamesWon
            prefs[Keys.GAMES_LOST] = newStats.gamesLost
        }
    }
}