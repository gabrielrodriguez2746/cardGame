package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.factory.CardSetFactory
import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.data.model.PokerCard
import javax.inject.Inject

class CardsGameRepository @Inject constructor(
    private val cardSetFactory: CardSetFactory
) {

    fun getSetOfCard(): List<PokerCard> {
        return cardSetFactory.get(CardSuit.HEARTS).toMutableList() +
            cardSetFactory.get(CardSuit.CLUB).toMutableList() +
            cardSetFactory.get(CardSuit.SPADES).toMutableList() +
            cardSetFactory.get(CardSuit.DIAMOND).toMutableList()
    }
}
