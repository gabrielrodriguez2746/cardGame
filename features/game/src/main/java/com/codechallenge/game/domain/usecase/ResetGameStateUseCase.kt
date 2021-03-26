package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.GameRoundRepository
import com.codechallenge.game.data.repositories.GameStateRepository
import com.codechallenge.game.data.repositories.SuitsPriorityRepository
import javax.inject.Inject

class ResetGameStateUseCase @Inject constructor(
    private val gameRoundRepository: GameRoundRepository,
    private val gameStateRepository: GameStateRepository,
    private val suitePriorityRepository: SuitsPriorityRepository
) {

    fun execute() {
        gameRoundRepository.clean()
        gameStateRepository.clean()
        suitePriorityRepository.clean()
    }
}
