package com.example.pokerdice.domain.player

data class Player(
    val id: Int,
    val name: String,
    val gamesPlayed: Int = 0,
    val gamesWon: Int = 0
)