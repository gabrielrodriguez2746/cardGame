package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.GameState
import com.codechallenge.game.data.model.PlayersGameState
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GameStateRepositoryTest {
    private lateinit var repository: GameStateRepository

    @BeforeEach
    fun setUp() {
        repository = GameStateRepository()
    }

    @Test
    fun `GIVEN updateGameState WHEN getGameState THEN contains GameState`() = runBlocking {
        val gameState = mockk<GameState>()
        repository.updateGameState(gameState)
        repository.getGameState() shouldBe gameState
    }

    @Test
    fun `GIVEN no updateGameState WHEN getGameState THEN get empty default state`() = runBlocking {
        repository.getGameState() shouldBe GameState(
            listOf(
                PlayersGameState.PlayerOneState(emptyList()),
                PlayersGameState.PlayerTwoState(emptyList())
            )
        )
    }

    @Test
    fun `GIVEN updateGameState WHEN clean THEN get empty default state`() = runBlocking {
        val gameState = mockk<GameState>()
        repository.updateGameState(gameState)

        repository.clean()

        repository.getGameState() shouldBe GameState(
            listOf(
                PlayersGameState.PlayerOneState(emptyList()),
                PlayersGameState.PlayerTwoState(emptyList())
            )
        )
    }
}
