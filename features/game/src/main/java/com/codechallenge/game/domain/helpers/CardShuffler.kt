package com.codechallenge.game.domain.helpers

import com.codechallenge.game.data.model.PokerCard
import javax.inject.Inject

class CardShuffler @Inject constructor() {

    fun List<PokerCard>.shuffleDeck(): List<PokerCard> {
        return toMutableList()
            .apply { shuffle() }
    }
}
