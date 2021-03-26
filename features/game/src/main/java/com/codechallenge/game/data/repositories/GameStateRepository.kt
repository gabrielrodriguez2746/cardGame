package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.GameState
import com.codechallenge.game.data.model.PlayersGameState
import javax.inject.Inject

class GameStateRepository @Inject constructor() {

    private var _gameState = GameState(
        listOf(
            PlayersGameState.PlayerOneState(emptyList()),
            PlayersGameState.PlayerTwoState(emptyList())
        )
    )

    fun getGameState(): GameState {
        return _gameState
    }

    fun updateGameState(state: GameState) {
        _gameState = state
    }

    fun clean() {
        _gameState = GameState(
            listOf(
                PlayersGameState.PlayerOneState(emptyList()),
                PlayersGameState.PlayerTwoState(emptyList())
            )
        )
    }
}
