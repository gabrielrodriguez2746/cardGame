package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.PriorityRepository
import com.codechallenge.game.domain.formatter.SuitsCardFormatter
import com.codechallenge.game.domain.model.PlayerCardSuit
import javax.inject.Inject

class GetSuitsPriorityUseCase @Inject constructor(
    private val suitsCardFormatter: SuitsCardFormatter,
    private val suitsPriorityRepository: PriorityRepository,
) {

    suspend fun execute(): List<PlayerCardSuit> {
        return with(suitsCardFormatter) {
            suitsPriorityRepository.getSuitsPriority().map { it.toPlayerCardSuit() }
        }
    }
}
