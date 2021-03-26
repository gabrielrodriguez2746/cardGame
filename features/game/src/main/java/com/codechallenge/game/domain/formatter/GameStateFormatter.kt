package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.GameState
import com.codechallenge.game.data.model.PlayersGameState.PlayerOneState
import com.codechallenge.game.data.model.PlayersGameState.PlayerTwoState
import com.codechallenge.game.domain.model.Player
import javax.inject.Inject

class GameStateFormatter @Inject constructor(
    private val playerCardFormatter: PlayerCardFormatter
) {

    fun Pair<Player, Player>.toGameState(): GameState {
        val (playerOne, playerTwo) = this
        return GameState(
            listOf(
                PlayerOneState(playerOne.cardDeck.map { with(playerCardFormatter) { it.toPokerCard() } }),
                PlayerTwoState(playerTwo.cardDeck.map { with(playerCardFormatter) { it.toPokerCard() } }),
            )
        )
    }

    fun GameState.toPlayers(): Pair<Player, Player> {
        return Player.PlayerOne(
            players.first { it is PlayerOneState }.list.map {
                with(playerCardFormatter) { it.toPlayerCard() }
            }
        ) to Player.PlayerTwo(
            players.first { it is PlayerTwoState }.list.map {
                with(playerCardFormatter) { it.toPlayerCard() }
            }
        )
    }
}
