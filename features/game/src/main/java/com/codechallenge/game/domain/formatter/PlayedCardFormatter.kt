package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.PokerCard
import com.codechallenge.game.domain.model.PlayedCard
import javax.inject.Inject

class PlayedCardFormatter @Inject constructor(
    private val playerCardFormatter: PlayerCardFormatter
) {

    fun PlayedCard.toPokerCard(): PokerCard {
        return with(playerCardFormatter) { card.toPokerCard() }
    }
}
