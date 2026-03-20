package com.example.pokerdice.domain.stats

data class AiStats(
    val gamesPlayed: Int = 0,
    val gamesWon: Int = 0,
    val gamesLost: Int = 0
) {
    val winRate: Float
        get() = if (gamesPlayed == 0) 0f
        else gamesWon.toFloat() / gamesPlayed
}
