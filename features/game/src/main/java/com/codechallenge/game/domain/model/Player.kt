package com.codechallenge.game.domain.model

sealed class Player(open val cardDeck: List<PlayerCard>) {
    data class PlayerOne(override val cardDeck: List<PlayerCard>) : Player(cardDeck)
    data class PlayerTwo(override val cardDeck: List<PlayerCard>) : Player(cardDeck)
}
