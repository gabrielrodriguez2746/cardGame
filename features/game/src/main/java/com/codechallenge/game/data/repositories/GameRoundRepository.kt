package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.Round
import com.codechallenge.injector.PerFragment
import javax.inject.Inject

@PerFragment
class GameRoundRepository @Inject constructor() : RoundRepository {

    private val gameSummary = mutableListOf<Round>()

    override suspend fun getGameSummary(): List<Round> {
        return gameSummary
    }

    override suspend fun addRound(round: Round) {
        gameSummary.add(round)
    }

    override suspend fun clean() {
        gameSummary.clear()
    }
}
