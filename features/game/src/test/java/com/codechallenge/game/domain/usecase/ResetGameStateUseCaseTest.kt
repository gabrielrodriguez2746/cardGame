package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.GameRoundRepository
import com.codechallenge.game.data.repositories.GameStateRepository
import com.codechallenge.game.data.repositories.SuitsPriorityRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class ResetGameStateUseCaseTest {

    private val gameRoundRepository = mockk<GameRoundRepository>(relaxUnitFun = true)
    private val gameStateRepository = mockk<GameStateRepository>(relaxUnitFun = true)
    private val suitePriorityRepository = mockk<SuitsPriorityRepository>(relaxUnitFun = true)

    private val useCase = ResetGameStateUseCase(
        gameRoundRepository,
        gameStateRepository,
        suitePriorityRepository
    )

    @Test
    fun `WHEN execute THEN call clean in all dependencies`() {

        runBlocking { useCase.execute() }

        coVerify(exactly = 1) {
            gameRoundRepository.clean()
            gameStateRepository.clean()
            suitePriorityRepository.clean()
        }
    }
}
