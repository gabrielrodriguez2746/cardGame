package com.codechallenge.game.presentation

import com.codechallenge.game.domain.model.GameSummary
import com.codechallenge.game.domain.model.Player
import com.codechallenge.game.domain.model.PlayerCard
import com.codechallenge.game.domain.model.PlayerCardSuit

sealed class GameState {
    object GameRest : GameState()

    data class GameStarted(
        val playerOne: Player,
        val playerTwo: Player,
        val suits: List<PlayerCardSuit>
    ) : GameState()

    data class RoundPlayed(val card1: PlayerCard, val card2: PlayerCard) : GameState()

    data class RoundWinnerCalculated(val hasPlayerOneWon: Boolean) : GameState()

    data class RoundFinished(
        val player1: Player,
        val playerOnePoints: Int,
        val player2: Player,
        val playerTwoPoints: Int
    ) : GameState()

    data class GameFinished(val summary: GameSummary) : GameState()
}
