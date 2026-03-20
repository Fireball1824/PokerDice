package com.example.pokerdice.domain.game

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GameMode: Parcelable {
    HUMAN_VS_HUMAN,
    HUMAN_VS_AI
}