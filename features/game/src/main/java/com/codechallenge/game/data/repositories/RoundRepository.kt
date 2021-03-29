package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.Round

interface RoundRepository {
    suspend fun getGameSummary(): List<Round>
    suspend fun addRound(round: Round)
    suspend fun clean()
}
