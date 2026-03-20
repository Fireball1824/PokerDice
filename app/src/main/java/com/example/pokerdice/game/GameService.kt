package com.example.pokerdice.game

import com.example.pokerdice.domain.dice.DiceHand
import com.example.pokerdice.domain.dice.Face
import com.example.pokerdice.domain.game.HandRank
import com.example.pokerdice.domain.game.PlayerRollResult

class GameService {

    fun rollDice(currentHand: DiceHand, holdIndices: Set<Int>): DiceHand {
        return currentHand.reroll(holdIndices)
    }

    fun evaluateHand(hand: DiceHand): HandRank {
        val faces = hand.dice.map { it.face }
        val counts = faces.groupingBy { it }.eachCount().values.sortedDescending()

        return when {
            counts == listOf(5) -> HandRank.FIVE_OF_A_KIND
            counts == listOf(4, 1) -> HandRank.FOUR_OF_A_KIND
            counts == listOf(3, 2) -> HandRank.FULL_HOUSE
            isStraight(faces) -> HandRank.STRAIGHT
            counts == listOf(3, 1, 1) -> HandRank.THREE_OF_A_KIND
            counts == listOf(2, 2, 1) -> HandRank.TWO_PAIR
            counts == listOf(2, 1, 1, 1) -> HandRank.ONE_PAIR
            else -> HandRank.Bust(faces.maxByOrNull { it.rank }!!)
        }
    }

    private fun isStraight(faces: List<Face>): Boolean {
        val sorted = faces.map { it.rank }.sorted()
        return sorted.zipWithNext().all { (a, b) -> b - a == 1 }
    }

    fun compareHands(a: PlayerRollResult, b: PlayerRollResult): Int {
        return DiceHand.compareHands(a, b)
    }

    fun nextPlayer(currentIndex: Int) = 1 - currentIndex


    // MONTE CARLO AI
    fun aiDecideHold(hand: DiceHand, rollsRemaining: Int): Set<Int> {
        val simulations = 300
        val allHolds = generateAllHoldSets()
        var bestHold = emptySet<Int>()
        var bestScore = Double.MIN_VALUE
        for (hold in allHolds) {
            var totalScore = 0.0
            repeat(simulations) {
                var simulatedHand = hand
                var remaining = rollsRemaining
                var currentHold = hold
                while (remaining > 0) {
                    simulatedHand = simulatedHand.reroll(currentHold)
                    remaining--
                    if (remaining > 0) {
                        currentHold = greedyHold(simulatedHand)
                    }
                }
                val rank = evaluateHand(simulatedHand)
                totalScore += handScore(rank)
            }
            val avgScore = totalScore / simulations
            if (avgScore > bestScore) {
                bestScore = avgScore
                bestHold = hold
            }
        }
        return bestHold
    }


    private fun greedyHold(hand: DiceHand): Set<Int> {
        val faces = hand.dice.map { it.face }
        val groups = faces
            .mapIndexed { i, f -> i to f }
            .groupBy({ it.second }, { it.first })

        val counts = groups.mapValues { it.value.size }
        counts.entries.firstOrNull { it.value >= 3 }?.let {
            return groups[it.key]!!.toSet()
        }
        val pairs = counts.filterValues { it == 2 }
        if (pairs.isNotEmpty()) {
            return pairs.keys.flatMap { groups[it]!! }.toSet()
        }

        return hand.dice
            .mapIndexed { i, d -> i to d.face.rank }
            .sortedByDescending { it.second }
            .take(2)
            .map { it.first }
            .toSet()
    }


    private fun generateAllHoldSets(): List<Set<Int>> {
        val holds = mutableListOf<Set<Int>>()
        for (mask in 0 until 32) {
            val set = mutableSetOf<Int>()
            for (i in 0 until 5) {
                if (mask and (1 shl i) != 0) {
                    set.add(i)
                }
            }
            holds.add(set)
        }
        return holds
    }


    private fun handScore(rank: HandRank): Int =
        when (rank) {
            HandRank.FIVE_OF_A_KIND -> 100
            HandRank.FOUR_OF_A_KIND -> 80
            HandRank.FULL_HOUSE -> 60
            HandRank.STRAIGHT -> 50
            HandRank.THREE_OF_A_KIND -> 30
            HandRank.TWO_PAIR -> 20
            HandRank.ONE_PAIR -> 10
            is HandRank.Bust -> rank.weight
        }
}