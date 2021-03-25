package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.domain.model.PlayerCardSuit
import javax.inject.Inject

class SuitsCardFormatter @Inject constructor() {

    fun CardSuit.toPlayerCardSuit(): PlayerCardSuit {
        return when (this) {
            CardSuit.CLUB -> PlayerCardSuit.Club
            CardSuit.DIAMOND -> PlayerCardSuit.Diamond
            CardSuit.HEARTS -> PlayerCardSuit.Hearts
            CardSuit.SPADES -> PlayerCardSuit.Spades
        }
    }

    fun PlayerCardSuit.toCardSuit(): CardSuit {
        return when (this) {
            PlayerCardSuit.Club -> CardSuit.CLUB
            PlayerCardSuit.Diamond -> CardSuit.DIAMOND
            PlayerCardSuit.Hearts -> CardSuit.HEARTS
            PlayerCardSuit.Spades -> CardSuit.SPADES
        }
    }
}
