package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.PokerCard
import com.codechallenge.game.domain.model.PlayerCard
import com.codechallenge.game.domain.model.PlayerCardSuit
import javax.inject.Inject

class PlayerCardFormatter @Inject constructor(
    private val cardValueFormatter: CardValueFormatter,
    private val suitsCardFormatter: SuitsCardFormatter
) {

    fun PokerCard.toPlayerCard(): PlayerCard {
        val (value, label, suits) = toPlayerCardInformation()
        return PlayerCard(value, label, suits)
    }

    fun PlayerCard.toPokerCard(): PokerCard {
        val cardValue = with(cardValueFormatter) { value.toCardValue() }
        val cardSuit = with(suitsCardFormatter) { suit.toCardSuit() }
        return PokerCard(cardValue, cardSuit)
    }

    private fun PokerCard.toPlayerCardInformation(): Triple<Int, String, PlayerCardSuit> {
        return Triple(
            with(cardValueFormatter) { value.toIntValue() },
            with(cardValueFormatter) { value.toLabel() },
            with(suitsCardFormatter) { suits.toPlayerCardSuit() }
        )
    }
}
