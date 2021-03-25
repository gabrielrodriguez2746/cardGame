package com.codechallenge.game.domain.model

sealed class GameSummaryRound(
    open val playerOneCard: PlayerCard,
    open val playerTwoCard: PlayerCard
) {
    data class PlayerOneRound(
        override val playerOneCard: PlayerCard,
        override val playerTwoCard: PlayerCard
    ) : GameSummaryRound(playerOneCard, playerTwoCard)

    data class PlayerTwoRound(
        override val playerOneCard: PlayerCard,
        override val playerTwoCard: PlayerCard
    ) : GameSummaryRound(playerOneCard, playerTwoCard)
}
