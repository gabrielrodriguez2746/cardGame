package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.GameState
import com.codechallenge.game.data.model.PlayersGameState
import com.codechallenge.injector.PerFragment
import javax.inject.Inject

@PerFragment
class GameStateRepository @Inject constructor() : StateRepository {

    private var _gameState = GameState(
        listOf(
            PlayersGameState.PlayerOneState(emptyList()),
            PlayersGameState.PlayerTwoState(emptyList())
        )
    )

    override suspend fun getGameState(): GameState {
        return _gameState
    }

    override suspend fun updateGameState(state: GameState) {
        _gameState = state
    }

    override suspend fun clean() {
        _gameState = GameState(
            listOf(
                PlayersGameState.PlayerOneState(emptyList()),
                PlayersGameState.PlayerTwoState(emptyList())
            )
        )
    }
}
