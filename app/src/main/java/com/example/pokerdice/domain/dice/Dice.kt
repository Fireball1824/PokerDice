package com.example.pokerdice.domain.dice


import com.example.pokerdice.domain.game.HandRank
import com.example.pokerdice.domain.game.PlayerRollResult

data class Dice(val face: Face) : Comparable<Dice> {
    companion object {
        fun roll(): Dice = Dice(Face.entries.random())
    }

    override fun compareTo(other: Dice): Int = this.face.rank - other.face.rank

    override fun toString(): String = face.toString()
}

data class DiceHand(val dice: List<Dice>) {
    companion object {
        fun roll(): DiceHand = DiceHand(List(5) { Dice.roll() })

        fun compareHands(a: PlayerRollResult, b: PlayerRollResult): Int {
            val aRank = a.handRank.weight
            val bRank = b.handRank.weight
            if (aRank != bRank) return aRank.compareTo(bRank)
            val aFaces = a.dice.faces().map { it.rank }.sortedDescending()
            val bFaces = b.dice.faces().map { it.rank }.sortedDescending()

            // special case: both busts
            if (a.handRank is HandRank.Bust || b.handRank is HandRank.Bust) {
                for (i in 0 until 5) {
                    val cmp = aFaces[i].compareTo(bFaces[i])
                    if (cmp != 0) return cmp
                }
                return 0
            }

            return when (a.handRank) {
                HandRank.FIVE_OF_A_KIND,
                HandRank.FOUR_OF_A_KIND,
                HandRank.THREE_OF_A_KIND -> {
                    val aGroup = aFaces.groupingBy { it }.eachCount().maxByOrNull { it.value }!!.key
                    val bGroup = bFaces.groupingBy { it }.eachCount().maxByOrNull { it.value }!!.key
                    aGroup.compareTo(bGroup)
                }
                HandRank.FULL_HOUSE -> {
                    val aTriple = aFaces.groupingBy { it }.eachCount().filterValues { it == 3 }.keys.first()
                    val bTriple = bFaces.groupingBy { it }.eachCount().filterValues { it == 3 }.keys.first()
                    val cmpTriple = aTriple.compareTo(bTriple)
                    if (cmpTriple != 0) cmpTriple else {
                        val aPair = aFaces.groupingBy { it }.eachCount().filterValues { it == 2 }.keys.first()
                        val bPair = bFaces.groupingBy { it }.eachCount().filterValues { it == 2 }.keys.first()
                        aPair.compareTo(bPair)
                    }
                }
                else -> {
                    // straight, two pair, one pair
                    for (i in 0 until 5) {
                        val cmp = aFaces[i].compareTo(bFaces[i])
                        if (cmp != 0) return cmp
                    }
                    0
                }
            }
        }
    }

    fun reroll(holdIndices: Set<Int> = emptySet()): DiceHand {
        val newDice =
            dice.mapIndexed { i, d ->
                if (i in holdIndices) d else Dice.roll()
            }
        return DiceHand(newDice)
    }

    fun faces(): List<Face> = dice.map { it.face }
}