package com.example.pokerdice.domain.game

import com.example.pokerdice.domain.dice.DiceHand
import com.example.pokerdice.domain.dice.Face
import com.example.pokerdice.domain.player.Player

data class Game(
    val configs: GameConfigs,
    val players: List<Player>,
    val round: Int = 1,
    val startingPlayerIndex: Int = 0,
    val currentPlayerIndex: Int = 0,
    val diceHand: DiceHand = DiceHand.roll(),
    val holdIndices: Set<Int> = emptySet(),
    val rollsRemaining: Int = 3,
    val playerResults: List<PlayerRollResult> = emptyList(),
    val allRounds: List<RoundResult> = emptyList(),
    val winnerPlayerId: Int? = null
)

data class PlayerRollResult(
    val playerId: Int,
    val playerName: String,
    val dice: DiceHand,
    val handRank: HandRank
)

data class RoundResult(
    val roundNumber: Int,
    val winnerPlayerId: Int? = null,
    val results: List<PlayerRollResult>
)

sealed class HandRank(open val weight: Int) {
    object FIVE_OF_A_KIND : HandRank(21)
    object FOUR_OF_A_KIND : HandRank(20)
    object FULL_HOUSE : HandRank(19)
    object STRAIGHT : HandRank(18)
    object THREE_OF_A_KIND : HandRank(17)
    object TWO_PAIR : HandRank(16)
    object ONE_PAIR : HandRank(15)
    data class Bust(val face: Face) : HandRank(face.rank)
}
