package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.model.GameState
import com.codechallenge.game.data.model.PlayersGameState
import com.codechallenge.game.data.repositories.GameStateRepository
import com.codechallenge.game.domain.formatter.GameStateFormatter
import com.codechallenge.game.domain.model.Player
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetGameStateUseCaseTest {

    private val gameStateFormatter: GameStateFormatter = mockk()
    private val gameStateRepository: GameStateRepository = mockk()

    private lateinit var useCase: GetGameStateUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetGameStateUseCase(gameStateFormatter, gameStateRepository)
    }

    @Test
    fun `WHEN execute THEN get expected`() {
        val playerOne = mockk<Player>()
        val playerTwo = mockk<Player>()
        val expected = playerOne to playerTwo

        val playerOneState = mockk<PlayersGameState.PlayerOneState>()
        val playerTwoState = mockk<PlayersGameState.PlayerOneState>()
        val gameState = GameState(listOf(playerOneState, playerTwoState))

        every { with(gameStateFormatter) { gameState.toPlayers() } } returns expected

        coEvery { gameStateRepository.getGameState() } returns gameState

        runBlocking { useCase.execute() } shouldBe expected
    }
}
