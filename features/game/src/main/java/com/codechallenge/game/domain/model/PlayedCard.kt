package com.codechallenge.game.domain.model

sealed class PlayedCard(open val card: PlayerCard) {
    data class WinnerCard(override val card: PlayerCard) : PlayedCard(card)
    data class LooserCard(override val card: PlayerCard) : PlayedCard(card)
}
