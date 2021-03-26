package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.GameRoundRepository
import com.codechallenge.game.domain.formatter.GameSummaryFormatter
import javax.inject.Inject

class GetRoundSummaryPoints @Inject constructor(
    private val gameSummaryFormatter: GameSummaryFormatter,
    private val gameRoundRepository: GameRoundRepository
) {

    fun execute(): Pair<Int, Int> {
        return with(gameSummaryFormatter) {
            gameRoundRepository.getGameSummary().toPoints()
        }
    }
}
