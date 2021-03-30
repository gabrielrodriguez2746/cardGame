package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.factory.CardSetFactory
import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.data.model.PokerCard
import com.codechallenge.injector.PerFragment
import javax.inject.Inject

@PerFragment
class CardsGameRepository @Inject constructor(
    private val cardSetFactory: CardSetFactory
) : GameRepository {

    override suspend fun getSetOfCard(): List<PokerCard> {
        return cardSetFactory.get(CardSuit.HEARTS).toMutableList() +
            cardSetFactory.get(CardSuit.CLUB).toMutableList() +
            cardSetFactory.get(CardSuit.SPADES).toMutableList() +
            cardSetFactory.get(CardSuit.DIAMOND).toMutableList()
    }
}
