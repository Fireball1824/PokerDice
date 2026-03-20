package com.example.pokerdice.domain.dice


enum class Face(val rank: Int) {
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14),
    ;

    override fun toString() =
        when (this) {
            NINE -> "9"
            TEN -> "10"
            JACK -> "J"
            QUEEN -> "Q"
            KING -> "K"
            ACE -> "A"
        }
}