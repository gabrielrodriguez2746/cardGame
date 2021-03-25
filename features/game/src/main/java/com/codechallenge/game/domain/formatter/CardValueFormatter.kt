package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.CardValue
import javax.inject.Inject

class CardValueFormatter @Inject constructor() {

    fun CardValue.toIntValue(): Int {
        return when (this) {
            CardValue.ACE -> 13
            CardValue.KING -> 12
            CardValue.QUEEN -> 11
            CardValue.JACK -> 10
            CardValue.TEN -> 9
            CardValue.NINE -> 8
            CardValue.EIGHT -> 7
            CardValue.SEVEN -> 6
            CardValue.SIX -> 5
            CardValue.FIVE -> 4
            CardValue.FOUR -> 3
            CardValue.THREE -> 2
            CardValue.TWO -> 1
        }
    }

    fun CardValue.toLabel(): String {
        return when (this) {
            CardValue.ACE -> "A"
            CardValue.KING -> "K"
            CardValue.QUEEN -> "Q"
            CardValue.JACK -> "J"
            CardValue.TEN -> "10"
            CardValue.NINE -> "9"
            CardValue.EIGHT -> "8"
            CardValue.SEVEN -> "7"
            CardValue.SIX -> "6"
            CardValue.FIVE -> "5"
            CardValue.FOUR -> "4"
            CardValue.THREE -> "3"
            CardValue.TWO -> "2"
        }
    }

    fun Int.toCardValue(): CardValue {
        return when (this) {
            13 -> CardValue.ACE
            12 -> CardValue.KING
            11 -> CardValue.QUEEN
            10 -> CardValue.JACK
            9 -> CardValue.TEN
            8 -> CardValue.NINE
            7 -> CardValue.EIGHT
            6 -> CardValue.SEVEN
            5 -> CardValue.SIX
            4 -> CardValue.FIVE
            3 -> CardValue.FOUR
            2 -> CardValue.THREE
            1 -> CardValue.TWO
            else -> throw IllegalArgumentException("Not valid input to transform")
        }
    }
}
