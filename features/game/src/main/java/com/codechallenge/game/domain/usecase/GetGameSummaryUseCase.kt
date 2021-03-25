package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.GameRoundRepository
import com.codechallenge.game.domain.formatter.GameSummaryFormatter
import com.codechallenge.game.domain.model.GameSummary
import javax.inject.Inject

class GetGameSummaryUseCase @Inject constructor(
    private val gameSummaryFormatter: GameSummaryFormatter,
    private val gameRoundRepository: GameRoundRepository
) {

    fun execute(): GameSummary {
        return GameSummary(
            gameRoundRepository.getGameSummary().map {
                with(gameSummaryFormatter) { it.toSummaryRound() }
            }
        )
    }
}
