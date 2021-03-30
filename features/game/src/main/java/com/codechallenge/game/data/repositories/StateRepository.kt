package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.GameState

interface StateRepository {
    suspend fun getGameState(): GameState
    suspend fun updateGameState(state: GameState)
    suspend fun clean()
}
