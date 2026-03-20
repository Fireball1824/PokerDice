package com.example.pokerdice.domain.game

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameConfigs(
    val player1: String,
    val player2: String,
    val rounds: Int,
    val mode: GameMode,
): Parcelable
