package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.RoundRepository
import com.codechallenge.game.domain.formatter.GameSummaryFormatter
import com.codechallenge.game.domain.model.GameSummary
import javax.inject.Inject

class GetGameSummaryUseCase @Inject constructor(
    private val gameSummaryFormatter: GameSummaryFormatter,
    private val gameRoundRepository: RoundRepository
) {

    suspend fun execute(): GameSummary {
        return GameSummary(
            gameRoundRepository.getGameSummary().map {
                with(gameSummaryFormatter) { it.toSummaryRound() }
            }
        )
    }
}
