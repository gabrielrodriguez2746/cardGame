package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.CardsGameRepository
import com.codechallenge.game.data.repositories.GameRoundRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class HasGameEndUseCaseTest {

    private val roundRepository: GameRoundRepository = mockk()
    private val cardsGameRepository: CardsGameRepository = mockk()

    private lateinit var useCase: HasGameEndUseCase

    @BeforeEach
    fun setUp() {
        useCase = HasGameEndUseCase(roundRepository, cardsGameRepository)
    }

    @Test
    fun `GIVEN getGameSummary size lower than half of the game cards WHEN execute THEN return false`() {
        coEvery { roundRepository.getGameSummary() } returns emptyList()
        coEvery { cardsGameRepository.getSetOfCard() } returns listOf(mockk(), mockk())

        runBlocking { useCase.execute() } shouldBe false
    }

    @Test
    fun `GIVEN getGameSummary size equals than half of the game cards WHEN execute THEN return true`() {
        coEvery { roundRepository.getGameSummary() } returns listOf(mockk())
        coEvery { cardsGameRepository.getSetOfCard() } returns listOf(mockk(), mockk())

        runBlocking { useCase.execute() } shouldBe true
    }
}
