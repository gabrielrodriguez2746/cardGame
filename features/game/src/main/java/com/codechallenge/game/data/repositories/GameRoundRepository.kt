package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.Round
import javax.inject.Inject

class GameRoundRepository @Inject constructor() {

    private val gameSummary = mutableListOf<Round>()

    fun getGameSummary(): List<Round> {
        return gameSummary
    }

    fun addRound(round: Round) {
        gameSummary.add(round)
    }
}
