package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.RoundRepository
import com.codechallenge.game.domain.formatter.GameSummaryFormatter
import javax.inject.Inject

class GetRoundSummaryPoints @Inject constructor(
    private val gameSummaryFormatter: GameSummaryFormatter,
    private val gameRoundRepository: RoundRepository
) {

    suspend fun execute(): Pair<Int, Int> {
        return with(gameSummaryFormatter) {
            gameRoundRepository.getGameSummary().toPoints()
        }
    }
}
