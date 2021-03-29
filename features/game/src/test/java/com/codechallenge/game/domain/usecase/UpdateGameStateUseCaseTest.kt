package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.model.GameState
import com.codechallenge.game.data.repositories.GameStateRepository
import com.codechallenge.game.domain.formatter.GameStateFormatter
import com.codechallenge.game.domain.model.Player
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

internal class UpdateGameStateUseCaseTest {
    private val gameStateFormatter: GameStateFormatter = mockk()
    private val gameStateRepository: GameStateRepository = mockk(relaxUnitFun = true)

    private val useCase = UpdateGameStateUseCase(gameStateFormatter, gameStateRepository)

    @Test
    fun `WHEN execute THEN updateGameState`() {
        val input = mockk<Player>() to mockk<Player>()
        val gameState = mockk<GameState>()

        every { with(gameStateFormatter) { input.toGameState() } } returns gameState

        runBlocking { useCase.execute(input) }

        coVerify(exactly = 1) { gameStateRepository.updateGameState(gameState) }
    }
}
