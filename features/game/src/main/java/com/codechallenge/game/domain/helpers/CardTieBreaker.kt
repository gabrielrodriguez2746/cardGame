package com.codechallenge.game.domain.helpers

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.domain.formatter.SuitsCardFormatter
import com.codechallenge.game.domain.model.PlayedCard
import com.codechallenge.game.domain.model.PlayerCard
import com.codechallenge.game.domain.model.PlayerCardSuit
import javax.inject.Inject

class CardTieBreaker @Inject constructor(
    private val suitsCardFormatter: SuitsCardFormatter
) {

    fun Pair<PlayerCard, PlayerCard>.tieBreak(suits: List<CardSuit>): Pair<PlayedCard, PlayedCard> {
        val (playerOneCard, playerTwoCard) = this
        return with(suits.map { with(suitsCardFormatter) { it.toPlayerCardSuit() } }) {
            when {
                suitIndex(playerOneCard) < suitIndex(playerTwoCard) -> PlayedCard.WinnerCard(playerOneCard) to PlayedCard.LooserCard(playerTwoCard)
                else -> PlayedCard.WinnerCard(playerTwoCard) to PlayedCard.LooserCard(playerOneCard)
            }
        }
    }

    private fun List<PlayerCardSuit>.suitIndex(card: PlayerCard): Int {
        return indexOf(card.suit).takeIf { it != -1 } ?: Int.MAX_VALUE
    }
}
