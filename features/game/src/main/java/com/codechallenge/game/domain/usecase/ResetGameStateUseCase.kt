package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.PriorityRepository
import com.codechallenge.game.data.repositories.RoundRepository
import com.codechallenge.game.data.repositories.StateRepository
import javax.inject.Inject

class ResetGameStateUseCase @Inject constructor(
    private val gameRoundRepository: RoundRepository,
    private val gameStateRepository: StateRepository,
    private val suitePriorityRepository: PriorityRepository
) {

    suspend fun execute() {
        gameRoundRepository.clean()
        gameStateRepository.clean()
        suitePriorityRepository.clean()
    }
}
