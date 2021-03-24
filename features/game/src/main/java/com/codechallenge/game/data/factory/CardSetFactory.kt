package com.codechallenge.game.data.factory

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.data.model.CardValue
import com.codechallenge.game.data.model.PokerCard
import javax.inject.Inject

class CardSetFactory @Inject constructor() {

    fun get(suit: CardSuit): List<PokerCard> {
        return CardValue.values().map { PokerCard(it, suit) }
    }
}
