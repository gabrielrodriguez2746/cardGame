package com.codechallenge.game.data.model

sealed class Round(open val playerOneCard: PokerCard, open val playerTwoCard: PokerCard) {
    data class PlayerOneRound(
        override val playerOneCard: PokerCard,
        override val playerTwoCard: PokerCard
    ) : Round(playerOneCard, playerTwoCard)

    data class PlayerTwoRound(
        override val playerOneCard: PokerCard,
        override val playerTwoCard: PokerCard
    ) : Round(playerOneCard, playerTwoCard)
}
